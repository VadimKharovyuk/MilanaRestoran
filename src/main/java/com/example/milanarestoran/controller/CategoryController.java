package com.example.milanarestoran.controller;

import com.example.milanarestoran.model.Category;
import com.example.milanarestoran.model.Dish;
import com.example.milanarestoran.service.CategoryService;
import com.example.milanarestoran.service.DishesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final DishesService dishesService;


    @GetMapping
    public String getAllCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories/list";
    }

    @GetMapping("/{id}")
    public String getCategoryById(@PathVariable Long id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);

        // Получаем блюда по категории
        List<Dish> dishes = dishesService.getDishesByCategoryId(id);
        model.addAttribute("dishes", dishes);

        return "categories/details";
    }

    @PostMapping
    public String createCategory(@ModelAttribute Category category) {
        categoryService.saveCategory(category);
        return "redirect:/categories";
    }


    @PostMapping("/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.getCategoryById(id);
        return "redirect:/categories";
    }
}
