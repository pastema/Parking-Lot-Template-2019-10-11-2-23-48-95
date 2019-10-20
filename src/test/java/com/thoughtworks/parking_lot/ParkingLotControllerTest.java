package com.thoughtworks.parking_lot;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.controller.ParkingLotController;
import com.thoughtworks.parking_lot.core.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
@ActiveProfiles(profiles = "test")
public class ParkingLotControllerTest {

    @MockBean
    private ParkingLotService parkingLotService;


    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void should_delete_parkingLot() throws Exception {
        ParkingLot parkingLot = new ParkingLot("ParkingLot1", 1, "WMALL");
        when(parkingLotService.delete(any())).thenReturn(parkingLot);

        ResultActions result = mvc.perform(delete("/parkingLot/ParkingLot1"));

        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void should_get_all_parking_lot() throws Exception {
        List<ParkingLot> parkingLot = new ArrayList<>();
        parkingLot.add(new ParkingLot("ParkingLot1", 1, "WMALL"));

        when(parkingLotService.findAll()).thenReturn(parkingLot);

        ResultActions result = mvc.perform(get("/parkingLot/all"));

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("ParkingLot1")));
    }


    @Test
    void should_add_parking_lot() throws Exception {
        ParkingLot parkingLot = new ParkingLot("ParkingLot1", 1, "WMALL");

        when(parkingLotService.save(parkingLot)).thenReturn(parkingLot);

        ResultActions result = mvc.perform(post("/parkingLot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingLot)));

        result.andExpect(status().isCreated())
                .andDo(print());
    }
}
