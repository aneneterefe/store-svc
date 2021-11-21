package com.store.api.mappers;

import com.store.api.controller.model.OrderResponse;
import com.store.api.dto.OrderDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderResponseMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory mapperFactory){
        mapperFactory.classMap(OrderDto.class, OrderResponse.class)
                .byDefault()
                .register();
    }
}
