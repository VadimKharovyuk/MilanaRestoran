package com.example.milanarestoran.controller;

import com.example.milanarestoran.model.Category;
import com.example.milanarestoran.model.Discount;
import com.example.milanarestoran.repository.CategoryRepository;
import com.example.milanarestoran.repository.DiscountRepository;
import com.example.milanarestoran.service.DiscountService;
import com.example.milanarestoran.service.DishesService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/discounts")
public class DiscountController {

    private final DiscountRepository discountRepository;
    private  final  CategoryRepository categoryRepository;
    private final DiscountService discountService;

    @GetMapping("/new")
    public String showCreateDiscountForm(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "discounts/create_discount";
    }

    ///пример в процента %

//    @PostMapping("/new")
//    public String createDiscount(@RequestParam("categoryId") Long categoryId,
//                                 @RequestParam("amount") BigDecimal amount,
//                                 @RequestParam("startDate") String startDate,
//                                 @RequestParam("endDate") String endDate) {
//        Discount discount = new Discount();
//        discount.setCategory(categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new RuntimeException("Category not found")));
//        discount.setAmount(amount);
//        discount.setStartDate(LocalDate.parse(startDate));
//        discount.setEndDate(LocalDate.parse(endDate));
//
//        discountRepository.save(discount);
//
//        return "redirect:/discounts";
//    }

    //пример фиксированй скидки для товаров
@PostMapping("/new")
public String createDiscount(@RequestParam("categoryId") Long categoryId,
                             @RequestParam("amount") BigDecimal amount,
                             @RequestParam("startDate") String startDate,
                             @RequestParam("endDate") String endDate) {
    Discount discount = new Discount();
    discount.setCategory(categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found")));
    discount.setAmount(amount);
    discount.setStartDate(LocalDate.parse(startDate));
    discount.setEndDate(LocalDate.parse(endDate));

    discountRepository.save(discount);

    return "redirect:/discounts";
}



    @GetMapping()
    public String listDiscounts(Model model) {
        List<Discount> discounts = discountRepository.findAll();
        model.addAttribute("discounts", discounts);
        return "discounts/list_discounts";
    }
    @PostMapping("/delete/{id}")
    public String deleteDiscount(@PathVariable Long id){
        discountService.delete(id);
        return "redirect:/discounts";

    }
}
