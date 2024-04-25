package com.prata.IA.controller;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/locations")
public class IAController {

    private final OpenAiChatClient chatClient;

    public IAController(OpenAiChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/travel")
    public String travelChat(@RequestParam(value = "message",
            defaultValue = "Recomenda Lugares bons para viajar?") String message){
        return chatClient.call(message);
    }

    @GetMapping("/reviews")
    public String bookstoreReview(@RequestParam(value = "location", defaultValue = "Porto Seguro - bahia") String location) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                  Por favor, me forneça
                  um breve resumo sobre esse local {local}
                  e os melhores pontos turisticos nesse local e região proxima.
                """);
        promptTemplate.add("location", location);
        return this.chatClient.call(promptTemplate.create()).getResult().getOutput().getContent();
    }

    @GetMapping("/stream/informations")
    public Flux<String> bookstoreChatStream(@RequestParam(value = "message",
            defaultValue = "Me informe bons motivos para realizar uma viagem e porque ir para Porto Seguro - bahia?") String message){
        return chatClient.stream(message);
    }
}