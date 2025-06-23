package gradleProject.shop3.domain;

import gradleProject.shop3.logic.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // @Componet + DAO 관련기능. 객체화
public interface ItemRepository extends JpaRepository<Item, Integer> {

	List<Item> findAll();

}
