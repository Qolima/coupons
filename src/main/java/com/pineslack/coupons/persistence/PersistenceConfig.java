package com.pineslack.coupons.persistence;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.lang.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
public class PersistenceConfig extends AbstractMongoClientConfiguration {

    @Value("${coupons.mongodb.url}")
    private String url;
    @Value("${coupons.mongodb.database}")
    private String database;

    @Override
    @NonNull
    public MongoClient mongoClient() {
        return MongoClients.create(
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(url + getDatabaseName()))
                        .build()
        );
    }

    @Override
    @NonNull
    protected String getDatabaseName() {
        return database;
    }
}
