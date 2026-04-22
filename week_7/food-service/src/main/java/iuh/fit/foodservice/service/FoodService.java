package iuh.fit.foodservice.service;

import iuh.fit.foodservice.entity.Food;
import iuh.fit.foodservice.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    // GET ALL
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    // CREATE
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }

    // UPDATE
    public Food updateFood(Integer id, Food newFood) {
        return foodRepository.findById(id).map(food -> {
            food.setName(newFood.getName());
            food.setDescription(newFood.getDescription());
            food.setPrice(newFood.getPrice());
            food.setImage(newFood.getImage());
            return foodRepository.save(food);
        }).orElseThrow(() -> new RuntimeException("Food not found"));
    }

    // DELETE
    public void deleteFood(Integer id) {
        foodRepository.deleteById(id);
    }
}