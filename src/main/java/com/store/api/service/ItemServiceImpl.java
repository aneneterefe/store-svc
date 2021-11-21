package com.store.api.service;

import com.store.api.controller.model.ItemRequest;
import com.store.api.dto.ItemDto;
import com.store.api.entities.Item;
import com.store.api.exceptions.GeneralException;
import com.store.api.mappers.ItemDtoMapper;
import com.store.api.mappers.ItemModelMapper;
import com.store.api.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ItemDtoMapper itemDtoMapper;
    private final ItemModelMapper itemModelMapper;
    private static final String ERROR_MESSAGE = "Exception has occurred while accessing db";

    @Override
    public ItemDto createItem(ItemRequest itemRequest) {
        try {
            Item item = itemRepository.save(itemModelMapper.map(itemRequest, Item.class));
            return itemDtoMapper.map(item, ItemDto.class);
        } catch (Exception exception){
            throw new GeneralException(exception,ERROR_MESSAGE);
        }
    }

    @Override
    public List<ItemDto> getAllItem() {
        try {
            final List<Item> items = itemRepository.findAll();
            return items.stream()
                    .map(item -> itemDtoMapper.map(item, ItemDto.class))
                    .collect(Collectors.toList());
        } catch (Exception exception){
            throw new GeneralException(exception,ERROR_MESSAGE);
        }

    }
}
