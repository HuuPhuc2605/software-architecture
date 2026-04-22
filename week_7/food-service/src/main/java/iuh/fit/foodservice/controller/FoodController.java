package iuh.fit.foodservice.controller;


import iuh.fit.foodservice.entity.Food;
import iuh.fit.foodservice.service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/foods")
@CrossOrigin
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    // GET /foods
    @GetMapping
    public List<Food> getAllFoods() {
        return foodService.getAllFoods();
    }

    // POST /foods
    @PostMapping
    public Food createFood(@RequestBody Food food) {
        return foodService.createFood(food);
    }

    // PUT /foods/{id}
    @PutMapping("/{id}")
    public Food updateFood(@PathVariable Integer id, @RequestBody Food food) {
        return foodService.updateFood(id, food);
    }

    // DELETE /foods/{id}
    @DeleteMapping("/{id}")
    public String deleteFood(@PathVariable Integer id) {
        foodService.deleteFood(id);
        return "Deleted successfully";
    }
}