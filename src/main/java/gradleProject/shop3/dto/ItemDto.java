package gradleProject.shop3.dto;

import gradleProject.shop3.domain.Item;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;


@Getter
@Setter
public class ItemDto {

	private int id;
	private String name;
	private int price;
	private String description;
	private String pictureUrl;
	private MultipartFile picture;

	// 장바구니 비교를 위해 id만 기준으로 equals/hashCode 오버라이드
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Item)) return false;
		Item item = (Item) o;
		return id == item.getId();
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
