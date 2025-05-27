package com.realworld.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseCleaner {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseCleaner(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public void cleanDatabase() {
        try {
            DatabaseMetaData metaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            
            List<String> truncateStatements = new ArrayList<>();
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                truncateStatements.add("TRUNCATE TABLE " + tableName + " CASCADE");
            }

            truncateStatements.forEach(jdbcTemplate::execute);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean database", e);
        }
    }
}