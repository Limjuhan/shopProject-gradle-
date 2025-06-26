package gradleProject.shop3.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
@Builder
public class UserDisplayDto  {
    private String userid;
    private String channel;
    private String password;
    private String username;
    private String phoneno;
    private String postcode;
    private String address;
    private Date birthday;
    private String email; // 복호화된 이메일

}