package by.safronenko.moviebooking.entities;

import org.springframework.stereotype.Component;

/**
 * Сущность "Бронирование"
 */
@Component
public class Booking {

    private int id;

    private String time;//время сеанса

    private int place;//забронированное место

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Booking booking = (Booking) o;

        return id == booking.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
