package com.pesto.ordermanagerservice.service.impl;

import com.pesto.ecomm.common.lib.builder.MetaDataDTOBuilder;
import com.pesto.ecomm.common.lib.dto.OrderItemDTO;
import com.pesto.ecomm.common.lib.dto.OrderItemListResponse;
import com.pesto.ecomm.common.lib.dto.request.OrderStatusUpdateRequestDTO;
import com.pesto.ecomm.common.lib.entity.OrderItem;
import com.pesto.ecomm.common.lib.enums.OrderStatus;
import com.pesto.ecomm.common.lib.repository.OrderItemRepository;
import com.pesto.ordermanagerservice.service.SellerOrderService;
import com.pesto.ordermanagerservice.builder.OrderDTOBuilder;
import com.pesto.ordermanagerservice.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class SellerOrderServiceImpl implements SellerOrderService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderDTOBuilder orderDTOBuilder;

    @Autowired
    MetaDataDTOBuilder metaDataDTOBuilder;

    @Override
    public OrderItemListResponse getAllSellerOrders(String sellerId, Integer pageNo, Integer pageSize) {
        OrderItemListResponse responseDTO = new OrderItemListResponse();
        pageNo = Objects.isNull(pageNo) ? 0 : pageNo;
        pageSize = Objects.isNull(pageSize) ? 10 : pageSize;
        Page<OrderItem> orderItemPage = orderItemRepository.findAllBySeller_UserId(sellerId, PageRequest.of(pageNo, pageSize));
        List<OrderItemDTO> orderItemDTOS = orderDTOBuilder.buildItems(orderItemPage.getContent());
        responseDTO.setOrderItems(orderItemDTOS);
        responseDTO.setMetaData(metaDataDTOBuilder.build(orderItemPage));
        return responseDTO;
    }

    @Transactional
    @Override
    public OrderItemDTO updateOrderStatus(OrderStatusUpdateRequestDTO requestDto) {
        OrderItem orderItem = orderItemRepository.findById(requestDto.getOrderItemId()).orElseThrow(() ->
                new OrderNotFoundException("Invalid order ID!"));
        OrderStatus orderStatus = OrderStatus.valueOf(requestDto.getStatus());
        orderItem.setStatus(orderStatus);
        orderItem = orderItemRepository.save(orderItem);
        return orderDTOBuilder.buildItem(orderItem);
    }
}
