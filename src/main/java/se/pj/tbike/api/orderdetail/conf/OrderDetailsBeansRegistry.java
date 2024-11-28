package se.pj.tbike.api.orderdetail.conf;

import com.ank.japi.RequestHandler;
import com.ank.japi.impl.StdRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import se.pj.tbike.api.orderdetail.data.OrderDetailRepository;
import se.pj.tbike.api.orderdetail.data.OrderDetailService;
import se.pj.tbike.api.orderdetail.dto.OrderDetailRequest;
import se.pj.tbike.api.orderdetail.impl.OrderDetailServiceImpl;
import se.pj.tbike.impl.ResponseConfigurerImpl;

@Configuration
public class OrderDetailsBeansRegistry {

    @Bean
    public OrderDetailService orderDetailService(
            OrderDetailRepository repository
    ) {
        return new OrderDetailServiceImpl(repository);
    }

    @Bean
    public RequestHandler<OrderDetailRequest, Long> orderDetailRequestHandler() {
        return new StdRequestHandler<>(ResponseConfigurerImpl::new);
    }
}
