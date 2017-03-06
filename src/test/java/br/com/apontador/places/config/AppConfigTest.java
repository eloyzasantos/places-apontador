package br.com.apontador.places.config;


import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@EnableMongoRepositories(basePackages = "br.com.apontador.places.repository")
@ComponentScan(basePackages = "br.com.apontador.places", excludeFilters = { @Filter(type = org.springframework.context.annotation.FilterType.ANNOTATION, value = Configuration.class) })
@PropertySource("classpath:application.properties")
public class AppConfigTest extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "places";
    }

	@Value("${mongo.database.name}")
	private String mongoDatabaseName;

	@Value("${mongo.uri}")
	private String mongoUri;

	@Bean
	public Mongo mongo() throws Exception {
		MongoClientURI clientUri = new MongoClientURI(mongoUri);
	    return new MongoClient(clientUri);
    }
	@Bean 
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
    
	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
	   return new MongoTemplate(mongo(), getDatabaseName());
	}

    @Override
    protected String getMappingBasePackage() {
        return "br.com.apontador.places.repository";
    }

}