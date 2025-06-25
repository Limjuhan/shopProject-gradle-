package gradleProject.shop3.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "saleitem")
@IdClass(SaleItemId.class)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class SaleItem {
	@Id
	private int saleid;

	@Id
	private int seq;

	private int itemid;
	private int quantity;

	@Transient
	private Item item; // 조회 시 직접 넣을 예정

	public SaleItem(int saleid, int seq, ItemSet itemSet) {
		this.saleid = saleid;
		this.seq = seq;
		this.item = itemSet.getItem();
		this.itemid = itemSet.getItem().getId();
		this.quantity = itemSet.getQuantity();
	}
}
