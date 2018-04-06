package br.com.apontador.places.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;


@Configuration
@EnableWebMvc
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "br.com.apontador.places", excludeFilters = { @Filter(type = org.springframework.context.annotation.FilterType.ANNOTATION, value = Configuration.class) })
@EnableMongoRepositories(basePackages = "br.com.apontador.places.repository")
public class AppConfig extends AbstractMongoConfiguration 
{

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
	public MongoTemplate mongoTemplate() throws Exception {
	   return new MongoTemplate(mongo(), mongoDatabaseName);
	}
	
	@Bean 
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Override
	protected String getDatabaseName() {
		return mongoDatabaseName;
	}
	
	@Override
	protected String getMappingBasePackage() {
		return "br.com.apontador.places.repository";
	}
	
}
