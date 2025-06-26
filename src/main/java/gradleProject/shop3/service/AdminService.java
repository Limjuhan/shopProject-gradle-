//package gradleProject.shop3.service;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Properties;
//
//import gradleProject.shop3.repository.AdminRepository;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import jakarta.mail.Authenticator;
//import jakarta.mail.Message;
//import jakarta.mail.MessagingException;
//import jakarta.mail.PasswordAuthentication;
//import jakarta.mail.Session;
//import jakarta.mail.Transport;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeBodyPart;
//import jakarta.mail.internet.MimeMessage;
//import jakarta.mail.internet.MimeMultipart;
//
//
//@Service
//public class AdminService {
//
//	private final AdminRepository adminDao;
//
////	@Value("${resources.dir}")
////	private String RESOURCE_DIR;
//
//	public AdminService(AdminDao adminDao) {
//		this.adminDao = adminDao;
//	}
//
//	public List<User> getUserList() {
//		return adminDao.getUserList();
//	}
//
//	public List<User> getUserList(String[] idchks) {
//
//		return adminDao.getUserList(idchks);
//	}
//
//	//메일 전송을 위한 인증 클래스
//	private final class MyAuthenticator extends Authenticator {
//
//		private String id;
//		private String pw;
//
//		public MyAuthenticator(String id, String pw) {
//			this.id = id;
//			this.pw = pw;
//		}
//
//		@Override
//		protected PasswordAuthentication getPasswordAuthentication() {
//			return new PasswordAuthentication(id, pw);
//		}
//	}
//
//	public boolean mailSend(Mail mail) {
//		String sender = mail.getGoogleid() + "@gmail.com";
//		Properties prop = new Properties();
//
//		try {
//			FileInputStream fis = new FileInputStream(RESOURCE_DIR + "mail.properties");
//
//			prop.load(fis);
//			prop.put("mail.smtp.user", sender);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		MyAuthenticator auth = new MyAuthenticator(sender, mail.getGooglepw());
//		Session session = Session.getInstance(prop, auth);
//		MimeMessage msg = new MimeMessage(session);
//
//		try {
//			msg.setFrom(new InternetAddress(sender));
//			List<InternetAddress> addrs = new ArrayList<InternetAddress>();
//			String[] emails = mail.getRecipient().split(",");
//
//			for (String email : emails) {
//				addrs.add(new InternetAddress(email));
//			}
//
//			InternetAddress[] arr = new InternetAddress[emails.length];
//			for (int i=0; i<addrs.size(); i++) {
//				arr[i]=addrs.get(i);
//			}
//
//			msg.setRecipients(Message.RecipientType.TO, arr);
//			msg.setSentDate(new Date());
//			msg.setSubject(mail.getTitle());
//			MimeMultipart multipart = new MimeMultipart();
//			MimeBodyPart message = new MimeBodyPart();
//			message.setContent(mail.getContents(), mail.getMtype());
//			multipart.addBodyPart(message);
//
//			for (MultipartFile mf : mail.getFile1()) {
//				if ((mf != null) && (!mf.isEmpty())) {
//					multipart.addBodyPart(bodyPart(mf));
//				}
//			}
//
//			msg.setContent(multipart);
//			Transport.send(msg);
//			return true;
//		} catch (MessagingException me) {
//			me.printStackTrace();
//		}
//		return false;
//	}
//
//	private MimeBodyPart bodyPart(MultipartFile mf) {
//		MimeBodyPart body = new MimeBodyPart();
//		String orgFile = mf.getOriginalFilename();
//		String path = RESOURCE_DIR + "mailupload/";
//		File f1 = new File(path);
//
//		if (!f1.exists()) {
//			f1.mkdirs();
//		}
//		File f2 = new File(path + orgFile);
//
//		try {
//			mf.transferTo(f2);
//			body.attachFile(f2);
//			body.setFileName(orgFile);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		return body;
//	}
//
//	public void mailfileDelete(Mail mail) {
//		String path = RESOURCE_DIR + "mailupload/";
//		List<String> filenames = new ArrayList<String>();
//
//		for (MultipartFile mf : mail.getFile1()) {
//			filenames.add(mf.getOriginalFilename());
//		}
//
//		for (String f : filenames) {
//			File df = new File(path,f);
//			df.delete();
//		}
//
//	}
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
