package by.safronenko.moviebooking.json;


import by.safronenko.moviebooking.entities.Booking;
import by.safronenko.moviebooking.entities.Movie;

/**
 * Интерфейс для работы с данными формата JSON
 */
public interface JSONWorker {

    Movie getMovie();

    void addBooking(Booking booking);

    Booking checkBooking(int bookingId);

    void deleteBooking(int bookingId);

}
