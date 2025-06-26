package gradleProject.shop3.service;

import gradleProject.shop3.domain.User;
import gradleProject.shop3.dto.MailDto; // MailDto 경로 확인
import gradleProject.shop3.dto.UserDisplayDto;
import gradleProject.shop3.exception.ShopException;
import gradleProject.shop3.repository.AdminRepository;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final UserService userService;

    // application.properties에서 메일 설정 값 주입
    @Value("${spring.mail.username}")
    private String mailSenderId;
    @Value("${spring.mail.password}")
    private String mailSenderPassword;
    @Value("${spring.mail.host}")
    private String mailHost;
    @Value("${spring.mail.port}")
    private int mailPort;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean smtpAuth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean startTlsEnable;
    @Value("${spring.mail.properties.mail.smtp.ssl.protocols}")
    private String sslProtocols;
    @Value("${file.upload-dir}")
    private String uploadDir;

    public AdminService(AdminRepository adminRepository, UserService userService) {
        this.adminRepository = adminRepository;
        this.userService = userService;
    }

    /**
     * @return 복호화된 이메일 정보를 포함하는 UserDisplayDto 반환
     */
    public List<UserDisplayDto> getAllUsersWithDecryptedEmails() {
        return userService.getDecryptedUsers(adminRepository.findAll());
    }

    /**
     * 주어진 사용자 ID 배열에 해당하는 사용자들의 목록을 조회하고,
     * 이메일이 복호화된 상태로 MailDto 객체의 recipient 필드에 조합하여 반환합니다.
     *
     * @param idchks 선택된 사용자 ID 배열
     * @return MailDto 객체, recipient 필드에 받는 사람 이메일 주소들이 조합되어 있음
     */
    public MailDto getMailDtoForSelectedUsers(String[] idchks) {
        List<UserDisplayDto> selectedUsers = userService.getDecryptedUsersByIds(idchks);
        MailDto mailDto = new MailDto();
        String recipientEmails = userService.buildRecipientEmails(selectedUsers);
        mailDto.setRecipient(recipientEmails);
        return mailDto;
    }

    // 메일 전송을 위한 인증 클래스 (private static inner class로 유지)
    private static class MyAuthenticator extends Authenticator {
        private final String id;
        private final String pw;

        public MyAuthenticator(String id, String pw) {
            this.id = id;
            this.pw = pw;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(id, pw);
        }
    }

    /**
     * 메일을 전송합니다.
     * @param mailDto 메일 전송에 필요한 정보를 담은 DTO
     * @param senderEmail 보내는 사람의 실제 이메일 주소 (예: 로그인한 관리자 이메일)
     * @return 메일 전송 성공 여부
     */
    public boolean sendMail(MailDto mailDto, String senderEmail) {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", mailHost);
        prop.put("mail.smtp.port", String.valueOf(mailPort));
        prop.put("mail.smtp.auth", String.valueOf(smtpAuth));
        prop.put("mail.smtp.starttls.enable", String.valueOf(startTlsEnable));
        prop.put("mail.smtp.ssl.protocols", sslProtocols);
        prop.put("mail.smtp.user", mailSenderId); // application.properties의 mail.smtp.username

        // MyAuthenticator 인스턴스 생성 시 application.properties에서 주입받은 ID/PW 사용
        MyAuthenticator auth = new MyAuthenticator(mailSenderId, mailSenderPassword);
        Session session = Session.getInstance(prop, auth);
        MimeMessage msg = new MimeMessage(session);

        try {
            msg.setFrom(new InternetAddress(senderEmail));

            List<InternetAddress> addrs = new java.util.ArrayList<>();
            String[] emails = mailDto.getRecipient().split(",");
            for (String email : emails) {
                String trimmedEmail = email.trim();
                if (!trimmedEmail.isEmpty()) {
                    addrs.add(new InternetAddress(trimmedEmail));
                }
            }

            if (addrs.isEmpty()) {
                throw new MessagingException("메일을 보낼 받는 사람이 없습니다.");
            }

            msg.setRecipients(Message.RecipientType.TO, addrs.toArray(new InternetAddress[0]));
            msg.setSentDate(new Date());
            msg.setSubject(mailDto.getTitle());

            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(mailDto.getContents(), mailDto.getMtype());
            multipart.addBodyPart(messageBodyPart);

            if (mailDto.getFiles() != null && !mailDto.getFiles().isEmpty()) {
                for (MultipartFile mf : mailDto.getFiles()) {
                    if (mf != null && !mf.isEmpty()) {
                        multipart.addBodyPart(createAttachmentPart(mf));
                    }
                }
            }

            msg.setContent(multipart);
            Transport.send(msg);
            return true;
        } catch (MessagingException me) {
            me.printStackTrace();
            throw new ShopException("메일 전송에 실패했습니다: " + me.getMessage(), me);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ShopException("첨부파일 처리 중 오류 발생: " + e.getMessage(), e);
        } finally {
            // 임시 파일 삭제 (성공 여부와 관계없이)
            if (mailDto.getFiles() != null) {
                mailDto.getFiles().forEach(this::deleteTemporaryFile);
            }
        }
    }

    // 첨부 파일을 위한 MimeBodyPart 생성 및 임시 파일 저장
    private MimeBodyPart createAttachmentPart(MultipartFile mf) throws IOException, MessagingException {
        MimeBodyPart body = new MimeBodyPart();
        String originalFilename = mf.getOriginalFilename();
        File uploadDirFile = new File(uploadDir);

        if (!uploadDirFile.exists()) {
            uploadDirFile.mkdirs();
        }

        File tempFile = new File(uploadDir, originalFilename); // 고유한 파일명 생성 고려
        mf.transferTo(tempFile);
        body.attachFile(tempFile);
        body.setFileName(originalFilename);
        return body;
    }

    // 임시 파일 삭제 메서드
    private void deleteTemporaryFile(MultipartFile mf) {
        if (mf != null && !mf.isEmpty()) {
            String originalFilename = mf.getOriginalFilename();
            File tempFile = new File(uploadDir, originalFilename);
            if (tempFile.exists() && !tempFile.isDirectory()) {
                if (tempFile.delete()) {
                    System.out.println("임시 파일 삭제 성공: " + tempFile.getName());
                } else {
                    System.err.println("임시 파일 삭제 실패: " + tempFile.getName());
                }
            }
        }
    }
}