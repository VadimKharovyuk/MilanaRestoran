package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Contact;
import com.example.milanarestoran.repository.ContactRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;


    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }

    public void deleteContactById(Long id) {
        contactRepository.deleteById(id);
    }

    public Contact updateContact(Long id, Contact updatedContact) {
        Optional<Contact> existingContact = contactRepository.findById(id);
        if (existingContact.isPresent()) {
            Contact contact = existingContact.get();
            contact.setName(updatedContact.getName());
            contact.setEmail(updatedContact.getEmail());
            contact.setMessage(updatedContact.getMessage());
            contact.setPhone(updatedContact.getPhone());
            contact.setPosition(updatedContact.getPosition());
            return contactRepository.save(contact);
        } else {
            return null;
        }
    }
}
