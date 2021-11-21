package com.store.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.store.api.controller.model.ItemRequest;
import com.store.api.dto.ItemDto;
import com.store.api.dto.OrderDto;
import com.store.api.entities.Item;
import com.store.api.entities.Order;
import com.store.api.exceptions.GeneralException;
import com.store.api.mappers.ItemDtoMapper;
import com.store.api.mappers.ItemModelMapper;
import com.store.api.repository.ItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private ItemRepository itemRepository;

    @Spy
    private ItemDtoMapper itemDtoMapper;

    @Spy
    private ItemModelMapper itemModelMapper;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Create Item: Success")
    void create_item_success() throws IOException {
        ItemRequest itemRequest = getItemRequest();
        when(itemRepository.save(Mockito.any())).thenReturn(getItem());
        ItemDto item = itemService.createItem(itemRequest);
        Assertions.assertNotNull(item);
        Assertions.assertEquals(item.getPrice(), itemRequest.getPrice());
        Assertions.assertEquals(item.getName(), itemRequest.getName());
    }

    @Test
    @DisplayName("Create Item: Success")
    void get_order_success() throws IOException {
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(getItem()));
        List<ItemDto> items = itemService.getAllItem();
        Assertions.assertNotNull(items);
        Assertions.assertTrue(items.size()>0);
    }

    private Item getItem() throws IOException {
        return objectMapper.readValue(
                new File("src/test/resources/item_response.json"),
                Item.class);
    }

    private ItemRequest getItemRequest() throws IOException {
        return objectMapper.readValue(
                        new File("src/test/resources/item_request.json"),
                        ItemRequest.class);

    }
}