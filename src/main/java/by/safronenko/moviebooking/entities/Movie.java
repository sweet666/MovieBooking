package by.safronenko.moviebooking.entities;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * Сущность "Фильм"
 */
@Component
public class Movie {

    private String name;

    private ArrayList<String> sessions = new ArrayList<>();//коллекция сеансов

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSessions() {
        return sessions;
    }

    public void setSessions(ArrayList<String> sessions) {
        this.sessions = sessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        return name.equals(movie.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
