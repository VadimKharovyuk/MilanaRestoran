package com.example.milanarestoran.service;

import com.example.milanarestoran.model.Booking;
import com.example.milanarestoran.repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;


    public Booking save (Booking booking){
       return bookingRepository.save(booking);
    }
    public Booking findById(Long id){
        return bookingRepository.findById(id).orElseThrow();
    }
    List<Booking> findAllBooking(Booking booking){
       return bookingRepository.findAll();
    }
    public void deleteById(Long id){
        bookingRepository.deleteById(id);
    }
}
