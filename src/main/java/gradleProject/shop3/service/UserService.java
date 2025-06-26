package gradleProject.shop3.service;

import gradleProject.shop3.domain.User;
import gradleProject.shop3.dto.UserDto;
import gradleProject.shop3.dto.UserPwdSearchDto;
import gradleProject.shop3.repository.UserRepository;
import gradleProject.shop3.util.CipherUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

//	@Value("${resources.dir}")
//	private String RESOURCES_DIR;

	@Autowired
	UserRepository userRepository;

	public void userInsert(User user) {
		userRepository.save(user);
	}

	public User selectUser(String userid) {
		return userRepository.findByUserid(userid).get();
	}

	public UserDto encryptEmail(UserDto userDto) throws Exception {
		// 이메일을 암호화 처리
		// userid의 해쉬값
		// email 암호화
		String cipherUserid = CipherUtil.makehash(userDto.getUserid());
		String cipherEmail = CipherUtil.encrypt(userDto.getEmail(), cipherUserid);
		userDto.setEmail(cipherEmail);

		return userDto;
	}

	 public String encryptPwd(String pwd) throws Exception {
		 String cipherPass = CipherUtil.makehash(pwd);
		 return cipherPass;
	 }

	public String decryptEmail(User user) {
		try{
			String hashId = CipherUtil.makehash(user.getUserid());
			System.err.println("user객체 email확인: " + user.getEmail());
			System.err.println("user객체 hashId확인: " + hashId);
			String email = CipherUtil.decrypt(user.getEmail(), hashId);
			return email;
		}
		catch (Exception e) {
			System.err.println("이메일 복호화 오류");
			e.printStackTrace();
		}
		return null;
	}

	public void userUpdate(User user) {
		userRepository.save(user);
	}

	public void userDelete(String userid) {
		userRepository.deleteById(userid);
	}

	public Optional<String> findUserIdByEmailAndPhoneno(String email, String phoneno) {

		List<User> usersWithPhoneno = userRepository.findByPhoneno(phoneno);

		for (User user : usersWithPhoneno) {
			try {
				// 각 사용자의 userid로 이메일 복호화 키 생성
				String hashId = CipherUtil.makehash(user.getUserid());
				// 이메일 복호화
				String decryptedEmail = CipherUtil.decrypt(user.getEmail(), hashId);
				// 복호화된 이메일과 입력된 이메일 비교
				if (decryptedEmail.equals(email)) {
					return Optional.of(user.getUserid()); // 일치하는 사용자 ID 반환
				}
			} catch (Exception e) {
				System.err.println("사용자 : " + user.getUserid() + "의 이메일 복호화 오류 : " + e.getMessage());
			}
		}
		return Optional.empty();
	}

	public Optional<User> findUserByIdEmailAndPhoneno(UserPwdSearchDto userPwdSearchDto) {

		Optional<User> findUser = userRepository.findByUserid(userPwdSearchDto.getUserid());

		if (findUser.isPresent()) {
			if (decryptEmail(findUser.get()).equals(userPwdSearchDto.getEmail())) {
				return findUser;
			}
		}
		return Optional.empty();
	}

	// 6자리 임시 비밀번호 생성
	public String generateRandomPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++) {
			sb.append(characters.charAt(random.nextInt(characters.length())));
		}
		return sb.toString();
	}
//	public void updatePassword(String chgpass, String userid) {
//		userdao.updatePassword(chgpass,userid);
//	}
}
