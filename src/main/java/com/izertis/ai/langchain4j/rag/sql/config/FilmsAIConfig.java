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
    public FilmsAgent filmsAgent(ChatLanguageModel openAiChatModel,
                                 ContentRetriever databaseContentRetriever) {

        return AiServices.builder(FilmsAgent.class)
                .chatLanguageModel(openAiChatModel)
                .contentRetriever(databaseContentRetriever)
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
                .sqlDialect("H2")
                .databaseStructure(
                        """
                        CREATE TABLE movie ( id VARCHAR(10) NOT NULL, title VARCHAR(200) DEFAULT NULL, release_year INT DEFAULT NULL, date_published DATE DEFAULT null, duration INT, country VARCHAR(250), worlwide_gross_income VARCHAR(30), languages VARCHAR(200), production_company VARCHAR(200), PRIMARY KEY (id) );
                        CREATE TABLE genre ( movie_id VARCHAR(10), genre VARCHAR(20), PRIMARY KEY (movie_id, genre) );
                        CREATE TABLE director_mapping ( movie_id VARCHAR(10), name_id VARCHAR(10), PRIMARY KEY (movie_id, name_id) );
                        CREATE TABLE role_mapping ( movie_id VARCHAR(10) NOT NULL, name_id VARCHAR(10) NOT NULL, category VARCHAR(10), PRIMARY KEY (movie_id, name_id) );
                        CREATE TABLE names ( id varchar(10) NOT NULL, name varchar(100) DEFAULT NULL, height int DEFAULT NULL, date_of_birth date DEFAULT null, known_for_movies varchar(100), PRIMARY KEY (id) );
                        CREATE TABLE ratings ( movie_id VARCHAR(10) NOT NULL, avg_rating DECIMAL(3,1), total_votes INT, median_rating INT, PRIMARY KEY (movie_id) );
                        """
                )
                .build();
    }

}
