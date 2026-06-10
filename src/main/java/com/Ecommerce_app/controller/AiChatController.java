package com.Ecommerce_app.controller;

import com.Ecommerce_app.Dtos.ChatRequest;
import com.Ecommerce_app.Dtos.ChatResponse;
import com.Ecommerce_app.services.AiChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class AiChatController {

    private final AiChatService service;

    @PostMapping
    public ChatResponse chat(
            @RequestBody ChatRequest request) {

        return new ChatResponse(
                service.chat(
                        request.getMessage()));
    }
}
