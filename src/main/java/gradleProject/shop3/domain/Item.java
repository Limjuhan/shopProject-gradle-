package gradleProject.shop3.domain;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name="item")
@Data
public class Item {
	@Id
	private int id;
	// @NotEmpty : null 또는 공백인 경우 오류 인식
	@NotEmpty(message = "상품명을 입력하세요")
	private String name;
	@Min(value = 10,message = "10원이상 가능합니다")
	@Max(value = 100000, message = "10만원이하 가능합니다")
	private int price;
	@NotEmpty(message = "상품설명을 입력하세요")
	private String description;
	private String pictureUrl;
	@Transient // 컬럼과 무관한 프로퍼티
	private MultipartFile picture; // 업로드 된 파일 저장
	
	// 장바구니 비교를 위해 id만 기준으로 equals/hashCode 오버라이드
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
	
}
