package com.store.api.service;

import com.store.api.controller.model.ItemRequest;
import com.store.api.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto createItem(ItemRequest itemRequest);
    List<ItemDto> getAllItem();
}
