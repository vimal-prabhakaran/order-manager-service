package com.pesto.ordermanagerservice.service;

import com.pesto.ecomm.common.lib.dto.OrderDTO;
import com.pesto.ecomm.common.lib.dto.OrderListResponseDTO;
import com.pesto.ecomm.common.lib.dto.request.OrderCancellationRequestDTO;

public interface OrderService {

    OrderDTO placeOrder(OrderDTO orderDTO) throws Exception;

    OrderDTO getOrder(String orderId);

    OrderListResponseDTO getAllOrders(String buyerId, Integer pageNo, Integer pageSize);

    OrderDTO cancelOrder(OrderCancellationRequestDTO cancellationRequestDTO);

}
