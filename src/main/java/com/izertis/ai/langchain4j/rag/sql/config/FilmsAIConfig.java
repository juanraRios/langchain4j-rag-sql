package com.izertis.ai.langchain4j.rag.sql.config;

import com.izertis.ai.langchain4j.rag.sql.FilmsAgent;
import dev.langchain4j.experimental.rag.content.retriever.sql.SqlDatabaseContentRetriever;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FilmsAIConfig {

    @Bean
    public FilmsAgent FilmsAgent(ChatLanguageModel openAiChatModel,
                                 DataSource dataSource) {

        ContentRetriever contentRetriever = SqlDatabaseContentRetriever.builder()
                .dataSource(dataSource)
                .chatLanguageModel(openAiChatModel)
                .build();

        return AiServices.builder(FilmsAgent.class)
                .chatLanguageModel(openAiChatModel)
                .contentRetriever(contentRetriever)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build();
    }

    @Bean
    ChatLanguageModel openAiChatModel(@Value("${langchain4j.open-ai.chat-model.api-key}") final String apiKey,
                                      @Value("${langchain4j.open-ai.chat-model.model-name}") final String modelName) {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .build();
    }

    @Bean
    ContentRetriever databaseContentRetriever(DataSource dataSource, ChatLanguageModel chatLanguageModel) {

        return SqlDatabaseContentRetriever.builder()
                .dataSource(dataSource)
                .chatLanguageModel(chatLanguageModel)
                .build();
    }

}
