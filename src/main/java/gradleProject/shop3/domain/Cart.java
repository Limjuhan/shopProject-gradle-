package gradleProject.shop3.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private List<ItemSet> itemSetList = new ArrayList<>();

    public List<ItemSet> getItemSetList() {
        return itemSetList;
    }

    // 같은 아이템이면 수량 누적
    public void addItem(Item item, int quantity) {
        for (ItemSet set : itemSetList) {
            if (set.getItem().equals(item)) {
                set.setQuantity(set.getQuantity() + quantity);
                return;
            }
        }
        itemSetList.add(new ItemSet(item, quantity));
    }
 
    public void push(ItemSet itemSet) {
        itemSetList.add(itemSet);
    }

    public int getTotal() {
        return itemSetList.stream()
                .mapToInt(set -> set.getItem().getPrice() * set.getQuantity())
                .sum();
    }
}
