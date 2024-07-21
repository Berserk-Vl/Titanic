package ru.sb.service;

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
}
