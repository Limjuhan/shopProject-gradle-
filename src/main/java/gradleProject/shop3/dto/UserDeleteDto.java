package gradleProject.shop3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDeleteDto {

    private String userid;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Size(min = 3,max = 10, message = "비밀번호는 3자이상 10하로 입력하세요")
    private String password;
}
