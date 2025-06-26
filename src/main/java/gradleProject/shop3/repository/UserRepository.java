package gradleProject.shop3.repository;


import gradleProject.shop3.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByUserid(String userId);

    List<User> findByPhoneno(String phoneno);
}
