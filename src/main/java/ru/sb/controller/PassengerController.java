package ru.sb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.sb.model.Passenger;
import ru.sb.service.PassengerService;

import java.util.List;

@RestController
public class PassengerController {
    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping("/passengers")
    ResponseEntity<List<Passenger>> getPassengers() {
        return new ResponseEntity<>(passengerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/passengers/{name}")
    ResponseEntity<List<Passenger>> getPassengersByName(@PathVariable(value = "name") String name) {
        return new ResponseEntity<>(passengerService.findByName(name), HttpStatus.OK);
    }
}
