package br.com.apontador.places.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "br.com.apontador.places")
@EnableWebMvc
public class AppConfig
{

	@Value("${mongo.database.name}")
	private String mongoDatabaseName;

	@Value("${mongo.database.host}")
	private String mongohost;
	
	@Value("${mongo.database.port}")
	private String port;

	public Mongo mongo() throws Exception {
		
		String uri = "mongodb://%s:%s/%s";
		
		MongoClientURI clientUri = new MongoClientURI(String.format(uri, mongohost, port, mongoDatabaseName));
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
}
