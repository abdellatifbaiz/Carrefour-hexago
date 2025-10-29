package com.carrefour.kata.adapters.in;



import com.carrefour.kata.adapters.dtos.OrderResponseDto;
import com.carrefour.kata.adapters.mappers.OrderMapper;
import com.carrefour.kata.domain.model.Order;
import com.carrefour.kata.domain.port.in.GenerateOrderInstallenemtCase;
import com.carrefour.kata.domain.port.in.GetAllOrdersCase;
import com.carrefour.kata.domain.port.in.UpdateOrderCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final GenerateOrderInstallenemtCase generateOrderInstallenemt;
    private final UpdateOrderCase updateOrdercase;
    private final GetAllOrdersCase getAllOrdersCase;
    private final OrderMapper orderMapper;

    @RequestMapping("/generate")
    public ResponseEntity<OrderResponseDto> generate(@RequestBody OrderResponseDto orderResponseDto) {
        Order order = orderMapper.fromDto(orderResponseDto);
        OrderResponseDto responseDto = orderMapper.toDto(generateOrderInstallenemt.generateOrderInstallenemt(order));
        return ResponseEntity.ok(responseDto);
    }

    @RequestMapping("/updateOrder")

    public ResponseEntity<OrderResponseDto> updateOrder(@RequestBody OrderResponseDto orderResponseDto) {
        Order order = orderMapper.fromDto(orderResponseDto);
        OrderResponseDto responseDto = orderMapper.toDto(updateOrdercase.updateOrderInstallenemt(order));
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("findAll")
    public ResponseEntity<Page<OrderResponseDto>> findAllOrders(@RequestParam int page,@RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders =  getAllOrdersCase.getAllOrders(pageable);
        Page<OrderResponseDto> responseDto = orders.map(orderMapper::toDto);
        return ResponseEntity.ok(responseDto);
    }
}

