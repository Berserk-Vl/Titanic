package ru.sb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sb.model.Passenger;
import ru.sb.service.PassengerService;

import java.util.*;
import java.util.stream.Stream;

@RestController
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/passengers")
    ResponseEntity<Map<String, Object>> getPassengers(@RequestParam Map<String, String> queryParameters) {
        Stream<Passenger> passengerStream = passengerService.findAll().stream();
        if (queryParameters.containsKey("sort")) {
            switch (queryParameters.get("sort")) {
                case "name-asc" ->
                        passengerStream = passengerService.findAll().stream().sorted(Comparator.comparing(Passenger::getName));
                case "name-desc" ->
                        passengerStream = passengerService.findAll().stream().sorted((o1, o2) -> o2.getName().compareTo(o1.getName()));
                case "age-asc" ->
                        passengerStream = passengerService.findAll().stream().sorted(Comparator.comparingDouble(Passenger::getAge));
                case "age-desc" ->
                        passengerStream = passengerService.findAll().stream().sorted((o1, o2) -> o2.getAge().compareTo(o1.getAge()));
                case "fare-asc" ->
                        passengerStream = passengerService.findAll().stream().sorted(Comparator.comparingDouble(Passenger::getFare));
                case "fare-desc" ->
                        passengerStream = passengerService.findAll().stream().sorted((o1, o2) -> o2.getFare().compareTo(o1.getFare()));
            }
        }
        if (queryParameters.containsKey("survived")) {
            if (queryParameters.get("survived").equals("true")) {
                passengerStream = passengerStream.filter(Passenger::isSurvived);
            } else if (queryParameters.get("survived").equals("false")) {
                passengerStream = passengerStream.filter(passenger -> !passenger.isSurvived());
            }
        }
        if (queryParameters.containsKey("age")) {
            if (queryParameters.get("age").equals("adult")) {
                passengerStream = passengerStream.filter(passenger -> passenger.getAge() > 16);
            }
        }
        if (queryParameters.containsKey("sex")) {
            if (queryParameters.get("sex").equals("male")) {
                passengerStream = passengerStream.filter(passenger -> passenger.getSex().equals("male"));
            } else if (queryParameters.get("sex").equals("female")) {
                passengerStream = passengerStream.filter(passenger -> passenger.getSex().equals("female"));
            }
        }
        if (queryParameters.containsKey("relatives")) {
            switch (queryParameters.get("relatives")) {
                case "single" -> passengerStream = passengerStream.filter(
                        passenger -> passenger.getSiblingsSpouses() == 0 && passenger.getParentsChildren() == 0);
                case "siblings-spouses" -> passengerStream = passengerStream.filter(
                        passenger -> passenger.getSiblingsSpouses() > 0);
                case "parents-children" -> passengerStream = passengerStream.filter(
                        passenger -> passenger.getParentsChildren() > 0);
            }
        }
        Map<String, Object> map = new HashMap<>();
        List<Passenger> passengers = passengerStream.toList();
        double totalFare = 0;
        long totalHaveRelatives = 0;
        long totalSurvived = 0;
        long totalPassengers = passengers.size();
        for (Passenger passenger : passengers) {
            totalFare += passenger.getFare();
            totalHaveRelatives += passenger.getSiblingsSpouses() > 0 || passenger.getParentsChildren() > 0 ? 1 : 0;
            totalSurvived += passenger.isSurvived() ? 1 : 0;
        }
        if (queryParameters.containsKey("limit")) {
            try {
                int limit = Integer.parseInt(queryParameters.get("limit"));
                if (queryParameters.containsKey("offset")) {
                    int offset = Integer.parseInt(queryParameters.get("offset"));
                    passengers = passengers.stream().skip(offset).limit(limit).toList();
                } else {
                    passengers = passengers.stream().limit(limit).toList();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        map.put("passengers", passengers);
        map.put("total", Map.of("fare", totalFare, "haveRelatives", totalHaveRelatives,
                "survived", totalSurvived, "passengers", totalPassengers));
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/passengers/{name}")
    ResponseEntity<List<Passenger>> getPassengersByName(@PathVariable(value = "name") String name) {
        return new ResponseEntity<>(passengerService.findByName(name), HttpStatus.OK);
    }
}
