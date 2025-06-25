package gradleProject.shop3.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Sale {
	@Id
	private int saleid;

	private Date saledate;

	private String userid; // 외래키 직접 들고 있기

	@Transient
	private List<SaleItem> itemList = new ArrayList<>();

	public int getTotal() {
		return itemList.stream()
				.filter(s -> s.getItem() != null)
				.mapToInt(s -> s.getItem().getPrice() * s.getQuantity())
				.sum();
	}
}
