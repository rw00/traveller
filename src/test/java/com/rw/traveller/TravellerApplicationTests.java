package com.rw.traveller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rw.traveller.trips.model.Trip;
import com.rw.traveller.trips.repository.TripEntity;
import com.rw.traveller.trips.repository.TripsRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
class TravellerApplicationTests {

    @Container
    static final PostgreSQLContainer<?> POSTGRES_DB = new PostgreSQLContainer<>("postgres:16.6")
            .withDatabaseName("app-db")
            .withUsername("root")
            .withPassword("secret");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", POSTGRES_DB::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", POSTGRES_DB::getUsername);
        propertyRegistry.add("spring.datasource.password", POSTGRES_DB::getPassword);
    }

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    TripsRepository tripsRepository;

    @Test
    void testCreateTrip() throws Exception {
        // given
        LocalDate today = LocalDate.now();
        Trip trip = Trip.builder()
                        .name("Vacation")
                        .destination("Amsterdam")
                        .startDate(today.plusMonths(2))
                        .endDate(today.plusMonths(3)).build();
        String requestContent = objectMapper.writeValueAsString(trip);

        // when
        MvcResult mvcResult = mockMvc.perform(post("/v1/trips")
                                                      .content(requestContent)
                                                      .contentType(MediaType.APPLICATION_JSON)
                                                      .accept(MediaType.APPLICATION_JSON))
                                     .andExpect(status().isCreated())
                                     .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();

        // then
        Trip createdTrip = objectMapper.readValue(responseContent, Trip.class);
        Optional<TripEntity> optionalTrip = tripsRepository.findById(createdTrip.getId());
        Assertions.assertTrue(optionalTrip.isPresent());
        Assertions.assertEquals("Amsterdam", optionalTrip.get().getDestination());
    }
}
