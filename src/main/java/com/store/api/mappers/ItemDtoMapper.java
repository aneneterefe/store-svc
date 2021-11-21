package com.store.api.mappers;

import com.store.api.entities.Item;
import com.store.api.dto.ItemDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemDtoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory mapperFactory){
        mapperFactory.classMap(Item.class, ItemDto.class)
                .byDefault()
                .register();
    }
}
