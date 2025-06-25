package gradleProject.shop3.service;

import gradleProject.shop3.domain.User;
import gradleProject.shop3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

//	@Value("${resources.dir}")
//	private String RESOURCES_DIR;

	@Autowired
	UserRepository userRepository;

	public void userInsert(User user) {
		userRepository.save(user);
	}

//	public User selectUser(String userid) {
//		return userdao.selectOne(userid);
//	}
//
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
