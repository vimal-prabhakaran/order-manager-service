package com.pesto.ordermanagerservice.controller;

import com.pesto.ecomm.common.lib.dto.OrderDTO;
import com.pesto.ecomm.common.lib.dto.OrderListResponseDTO;
import com.pesto.ecomm.common.lib.dto.request.OrderCancellationRequestDTO;
import com.pesto.ordermanagerservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RequestMapping("/api/v1")
@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderDTO requestDTO) throws Exception {
        OrderDTO response = orderService.placeOrder(requestDTO);
        if (Objects.isNull(response))
            return new ResponseEntity<OrderDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<OrderDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable String orderId) {
        OrderDTO response = orderService.getOrder(orderId);
        if (Objects.isNull(response))
            return new ResponseEntity<OrderDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<OrderDTO>(response, HttpStatus.OK);
    }

    @PutMapping("/order/{orderId}/cancel")
    public ResponseEntity<OrderDTO> cancelOrder(@PathVariable String orderId, @RequestBody OrderCancellationRequestDTO requestDTO) {
        requestDTO.setOrderId(orderId);
        OrderDTO response = orderService.cancelOrder(requestDTO);
        if (Objects.isNull(response))
            return new ResponseEntity<OrderDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<OrderDTO>(response, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<OrderListResponseDTO> getAllOrders(@RequestParam String buyerId,
                                                             @RequestParam(required = false) Integer pageNo,
                                                             @RequestParam(required = false) Integer pageSize) {
        OrderListResponseDTO response = orderService.getAllOrders(buyerId, pageNo, pageSize);
        if (Objects.isNull(response))
            return new ResponseEntity<OrderListResponseDTO>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<OrderListResponseDTO>(response, HttpStatus.OK);
    }


}
