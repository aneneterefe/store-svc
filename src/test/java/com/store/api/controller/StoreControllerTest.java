package com.store.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.store.api.controller.model.ItemRequest;
import com.store.api.controller.model.OrderRequest;
import com.store.api.dto.ItemDto;
import com.store.api.dto.OrderDto;
import com.store.api.mappers.ItemResponseMapper;
import com.store.api.mappers.OrderResponseMapper;
import com.store.api.service.ItemServiceImpl;
import com.store.api.service.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderServiceImpl orderService;

    @MockBean
    private ItemServiceImpl itemService;

    @Spy
    private ItemResponseMapper itemResponseMapper;

    @Spy
    private OrderResponseMapper orderResponseMapper;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getAllItem() throws Exception{
        when(itemService.getAllItem()).thenReturn(Collections.singletonList(getItem()));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/store/api/items"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assertions.assertEquals("[{\"id\":1,\"name\":\"Apples\",\"price\":0.6,\"description\":\"Yummy Apples\",\"offerType\":null}]",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    void createItem() throws Exception {
        when(itemService.createItem(getItemRequest())).thenReturn(getItem());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/store/api/item")
                        .content("{\"name\":\"Apples\",\"price\":0.6,\"description\":\"Yummy Apples\"}")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assertions.assertEquals("{\"id\":1,\"name\":\"Apples\",\"price\":0.6,\"description\":\"Yummy Apples\",\"offerType\":null}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    void createOrder() throws Exception {
        when(orderService.createOrder(getOrderRequest())).thenReturn(getOrder());
        mockMvc.perform(MockMvcRequestBuilders.post("/store/api/order")
                        .content("[\n" +
                                "  {\n" +
                                "    \"item\": {\n" +
                                "    \"id\": 1,\n" +
                                "    \"name\": \"Apples\",\n" +
                                "    \"price\": 0.6,\n" +
                                "    \"description\": \"Yummy Apples\"\n" +
                                "  },\n" +
                                "  \"quantity\": 2\n" +
                                "  }\n" +
                                "]")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void getAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(getOrder()));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/store/api/orders"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assertions.assertEquals("[{\"id\":1,\"orderDate\":\"2021-11-20\",\"totalOrderPrice\":1.2,\"orderItems\":[{\"id\":1,\"quantity\":2,\"price\":1.2,\"item\":{\"id\":1,\"name\":\"Apples\",\"price\":0.6,\"description\":\"Yummy Apples\",\"offerType\":null},\"offer\":null}],\"totalOfferPrice\":null}]",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getOrderById() throws Exception {
        when(orderService.getOrder(Mockito.anyLong())).thenReturn(Optional.of(getOrder()));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/store/api/order")
                        .param("id","1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        Assertions.assertEquals("{\"id\":1,\"orderDate\":\"2021-11-20\",\"totalOrderPrice\":1.2,\"orderItems\":[{\"id\":1,\"quantity\":2,\"price\":1.2,\"item\":{\"id\":1,\"name\":\"Apples\",\"price\":0.6,\"description\":\"Yummy Apples\",\"offerType\":null},\"offer\":null}],\"totalOfferPrice\":null}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    void getOrderById_NotFound() throws Exception {
        when(orderService.getOrder(Mockito.anyLong())).thenReturn(Optional.empty());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/store/api/order")
                        .param("id","1"))
                .andExpect(status().isNotFound())
                .andReturn();
        Assertions.assertEquals("",
                mvcResult.getResponse().getContentAsString());
    }

    private ItemDto getItem() throws IOException {
        return objectMapper.readValue(
                new File("src/test/resources/item_response.json"),
                ItemDto.class);
    }

    private ItemRequest getItemRequest() throws IOException {
        return objectMapper.readValue(
                new File("src/test/resources/item_request.json"),
                ItemRequest.class);

    }

    private OrderDto getOrder() throws IOException {
        return objectMapper.readValue(
                new File("src/test/resources/order_response.json"),
                OrderDto.class);
    }

    private List<OrderRequest> getOrderRequest() throws IOException {
        return Arrays.asList(
                objectMapper.readValue(
                        new File("src/test/resources/order_request.json"),
                        OrderRequest[].class));

    }
}