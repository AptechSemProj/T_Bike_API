package se.pj.tbike.api.order.conf;

import com.ank.japi.RequestHandler;
import com.ank.japi.impl.StdRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.api.order.data.OrderRepository;
import se.pj.tbike.api.order.data.OrderService;
import se.pj.tbike.api.order.dto.OrderRequest;
import se.pj.tbike.api.order.impl.OrderServiceImpl;
import se.pj.tbike.impl.ResponseConfigurerImpl;

@Configuration
public class OrderBeansRegistry {

    @Bean
    public OrderService orderService(OrderRepository repository) {
        return new OrderServiceImpl(repository);
    }

    @Bean
    public RequestHandler<OrderRequest, Long> createOrderHandler() {
        return new StdRequestHandler<>(
                ResponseConfigurerImpl::new
        );
    }
}
