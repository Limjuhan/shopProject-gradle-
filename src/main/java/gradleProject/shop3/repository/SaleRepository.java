package gradleProject.shop3.repository;


import gradleProject.shop3.domain.Sale;
import gradleProject.shop3.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale,Integer> {

    List<Sale> findByUserUserid(String userid);
}
