package by.safronenko.moviebooking.service;


import by.safronenko.moviebooking.entities.Booking;
import by.safronenko.moviebooking.entities.Movie;
import by.safronenko.moviebooking.json.JSONWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация интерфейса сервисного слоя приложения
 */
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private JSONWorker jsonWorker;

    @Transactional
    public Movie getMovie() {
        return jsonWorker.getMovie();
    }

    @Transactional
    public void addBooking(Booking booking) {
        jsonWorker.addBooking(booking);
    }

    @Transactional
    public Booking checkBooking(int bookingId) {
        return jsonWorker.checkBooking(bookingId);
    }

    @Transactional
    public void deleteBooking(int bookingId) {
        jsonWorker.deleteBooking(bookingId);
    }

    public boolean checkPlace(String time, int place) {
        if (jsonWorker.checkPlace(time, place)){
            return true;
        }
        else return false;
    }

}
