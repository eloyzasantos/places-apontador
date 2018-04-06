package br.com.manager.places.config;

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

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;


@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "br.com.manager.places", excludeFilters = { @Filter(type = org.springframework.context.annotation.FilterType.ANNOTATION, value = Configuration.class) })
@EnableMongoRepositories(basePackages = "br.com.manager.places.repository")
public class AppConfigTest extends AbstractMongoConfiguration 
{

	@Value("${mongo.database.name}")
	private String mongoDatabaseName;

	@Bean
	public Mongo mongo() throws Exception {
		return new Fongo(getDatabaseName()).getMongo();
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
		return "br.com.manager.places.repository";
	}
	
}