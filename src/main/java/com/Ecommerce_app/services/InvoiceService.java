package com.Ecommerce_app.services;

import com.Ecommerce_app.entities.Order;
import com.Ecommerce_app.entities.OrderItem;
import com.Ecommerce_app.exception.ResourceNotFoundException;
import com.Ecommerce_app.repositories.OrderRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final OrderRepository orderRepository;

    public byte[] generateInvoice(
            Long orderId) throws Exception {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Order not found"));

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        Document document =
                new Document();

        PdfWriter.getInstance(
                document,
                out);

        document.open();

        document.add(
                new Paragraph(
                        "E-Commerce Invoice"));

        document.add(
                new Paragraph(
                        "Order ID : "
                                + order.getId()));

        document.add(
                new Paragraph(
                        "Customer : "
                                + order.getUser()
                                .getUsername()));

        document.add(
                new Paragraph(
                        "Status : "
                                + order.getStatus()));

        document.add(
                new Paragraph(
                        "Total Amount : ₹"
                                + order.getTotalAmount()));

        document.add(
                new Paragraph(" "));

        for (OrderItem item :
                order.getOrderItems()) {

            document.add(
                    new Paragraph(
                            item.getProduct()
                                    .getName()
                                    + " x "
                                    + item.getQuantity()
                                    + " = ₹"
                                    + (item.getPrice()
                                    * item.getQuantity())
                    ));
        }

        document.close();

        return out.toByteArray();
    }
}
