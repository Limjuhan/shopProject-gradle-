package gradleProject.shop3.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserPwdChgDto {

    @NotBlank(message = "현재 비밀번호는 필수 입력입니다.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호는 필수 입력입니다.")
    @Size(min = 4, max = 10, message = "비밀번호는 4자 이상 10자 이하로 입력해주세요.")
    private String newPassword;

    @NotBlank(message = "새 비밀번호 확인은 필수 입력입니다.")
    private String confirmNewPassword;
}