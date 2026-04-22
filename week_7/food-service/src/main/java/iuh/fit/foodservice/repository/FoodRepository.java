package iuh.fit.foodservice.repository;


import iuh.fit.foodservice.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Integer> {
}