package com.backend.springboot.template.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.stereotype.Component;

@Component
public class MongoDbFactoryConnection {
	
	private final MongoDbFactory mongo;
	
	@Autowired
	public MongoDbFactoryConnection(MongoDbFactory mongo) {
		this.mongo = mongo;
	}

	public MongoDbFactory getMongo() {
		return mongo;
	}
}
