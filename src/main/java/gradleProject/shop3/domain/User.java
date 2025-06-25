package gradleProject.shop3.domain;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity(name = "Usercipher")
@Table(name = "usercipher")
@Getter
@Setter
@ToString
public class User {
	@Id
	private String userid;
	private String channel;
	private String password;
	private String username;
	private String phoneno;
	private String postcode;
	private String address;
	private String email;
	private Date birthday;

}
