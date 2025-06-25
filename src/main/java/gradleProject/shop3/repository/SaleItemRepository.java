package gradleProject.shop3.repository;


import gradleProject.shop3.domain.Sale;
import gradleProject.shop3.domain.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleItemRepository extends JpaRepository<SaleItem,Integer> {


    List<SaleItem> findBySaleid(int saleid);
}
