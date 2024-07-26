package ru.sb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import ru.sb.model.Passenger;
import ru.sb.model.PassengerRepository;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
@EnableCaching
public class TitanicApplication implements CommandLineRunner {
    @Autowired
    private PassengerRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(TitanicApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0 && args[0].equals("-l")) {
            loadData(repository);
        }
    }

    /**
     * Loads data from the given URL and stores it into database.
     */
    private static void loadData(PassengerRepository passengerRepository) {
        try {
            URL url = new URL("https://web.stanford.edu/class/archive/cs/cs109/cs109.1166/stuff/titanic.csv");
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = reader.readLine();
            List<Passenger> passengers = new LinkedList<>();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                passengers.add(new Passenger(
                        data[0].equals("1"),
                        Passenger.Class.values()[data[1].charAt(0) - '0' - 1],
                        data[2],
                        data[3],
                        Float.parseFloat(data[4]),
                        Short.parseShort(data[5]),
                        Short.parseShort(data[6]),
                        Double.parseDouble(data[7])
                ));
            }
            passengerRepository.saveAllAndFlush(passengers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
