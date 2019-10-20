package com.thoughtworks.parking_lot;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.controller.OrdersController;
import com.thoughtworks.parking_lot.core.Orders;
import com.thoughtworks.parking_lot.service.OrdersService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrdersController.class)
@ActiveProfiles(profiles = "test")
public class OrdersControllerTest {

    @MockBean
    private OrdersService ordersService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

//    @Test
//    void should_change_order_status() throws Exception {
//        Orders orders = new Orders("ParkingLot2", "123aa", "2019-10-20 13:02:01",
//                null, "Open");
//        when(ordersService.update(orders)).thenReturn(orders);
//
//        ResultActions result = mvc.perform(patch("/parkingLot/ParkingLot2/orders?plateNumber=2"));
//
//        result.andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$", hasSize(1)))
//                .andExpect(jsonPath("$.orderStatus", is("Close")));
//    }


    @Test
    void should_get_all_orders() throws Exception {
        List<Orders> ordersList = new ArrayList<>();
        ordersList.add(new Orders("ParkingLot2", "123aa", "2019-10-20 13:02:01",
                null, "Open"));

        when(ordersService.findAll()).thenReturn(ordersList);

        ResultActions result = mvc.perform(get("/parkingLot/all/orders"));

        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].parkingLotName", is("ParkingLot2")));
    }

}
