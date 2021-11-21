package com.store.api.controller;

import com.store.api.controller.model.ItemRequest;
import com.store.api.controller.model.ItemResponse;
import com.store.api.controller.model.OrderRequest;
import com.store.api.controller.model.OrderResponse;
import com.store.api.dto.ItemDto;
import com.store.api.dto.OrderDto;
import com.store.api.mappers.ItemResponseMapper;
import com.store.api.mappers.OrderResponseMapper;
import com.store.api.service.ItemService;
import com.store.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/store/api")
public class StoreController {

    private final OrderService orderService;
    private final ItemService itemService;
    private final ItemResponseMapper itemResponseMapper;
    private final OrderResponseMapper orderResponseMapper;

    @GetMapping("/items")
    public ResponseEntity<List<ItemResponse>> getAllItems(){
        final List<ItemDto> items = itemService.getAllItem();
        List<ItemResponse> itemResponses = items.stream()
                .map(itemDto -> itemResponseMapper.map(itemDto, ItemResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(itemResponses, HttpStatus.OK);
    }

    @PostMapping("/items")
    public ResponseEntity<ItemResponse> createItem(@RequestBody ItemRequest itemRequest){
        final ItemDto item = itemService.createItem(itemRequest);
        return new ResponseEntity<>(
                itemResponseMapper.map(item, ItemResponse.class)
                , HttpStatus.CREATED
        );
    }

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody List<OrderRequest> orderRequest){
        final OrderDto order = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(
                orderResponseMapper.map(order, OrderResponse.class)
                , HttpStatus.CREATED
        );
    }
}
