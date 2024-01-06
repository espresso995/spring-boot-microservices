package com.espresso.orderservice.service;

import com.espresso.orderservice.dto.OrderLineItemsDto;
import com.espresso.orderservice.dto.OrderRequest;
import com.espresso.orderservice.model.Order;
import com.espresso.orderservice.model.OrderLineItems;
import com.espresso.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::from)
                .toList();
        order.setOrderLineItemsList(orderLineItemsList);
        this.orderRepository.save(order);
    }

    private OrderLineItems from(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        return orderLineItems;
    }
}
