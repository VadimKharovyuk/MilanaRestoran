package com.example.milanarestoran.controller;
import com.example.milanarestoran.model.Contact;
import com.example.milanarestoran.service.ContactService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/contact")
@AllArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping()
    public String contactForm(Model model) {
        List<String> positions = Arrays.asList(
                "ОФИЦИАНТ", "БАРМЕН", "АДМИНИСТРАТОР", "ШЕФ_ПОВАР", "ПОВАР",
                "ХОСТЕС", "ПОСУДОМОЙЩИК", "КОНДИТЕР", "УБОРЩИК", "ОХРАННИК"
        );
        model.addAttribute("positions", positions);
        model.addAttribute("contact",new Contact());
        return "contactForm";
    }


    @PostMapping("/save")
    public String saveContact(Contact contact, RedirectAttributes redirectAttributes) {
        contactService.saveContact(contact);
        redirectAttributes.addFlashAttribute("successMessage","Спасибо за сообщение! Ваш запрос получен, ожидайте нашего ответа.");
        return "redirect:/contact";
    }

    @GetMapping("/all")
    public String showAllContacts(Model model) {
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        return "allContacts";
    }
    @PostMapping("/remove/{id}")
    public String deleteContact(@PathVariable Long id){
        contactService.deleteContactById(id);
        return "redirect:/contact/all";
    }
}

