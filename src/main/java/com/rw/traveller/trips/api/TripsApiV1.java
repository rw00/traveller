package com.rw.traveller.trips.api;

import com.rw.traveller.trips.model.Trip;
import com.rw.traveller.trips.service.TripsService;
import com.rw.traveller.trips.validation.NewEntityValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/trips")
class TripsApiV1 {
    private final TripsService tripsService;

    TripsApiV1(TripsService tripsService) {
        this.tripsService = tripsService;
    }

    @GetMapping
    List<Trip> getAll() {
        return tripsService.getAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Trip create(@RequestBody @Validated(NewEntityValidation.class) Trip trip) {
        return tripsService.create(trip);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteById(@PathVariable @NotNull @NotBlank String id) {
        tripsService.deleteById(id);
    }
}
