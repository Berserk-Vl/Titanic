package ru.sb.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.sb.model.Passenger;
import ru.sb.model.PassengerRepository;

import java.util.List;

@Service
public class PassengerServiceImpl implements PassengerService {
    private PassengerRepository passengerRepository;

    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    @Override
    public List<Passenger> findByName(String name) {
        Passenger passenger = new Passenger();
        passenger.setName(name);
        return passengerRepository.findAll(Example.of(passenger,
                ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)));
    }
}
