package com.rw.traveller.trips.service;

import com.rw.traveller.trips.mapper.ModelMapper;
import com.rw.traveller.trips.model.Trip;
import com.rw.traveller.trips.repository.TripEntity;
import com.rw.traveller.trips.repository.TripsRepository;
import java.util.List;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TripsService {
    private final TripsRepository tripsRepository;
    private final ModelMapper modelMapper;

    public TripsService(TripsRepository tripsRepository, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.tripsRepository = tripsRepository;
    }

    @Transactional(readOnly = true)
    public List<Trip> getAll() {
        return Streamable.of(tripsRepository.findAll())
                         .map(modelMapper::fromEntity)
                         .toList();
    }

    @Transactional
    public Trip create(Trip trip) {
        TripEntity tripEntity = modelMapper.toEntity(trip);
        TripEntity savedTripEntity = tripsRepository.save(tripEntity);
        return modelMapper.fromEntity(savedTripEntity);
    }

    @Transactional
    public void deleteById(String id) {
        tripsRepository.deleteById(id);
    }
}
