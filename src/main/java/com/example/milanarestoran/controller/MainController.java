package com.example.milanarestoran.controller;

import com.example.milanarestoran.service.CategoryService;
import com.example.milanarestoran.service.DishesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {
    private final CategoryService categoryService;


    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "HomePage";
    }


}
