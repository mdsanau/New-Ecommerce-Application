package com.Ecommerce_app.controller;

import com.Ecommerce_app.Dtos.OrderResponse;
import com.Ecommerce_app.Dtos.UpdateOrderStatusRequest;
import com.Ecommerce_app.entities.Order;
import com.Ecommerce_app.services.InvoiceService;
import com.Ecommerce_app.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final InvoiceService invoiceService;


    @PostMapping("/checkout")
    public OrderResponse checkout(

            Authentication authentication,

            @RequestParam
            Long addressId) {

        return orderService.checkout(
                authentication.getName(),
                addressId);
    }

    @GetMapping
    public List<Order> getOrders(
            Authentication authentication) {

        return orderService.getOrders(
                authentication.getName());
    }

    @GetMapping("/{orderId}")
    public Order getOrderDetails(
            @PathVariable Long orderId) {

        return orderService
                .getOrderDetails(orderId);
    }

    @PutMapping("/{orderId}/cancel")
    public String cancelOrder(
            @PathVariable Long orderId) {

        return orderService.cancelOrder(
                orderId);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateStatus(

            @PathVariable Long orderId,

            @RequestBody
            UpdateOrderStatusRequest request) {

        return ResponseEntity.ok(
                orderService.updateOrderStatus(
                        orderId,
                        request.getStatus()));
    }

    @PutMapping("/{orderId}/ship")
    public ResponseEntity<String> shipOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.shipOrder(
                        orderId));
    }

    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<String> deliverOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(
                orderService.deliverOrder(
                        orderId));
    }

//    http://localhost:8080/orders/4/invoice
    @GetMapping("/{orderId}/invoice")
    public ResponseEntity<byte[]>
    downloadInvoice(
            @PathVariable Long orderId)
            throws Exception {

        byte[] pdf =
                invoiceService
                        .generateInvoice(
                                orderId);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=invoice-"
                                + orderId
                                + ".pdf")
                .contentType(
                        MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
