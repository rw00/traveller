package com.rw.traveller.trips.model;

import com.rw.traveller.trips.validation.NewEntityValidation;
import com.rw.traveller.trips.validation.ValidDestination;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Trip {
    @Null(groups = NewEntityValidation.class)
    private String id;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    @ValidDestination
    private String destination;
    @NotNull
    @FutureOrPresent
    private LocalDate startDate;
    @NotNull
    @Future
    private LocalDate endDate;
    private String imageUrl; // optional
}
