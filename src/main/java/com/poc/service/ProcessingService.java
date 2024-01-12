package com.poc.service;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.poc.entity.Greetings;
import com.poc.repository.GreetingRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@ApplicationScoped
public class ProcessingService {

    @Inject
    GreetingRepository greetingRepository;
    private static final Logger logger = LogManager.getLogger(ProcessingService.class);

    public APIGatewayProxyResponseEvent process(APIGatewayProxyRequestEvent input) {
        String path = input.getPath();
        APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent= new APIGatewayProxyResponseEvent();
        switch (path){
            case "/create":
                Greetings greetings = new Greetings();
                greetings.greeting = input.getBody();
                greetings.name = input.getPath();
                try {
                    greetingRepository.persist(greetings);
                    logger.info("Successfully Inserted Data::: " + input.getBody());
                    apiGatewayProxyResponseEvent.setStatusCode(200);
                    apiGatewayProxyResponseEvent.setBody("Success");
                }
                catch (Exception ex){
                    logger.error(ex.getMessage() + " for ::: " + input.getBody());
                    apiGatewayProxyResponseEvent.setBody("Failed");
                    apiGatewayProxyResponseEvent.setStatusCode(500);
                }
                break;
            case "/list":
                try {
                    apiGatewayProxyResponseEvent.setBody(convertListToJson(greetingRepository.findAll().list()));
                    apiGatewayProxyResponseEvent.setStatusCode(200);
                    logger.info("Success for ::::: " + input.getBody());
                }
                catch (Exception ex){
                    logger.error("Failed :::: " + input.getBody());
                    apiGatewayProxyResponseEvent.setStatusCode(500);
                    apiGatewayProxyResponseEvent.setBody("Failed");
                }
                break;
        }
        return apiGatewayProxyResponseEvent;
    }

    private static String convertListToJson(List<Greetings> greetingsList) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(greetingsList);
    }
}
