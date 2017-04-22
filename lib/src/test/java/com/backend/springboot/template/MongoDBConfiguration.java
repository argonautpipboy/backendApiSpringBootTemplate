package com.backend.springboot.template;

import com.backend.springboot.template.mongodb.repositories.RepositoryPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;

@Configuration
@EnableMongoRepositories(basePackageClasses = RepositoryPackage.class)
public class MongoDBConfiguration extends AbstractMongoConfiguration{

	@Bean
	public MongoTemplate mongoTemplate() throws Exception{
		return new MongoTemplate(mongo(), getDatabaseName());
	}

	@Override
	protected String getDatabaseName() {
		return "apibackendspringboottemplate";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		MongoClient client = new MongoClient("localhost");
        client.setWriteConcern(WriteConcern.SAFE);
        return client;
	}
}
