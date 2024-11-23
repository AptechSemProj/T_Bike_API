package se.pj.tbike.core.api.order.conf;

import com.ank.japi.RequestHandler;
import com.ank.japi.impl.StdRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.core.api.order.data.OrderRepository;
import se.pj.tbike.core.api.order.data.OrderService;
import se.pj.tbike.core.api.order.dto.OrderMapper;
import se.pj.tbike.core.api.order.dto.OrderRequest;
import se.pj.tbike.core.api.order.impl.OrderMapperImpl;
import se.pj.tbike.core.api.order.impl.OrderServiceImpl;
import se.pj.tbike.core.japi.impl.ResponseConfigurerImpl;

@Configuration
public class OrderBeansRegistry {

    @Bean
    public OrderService orderService(OrderRepository repository) {
        return new OrderServiceImpl(repository);
    }

    @Bean
    public OrderMapper orderMapper() {
        return new OrderMapperImpl();
    }

    @Bean
    public RequestHandler<OrderRequest, Long> createOrderHandler() {
        return new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        );
    }
}
