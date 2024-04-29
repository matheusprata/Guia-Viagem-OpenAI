package com.prata.IA.controller;

import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/locations")
public class IAController {

    private final OpenAiChatClient chatClient;
    private final List<String> visitedPlaces;

    public IAController(OpenAiChatClient chatClient) {
        this.chatClient = chatClient;
        this.visitedPlaces = new ArrayList<>();
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
                  um breve resumo sobre esse local {location}
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

    @PostMapping("/visited")
    public String addVisitedPlace(@RequestParam("place") String place) {
        visitedPlaces.add(place);
        return "Local visitado adicionado com sucesso!";
    }

    @GetMapping("/visited/recommendations")
    public String recommendPlaces() {
        if (visitedPlaces.isEmpty()) {
            return "Você ainda não visitou nenhum lugar.";
        } else {
            StringBuilder allVisitedPlacesBuilder = new StringBuilder();
            for (String visitedPlace : visitedPlaces) {
                allVisitedPlacesBuilder.append(visitedPlace).append("\n");
            }
            String allVisitedPlaces = allVisitedPlacesBuilder.toString();

            String prompt = "Recomende lugares semelhantes a:\n" + allVisitedPlaces;
            try {
                String recommendations = chatClient.call(prompt);
                return "Com base no seu último local visitado:\n" + allVisitedPlaces + "recomendamos os seguintes lugares semelhantes:\n" + recommendations;
            } catch (Exception e) {
                e.printStackTrace();
                return "Ocorreu um erro ao obter recomendações.";
            }
        }
    }

}