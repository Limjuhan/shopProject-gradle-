package gradleProject.shop3.service;

import gradleProject.shop3.domain.User;
import gradleProject.shop3.repository.UserRepository;
import gradleProject.shop3.util.CipherUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

//	@Value("${resources.dir}")
//	private String RESOURCES_DIR;

	@Autowired
	UserRepository userRepository;

	public void userInsert(User user) {
		userRepository.save(user);
	}

	public User selectUser(String userid) {
		User user = userRepository.findById(userid).get();
		System.err.println("user객체확인: " + user);
		System.out.println("email = " + user.getEmail());
		try{
			String hashId = CipherUtil.makehash(user.getUserid());
			String email = CipherUtil.decrypt(user.getEmail(), hashId);
			user.setEmail(email);
			return user;
		}
		catch (Exception e) {
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
