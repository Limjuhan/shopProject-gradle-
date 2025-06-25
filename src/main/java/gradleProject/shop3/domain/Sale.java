package gradleProject.shop3.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = "itemList") // toString에서 StackOverflowError 방지
public class Sale {
	@Id
	private int saleid;
	private Date saledate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	private User user;

	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@Transient
	private List<SaleItem> itemList = new ArrayList<>();

	public int getTotal() {
		return itemList.stream()
				.mapToInt(s -> s.getItem().getPrice() * s.getQuantity())
				.sum();
	}
}