package gradleProject.shop3.service;

import gradleProject.shop3.domain.User;
import gradleProject.shop3.repository.UserRepository;
import gradleProject.shop3.util.CipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		return userRepository.findById(userid).get();
	}

	public String decryptEmail(User user) {
		try{
			String hashId = CipherUtil.makehash(user.getUserid());
			String email = CipherUtil.decrypt(user.getEmail(), hashId);
			return email;
		}
		catch (Exception e) {
			System.err.println("이메일 복호화 오류");
			e.printStackTrace();
		}
		return null;
	}

//	public void userUpdate(User user) {
//		userdao.update(user);
//	}
//
//	public void userDelete(String userid) {
//		userdao.delete(userid);
//
//	}
//
//	public void updatePassword(String chgpass, String userid) {
//		userdao.updatePassword(chgpass,userid);
//	}
//
//	public String getSearch(User user) {
//		return userdao.search(user);
//	}


}
