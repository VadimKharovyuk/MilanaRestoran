package com.example.milanarestoran.controller;

import com.example.milanarestoran.model.Category;
import com.example.milanarestoran.model.Dish;
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

    @PostMapping
    public String createDish(@RequestBody Dish dish) {
        dishService.saveDish(dish);
        return "redirect:/dishes";
    }


    @PostMapping("/{id}")
    public ResponseEntity<Void> deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
