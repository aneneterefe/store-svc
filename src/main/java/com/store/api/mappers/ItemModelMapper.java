package com.store.api.mappers;

import com.store.api.controller.model.ItemRequest;
import com.store.api.entities.Item;
import com.store.api.dto.ItemDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemModelMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory mapperFactory){
        mapperFactory.classMap(ItemDto.class, Item.class)
                .byDefault()
                .register();

        mapperFactory.classMap(ItemRequest.class, Item.class)
                .byDefault()
                .register();

    }
}
