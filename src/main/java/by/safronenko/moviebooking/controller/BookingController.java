package by.safronenko.moviebooking.controller;

import by.safronenko.moviebooking.entities.Booking;
import by.safronenko.moviebooking.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Основной контроллер преложения
 */
@Controller
public class BookingController {

    @Autowired
    private MovieService movieService;

    //Возвращает основное представление
    @RequestMapping("/booking")
    public ModelAndView main() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("movie", movieService.getMovie());
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping("/")
    public String url() {
        return "redirect:/booking";
    }

    //Возвращает представление с выбранным временем сеанса
    @RequestMapping(value = "/session/{time}", method = RequestMethod.GET)
    public String book(@PathVariable("time") String time, Model model) {
        model.addAttribute("time", time);
        return "book";
    }

    //Добавление нового бронирования
    @RequestMapping(value = "/session/book/{time}", method = RequestMethod.GET)
    public String addBooking(@PathVariable("time") String time, @RequestParam int place, Model model) {
        if (place>100||place<1){
            return "redirect:/session/{time}";
        }
        //Проверка места на доступность
        if (movieService.checkPlace(time, place)){
            Booking booking = new Booking();
            int id = (int) (Math.random()*1000);//Генерирует id для каждого бронирования
            booking.setId(id);
            booking.setTime(time);
            booking.setPlace(place);
            //Вносим новое бронирование в систему
            movieService.addBooking(booking);

            //Возвращаем пользователю представление с номером бронирования
            model.addAttribute("id", id);
            return "success";
        }
        //Если место занято
        else return "taken";
    }

    //Возвращает представление с информацией о бронировании
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    public String check(@RequestParam int bookingId, Model model) {
        Booking booking = movieService.checkBooking(bookingId);
        model.addAttribute("booking", booking);
        return "check";
    }

    //Удаление бронирования, возврат на основное представление
    @RequestMapping(value = "/delete/{bookingId}")
    public String deleteBooking(@PathVariable("bookingId") int bookingId) {
        movieService.deleteBooking(bookingId);
        return "redirect:/booking";
    }

}
