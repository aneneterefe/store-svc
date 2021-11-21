package com.store.api.mappers;

import com.store.api.entities.Order;
import com.store.api.dto.OrderDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderDtoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory mapperFactory){
        mapperFactory.classMap(Order.class, OrderDto.class)
                .byDefault()
                .register();
    }
}
