package ru.sb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sb.model.Passenger;
import ru.sb.service.PassengerService;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@RestController
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping("/passengers")
    ResponseEntity<List<Passenger>> getPassengers(@RequestParam Map<String, String> queryParameters) {
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
            if(queryParameters.get("age").equals("adult")){
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
                        passenger -> passenger.getSiblingSpouses() == 0 && passenger.getParentsChildren() == 0);
                case "siblings-spouses" -> passengerStream = passengerStream.filter(
                        passenger -> passenger.getSiblingSpouses() > 0);
                case "parents-children" -> passengerStream = passengerStream.filter(
                        passenger -> passenger.getParentsChildren() > 0);
            }
        }
        return new ResponseEntity<>(passengerStream.toList(), HttpStatus.OK);
    }

    @GetMapping("/passengers/{name}")
    ResponseEntity<List<Passenger>> getPassengersByName(@PathVariable(value = "name") String name) {
        return new ResponseEntity<>(passengerService.findByName(name), HttpStatus.OK);
    }
}
