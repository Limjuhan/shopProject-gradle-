package gradleProject.shop3.service;

import gradleProject.shop3.domain.User;
import gradleProject.shop3.dto.UserDisplayDto;
import gradleProject.shop3.dto.UserDto; // 기존 DTO 그대로 사용
import gradleProject.shop3.dto.UserPwdSearchDto; // 기존 DTO 그대로 사용
import gradleProject.shop3.exception.ShopException; // ShopException 경로 확인
import gradleProject.shop3.mapper.UserMapper;
import gradleProject.shop3.repository.UserRepository; // UserRepository 경로 확인
import gradleProject.shop3.util.CipherUtil; // CipherUtil 경로 확인
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	@Autowired
	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

	/**
	 * 새로운 사용자를 저장합니다.
	 * @param user 저장할 User 엔티티
	 */
	public void userInsert(User user) {
		userRepository.save(user);
	}

	/**
	 * 특정 userid로 사용자를 조회합니다.
	 * @param userid 조회할 사용자 ID
	 * @return 조회된 User 엔티티
	 * @throws java.util.NoSuchElementException 해당 userid의 사용자가 없을 경우 발생
	 */
	public User selectUser(String userid) {
		return userRepository.findByUserid(userid)
				.orElseThrow(() -> new ShopException("사용자를 찾을 수 없습니다: " + userid, (String) null));
	}

	/**
	 * UserDto 객체의 이메일을 암호화합니다.
	 * 암호화 키는 userid의 해시값을 사용합니다.
	 * @param userDto 암호화할 이메일 정보가 담긴 UserDto
	 * @return 이메일이 암호화된 UserDto
	 * @throws Exception 암호화 중 오류 발생 시
	 */
	public UserDto encryptEmail(UserDto userDto) throws Exception {
		String cipherUserid = CipherUtil.makehash(userDto.getUserid());
		String cipherEmail = CipherUtil.encrypt(userDto.getEmail(), cipherUserid);
		userDto.setEmail(cipherEmail);
		return userDto;
	}

	/**
	 * 비밀번호를 해시값으로 암호화합니다.
	 * @param pwd 암호화할 비밀번호
	 * @return 해시화된 비밀번호
	 * @throws Exception 암호화 중 오류 발생 시
	 */
	public String encryptPwd(String pwd) throws Exception {
		return CipherUtil.makehash(pwd);
	}

	/**
	 * User 엔티티의 이메일을 복호화합니다.
	 * @param user 복호화할 이메일 정보가 담긴 User 엔티티
	 * @return 복호화된 이메일 문자열
	 * @throws IllegalArgumentException 사용자 또는 이메일 정보가 유효하지 않을 경우
	 * @throws ShopException 이메일 복호화 중 오류 발생 시
	 */
	public String decryptEmail(User user) {
		if (user == null || user.getUserid() == null || user.getEmail() == null) {
			throw new IllegalArgumentException("사용자 또는 이메일 정보가 유효하지 않습니다.");
		}
		try {
			String hashId = CipherUtil.makehash(user.getUserid());
			return CipherUtil.decrypt(user.getEmail(), hashId);
		} catch (Exception e) {
			log.error("이메일 복호화 중 오류 발생 - 사용자 ID: {}", user.getUserid(), e);
			throw new ShopException("이메일 복호화에 실패했습니다.", e);
		}
	}

	/**
	 * 주어진 사용자 리스트의 각 User 객체에 대해 이메일을 복호화하여 업데이트합니다.
	 * 복호화 실패 시 경고 로그를 남기고 해당 사용자는 원본 이메일을 유지하거나 null로 처리할 수 있습니다.
	 * (현재 구현은 실패 시 원본 유지하고 경고 로그만 남김)
	 * @param users 이메일 복호화가 필요한 User 객체 리스트
	 * @return 이메일이 복호화된 User 객체 리스트
	 */
	public List<UserDisplayDto> getDecryptedUsers (List<User> users) {
		return users.stream()
				.map(user -> {
					UserDisplayDto displayDto = userMapper.toDisplayDto(user);
					String decryptedEmail;
					try {
						decryptedEmail = decryptEmail(user);
					} catch (Exception e) {
						log.warn("사용자 {} 의 이메일 복호화 실패: {}", user.getUserid(), e.getMessage());
						decryptedEmail = "복호화 오류";
					}
					displayDto.setEmail(decryptedEmail);

					return displayDto;
				})
				.collect(Collectors.toList());
	}

	/**
	 * 주어진 사용자 ID 배열에 해당하는 사용자들을 조회하고,
	 * 각 사용자의 이메일을 복호화하여 반환합니다.
	 * 이 메서드는 특히 관리자 메일 발송 기능에서 선택된 사용자들의 이메일을 가져올 때 유용합니다.
	 * @param userIds 조회 및 복호화할 사용자 ID 배열
	 * @return 이메일이 복호화된 User 객체 리스트
	 */
	public List<UserDisplayDto> getDecryptedUsersByIds(String[] userIds) {
		// UserRepository의 findAllById 메서드를 사용하여 ID 목록으로 User 객체들을 조회
		List<User> users = userRepository.findAllById(List.of(userIds));
		// 조회된 사용자 리스트에 대해 이메일 복호화 적용
		return getDecryptedUsers(users);
	}

	/**
	 * 선택된 사용자 목록에서 받는 사람 이메일 주소를 "이름<이메일>," 형식으로 조합합니다.
	 * @param users 복호화된 이메일 정보를 포함하는 User 객체 리스트
	 * @return 쉼표로 구분된 받는 사람 이메일 주소 문자열
	 */
	public String buildRecipientEmails(List<UserDisplayDto> users) {
		StringBuilder recipient = new StringBuilder();
		for (UserDisplayDto u : users) {
			// User 객체의 email은 복호화된 상태라고 가정.
			recipient.append(u.getUsername()).append("<").append(u.getEmail()).append(">,");
		}
		// 마지막 쉼표 제거 (있을 경우)
		if (recipient.length() > 0) {
			recipient.setLength(recipient.length() - 1);
		}
		return recipient.toString();
	}


	/**
	 * 사용자의 정보를 업데이트합니다.
	 * @param user 업데이트할 User 엔티티
	 */
	public void userUpdate(User user) {
		userRepository.save(user);
	}

	/**
	 * 특정 userid의 사용자를 삭제합니다.
	 * @param userid 삭제할 사용자 ID
	 */
	public void userDelete(String userid) {
		userRepository.deleteById(userid);
	}

	/**
	 * 이메일과 전화번호로 사용자 ID를 찾습니다.
	 * @param email 사용자 이메일
	 * @param phoneno 사용자 전화번호
	 * @return 일치하는 사용자 ID (Optional)
	 */
	public Optional<String> findUserIdByEmailAndPhoneno(String email, String phoneno) {
		List<User> usersWithPhoneno = userRepository.findByPhoneno(phoneno);

		for (User user : usersWithPhoneno) {
			try {
				String decryptedEmail = decryptEmail(user); // 이메일 복호화 로직 재사용
				if (decryptedEmail.equals(email)) {
					return Optional.of(user.getUserid());
				}
			} catch (Exception e) {
				log.error("사용자 ID 찾기 중 이메일 복호화 오류 - 사용자: {}", user.getUserid(), e);
			}
		}
		return Optional.empty();
	}

	/**
	 * 사용자 ID, 이메일, 전화번호로 사용자를 찾습니다. (비밀번호 찾기 시 사용)
	 * @param userPwdSearchDto 사용자 ID, 이메일, 전화번호 정보가 담긴 DTO
	 * @return 일치하는 User 엔티티 (Optional)
	 */
	public Optional<User> findUserByIdEmailAndPhoneno(UserPwdSearchDto userPwdSearchDto) {
		Optional<User> findUser = userRepository.findByUserid(userPwdSearchDto.getUserid());

		if (findUser.isPresent()) {
			User user = findUser.get();
			try {
				if (decryptEmail(user).equals(userPwdSearchDto.getEmail())) {
					// 전화번호도 비교 (추가된 부분)
					if (user.getPhoneno().equals(userPwdSearchDto.getPhoneno())) {
						return Optional.of(user);
					}
				}
			} catch (Exception e) {
				log.error("비밀번호 찾기 중 사용자 정보 확인 오류 - 사용자: {}", user.getUserid(), e);
			}
		}
		return Optional.empty();
	}

	/**
	 * 6자리 임시 비밀번호를 생성합니다.
	 * @return 생성된 임시 비밀번호
	 */
	public String generateRandomPassword() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++) {
			sb.append(characters.charAt(random.nextInt(characters.length())));
		}
		return sb.toString();
	}
}