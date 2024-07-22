package ru.sb.service;

import ru.sb.model.Passenger;

import java.util.List;

public interface PassengerService {
    List<Passenger> findAll();

    List<Passenger> findByName(String name);
}
