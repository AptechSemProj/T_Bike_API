package se.pj.tbike.core.api.orderdetail.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.core.api.orderdetail.data.OrderDetailRepository;
import se.pj.tbike.core.api.orderdetail.data.OrderDetailService;
import se.pj.tbike.core.api.orderdetail.dto.OrderDetailMapper;
import se.pj.tbike.core.api.orderdetail.impl.OrderDetailMapperImpl;
import se.pj.tbike.core.api.orderdetail.impl.OrderDetailServiceImpl;

@Configuration
public class OrderDetailsBeansRegistry {

    @Bean
    public OrderDetailService orderDetailService(
            OrderDetailRepository repository
    ) {
        return new OrderDetailServiceImpl(repository);
    }

    @Bean
    public OrderDetailMapper detailMapper() {
        return new OrderDetailMapperImpl();
    }

}
