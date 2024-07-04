package com.example.milanarestoran.controller;

import com.example.milanarestoran.model.Booking;
import com.example.milanarestoran.model.Review;
import com.example.milanarestoran.model.User;
import com.example.milanarestoran.service.BookingService;
import com.example.milanarestoran.service.ReviewService;
import com.example.milanarestoran.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;
    private final ReviewService reviewService;

    @GetMapping
    public String getAllBookings(Model model) {
        List<Booking> bookings = bookingService.findAllBooking();
        model.addAttribute("bookings", bookings);
        return "booking-list";
    }

    @GetMapping("/{id}")
    public String getBookingById(@PathVariable Long id, Model model) {
        Booking booking = bookingService.findById(id);
        model.addAttribute("booking", booking);
        return "booking-detail";
    }

    @GetMapping("/new")
    public String createBookingForm(Model model) {
        List<User> users = userService.getAllUsers();
        // Помещаем список пользователей в модель
        model.addAttribute("users", users);
        model.addAttribute("booking", new Booking());
        model.addAttribute("review", new Review());
        return "booking-form";
    }

    @PostMapping
    public String saveBooking(@ModelAttribute("booking") Booking booking,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        // Проверка наличия ошибок валидации
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Пожалуйста, проверьте введенные данные.");
            return "redirect:/bookings/new";
        }

        bookingService.save(booking);
        return "booking-confirmation";
    }

    @PostMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteById(id);
        return "redirect:/bookings";
    }


    @PostMapping("/reviews")
    public String saveReview(@ModelAttribute("review") Review review, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("param", "Thank you for your review. We appreciate your feedback!");
        reviewService.save(review);
        return "redirect:/bookings/new";
    }

    @GetMapping("/all-reviews")
    public String reviewsList(Model model){
        model.addAttribute("reviews",reviewService.getAll());
        return "listreviews";

    }


}

