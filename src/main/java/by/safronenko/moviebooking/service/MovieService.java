package by.safronenko.moviebooking.service;


import by.safronenko.moviebooking.entities.Booking;
import by.safronenko.moviebooking.entities.Movie;
/**
 * Интерфейс сервисного слоя приложения
 */
public interface MovieService {

    Movie getMovie();

    void addBooking(Booking booking);

    Booking checkBooking(int bookingId);

    void deleteBooking(int bookingId);
}
