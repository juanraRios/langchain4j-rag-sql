package com.izertis.ai.langchain4j.rag.sql.config;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Configuration
public class DatasourceConfig {

    /**
     * Configures and returns datasource for database
     *
     * @param url      the database url
     * @param user     the database user
     * @param password the database password
     * @param script   the path for database creation script
     * @return the dataSource configured.
     */
    @Bean
    DataSource dataSource(@Value("${dataSource.url}") final String url,
                          @Value("${dataSource.user}") final String user,
                          @Value("${dataSource.password}") final String password,
                          @Value("${dataSource.script}") final String script) {

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);

        String createTablesScript = read(script);
        execute(createTablesScript, dataSource);

        return dataSource;
    }

    /**
     * Reads data of SQL script file from the path provided.
     *
     * @param path the relative path of SQL file.
     * @return the file content like string.
     */
    private String read(String path) {
        try {
            return new String(Files.readAllBytes(toPath(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Convert relative path to Path object.
     *
     * @param relativePath the relative path of resource.
     * @return the path object.
     */
    private Path toPath(String relativePath) {
        try {
            URL fileUrl = DatasourceConfig.class.getClassLoader().getResource(relativePath);
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Execute several SQL sentences separated by semicolons.
     *
     * @param sql        the SQL sentences to execute.
     * @param dataSource the datasource.
     */
    private void execute(String sql, DataSource dataSource) {
        try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {
            for (String sqlStatement : sql.split(";")) {
                statement.execute(sqlStatement.trim());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
