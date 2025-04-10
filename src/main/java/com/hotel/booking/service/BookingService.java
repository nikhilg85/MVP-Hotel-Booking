package com.hotel.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.hotel.booking.request.Booking;


@Service
public class BookingService {
    private final Map<Long, Booking> bookingStore = new ConcurrentHashMap<>();
    
    private final AtomicLong bookingIdGenerator = new AtomicLong();

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookingStore.values());
    }

    public Booking getBooking(Long id) {
        return bookingStore.get(id);
    }

    public Booking createBooking(Booking booking) {
        Long id = bookingIdGenerator.incrementAndGet();
        booking.setId(id);
        bookingStore.put(id, booking);
        return booking;
    }

    public Booking updateBooking(Long id, Booking booking) {
        if (bookingStore.containsKey(id)) {
            booking.setId(id);
            bookingStore.put(id, booking);
            return booking;
        }
        throw new NoSuchElementException("Booking not found");
    }

    public void cancelBooking(Long id) {
        bookingStore.remove(id);
    }
}