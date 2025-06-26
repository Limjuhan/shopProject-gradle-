package gradleProject.shop3.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailDto {

    private String googleid;

    private String googlepw;

    private String recipient; // 쉼표로 구분된 받는 사람 이메일 주소

    @NotEmpty(message = "제목을 입력하세요")
    private String title;

    private String mtype; // 메시지 타입 (text/html 또는 text/plain)

    private List<MultipartFile> files;

    @NotEmpty(message = "내용을 입력하세요")
    private String contents;
}