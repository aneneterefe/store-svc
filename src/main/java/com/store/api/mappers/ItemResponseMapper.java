package com.store.api.mappers;

import com.store.api.controller.model.ItemResponse;
import com.store.api.dto.ItemDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class ItemResponseMapper  extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory mapperFactory){
        mapperFactory.classMap(ItemDto.class, ItemResponse.class)
                .byDefault()
                .register();
    }
}
