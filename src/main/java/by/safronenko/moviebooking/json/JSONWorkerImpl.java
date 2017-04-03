package by.safronenko.moviebooking.json;

import by.safronenko.moviebooking.entities.Booking;
import by.safronenko.moviebooking.entities.Movie;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Реализация интерфейса для работы с данными формата JSON
 */
@Repository
public class JSONWorkerImpl implements JSONWorker {

    private static final String MOVIEFILE = "movie.json";
    private static final String BOOKINGFILE= "bookings.json";
    private static final String TEMPFILE= "temp.json";//временный файл для работы метода deleteBooking()

    //Получение данных о названии фильма и времени сеансов из файла movie.json
    public Movie getMovie() {
        //Парсер библиотеки JSON.simple
        JSONParser parser = new JSONParser();
        Movie movie = new Movie();
        try {
            //Чтение данных в виде JSON объектов,
            //преобразование в Java объекты
            JSONObject object = (JSONObject) parser.parse(
                    new FileReader(MOVIEFILE));
            String name = (String) object.get("name");
            movie.setName(name);
            JSONArray s = (JSONArray) object.get("sessions");
            ArrayList<String> sessions = new ArrayList<>();
            Iterator<String> iterator = s.iterator();
            while(iterator.hasNext()) {
                sessions.add(iterator.next());
            }
            movie.setSessions(sessions);
        }
        catch (IOException | ParseException e) {
            Logger.getLogger(JSONWorkerImpl.class.getName())
                    .log(Level.SEVERE, null, e);
        }
        return movie;
    }

    //Добавление нового бронирования происходит путем перемещения
    // в конец файла и добавления нового объекта
    public void addBooking(Booking booking) {
        JSONObject object = new JSONObject();
        object.put("id", booking.getId());
        object.put("time", booking.getTime());
        object.put("place", booking.getPlace());

        try (RandomAccessFile file = new RandomAccessFile(BOOKINGFILE, "rw")){
            file.skipBytes((int)file.length()); //перемещение в конец файла
            file.writeBytes(object.toJSONString()+"\n");
            file.close();
        }
        catch (IOException ex) {
            Logger.getLogger(JSONWorkerImpl.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    //Проверка наличия брони в системе по id
    public Booking checkBooking(int bookingId) {
        JSONParser parser = new JSONParser();
        Booking booking = new Booking();

        int i =0;
        try{
            FileReader fileReader = new FileReader(BOOKINGFILE);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while(bufferedReader.readLine()!=null) {
                i++;//вычисление количества строк в файле
            }

            //Построчное чтение файла и получение значения id
            //Если необходимый id найден - возвращает Java объект,
            //преобразованный из JSON объекта
            Scanner sc;
            sc = new Scanner(new File(BOOKINGFILE));
            for (int j = 0; j < i; j++) {
                String str = sc.next();
                JSONObject object = (JSONObject) parser.parse(str);
                Long id = (Long) object.get("id");
                if (id==bookingId){
                    String time = (String) object.get("time");
                    Long place = (Long) object.get("place");
                    booking.setId(Math.toIntExact(id));
                    booking.setTime(time);
                    booking.setPlace(Math.toIntExact(place));
                }
            }

            sc.close();
            fileReader.close();
            bufferedReader.close();

        }catch(Exception ex){
            Logger.getLogger(JSONWorkerImpl.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

        return booking;
    }

    //Удаление бронирования по id
    //Алгоритм поиска такой же, как в методе checkBooking()
    //Происходит запись временного файла, в который не включается удаляемая бронь,
    //После чего происходит удаление старого bookings.json,
    //Временный файл переименовывается в bookings.json
    public void deleteBooking(int bookingId) {
        JSONParser parser = new JSONParser();
        StringBuilder stringBuilder = new StringBuilder();
        File temp = new File(TEMPFILE);
        File file = new File(BOOKINGFILE);

        int i =0;
        try{
            FileReader fileReader = new FileReader(BOOKINGFILE);
            FileWriter writer = new FileWriter(temp);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while(bufferedReader.readLine()!=null) {
                i++;
            }

            Scanner sc;
            sc = new Scanner(new File(BOOKINGFILE));
            for (int j = 0; j < i; j++) {
                String str = sc.next();
                JSONObject object = (JSONObject) parser.parse(str);
                Long id = (Long) object.get("id");
                if (id!=bookingId){
                    stringBuilder.append(str).append("\n");
                }
            }
            writer.write(stringBuilder.toString());
            sc.close();
            fileReader.close();
            writer.close();
            bufferedReader.close();

            file.delete();
            temp.renameTo(file);

        }catch(Exception ex){
            Logger.getLogger(JSONWorkerImpl.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
