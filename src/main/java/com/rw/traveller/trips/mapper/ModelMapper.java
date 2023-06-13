package com.rw.traveller.trips.mapper;

import com.rw.traveller.trips.model.Trip;
import com.rw.traveller.trips.repository.TripEntity;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {
    public Trip fromEntity(TripEntity tripEntity) {
        return Trip.builder()
                   .id(tripEntity.getId())
                   .name(tripEntity.getName())
                   .destination(tripEntity.getDestination())
                   .startDate(tripEntity.getStartDate())
                   .endDate(tripEntity.getEndDate())
                   .imageUrl(tripEntity.getImageUrl())
                   .build();
    }

    public TripEntity toEntity(Trip trip) {
        TripEntity tripEntity = new TripEntity();
        tripEntity.setId(trip.getId());
        tripEntity.setName(trip.getName());
        tripEntity.setDestination(trip.getDestination());
        tripEntity.setStartDate(trip.getStartDate());
        tripEntity.setEndDate(trip.getEndDate());
        tripEntity.setImageUrl(trip.getImageUrl());
        return tripEntity;
    }
}
