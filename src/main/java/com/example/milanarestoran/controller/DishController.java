package com.example.milanarestoran.controller;

import com.example.milanarestoran.model.Category;
import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.service.CategoryService;
import com.example.milanarestoran.service.DishesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dishes")
@AllArgsConstructor
public class DishController {

    private final DishesService dishService;
    private final CategoryService categoryService;


    @GetMapping
    public String getAllDishes(Model model) {
        List<Dish> dishes = dishService.getAllDishes();
        model.addAttribute("dishes", dishes);
        return "dishes/list";
    }

    @GetMapping("/{id}")
    public String getDishById(@PathVariable Long id, Model model) {
        Dish dish = dishService.getDishById(id);
        model.addAttribute("dish", dish);
        return "dishes/details";
    }

    @PostMapping()
    public String createDish(@ModelAttribute Dish dish) {
        dishService.saveDish(dish);
        return "redirect:/dishes";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("dish", new Dish());
        model.addAttribute("categories", categoryService.getAllCategories()); // Add categories to the model
        return "dishes/createDish";
    }


    @PostMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
