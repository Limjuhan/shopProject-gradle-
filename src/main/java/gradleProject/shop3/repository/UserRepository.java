package gradleProject.shop3.repository;


import gradleProject.shop3.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {

}
