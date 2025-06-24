package gradleProject.shop3.domain;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
//@Entity
//@Table(name="board")
public class Board {

	@Id
	private int num;
	private String boardid;
	@NotBlank(message="작성자 입력하세요")
	private String writer;
	@NotBlank(message="비밀번호 입력하세요")
	private String pass;
	@NotBlank(message="제목 입력하세요")
	private String title;
	@NotBlank(message="내용 입력하세요")
	private String content;
	private MultipartFile file1;
	private String fileurl;
	private Date regdate;
	private int readcnt;
	private int grp;
	private int grplevel;
	private int grpstep;
	private int commentCnt;
}
