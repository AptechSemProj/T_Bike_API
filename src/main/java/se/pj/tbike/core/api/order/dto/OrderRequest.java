package se.pj.tbike.core.api.order.dto;

import com.ank.japi.Request;
import lombok.AllArgsConstructor;
import lombok.Getter;
import se.pj.tbike.core.api.orderdetail.dto.OrderDetailRequest;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderRequest implements Request<Long> {

    private Long id;

    private Long user;

    private String status;

    private List<OrderDetailRequest> details;

    public List<OrderDetailRequest> getDetails() {
        return details == null ? new ArrayList<>() : details;
    }
}
