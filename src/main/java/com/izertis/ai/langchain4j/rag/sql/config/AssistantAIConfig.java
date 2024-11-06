package com.izertis.ai.langchain4j.rag.sql.config;

import com.izertis.ai.langchain4j.rag.sql.FilmsAgent;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class AssistantAIConfig {

    @Bean
    ApplicationRunner interactiveChatRunner(FilmsAgent filmsAgent) {

        return args -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {

                System.out.print("User: ");
                String userMessage = scanner.nextLine();

                if ("exit".equalsIgnoreCase(userMessage)) {
                    break;
                }

                String assistantMessage = filmsAgent.chat(userMessage);
                System.out.println("Assistant: " + assistantMessage);
            }
            scanner.close();
        };
    }

}
