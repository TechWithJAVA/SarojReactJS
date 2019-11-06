package com.yash.es.config;

import static springfox.documentation.builders.PathSelectors.regex;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@PropertySource("classpath:application.properties")
public class EsConfig{

	
	
	    @Value("${elasticsearch.host}")
	    private String host;

	    @Value("${elasticsearch.port}")
	    private int port;

	    @Value("${elasticsearch.username}")
	    private String userName;

	    @Value("${elasticsearch.password}")
	    private String password;
	    
	    @Value("${elasticsearch.indexname}")
	    private String indexname;
	    
	    @Value("${elasticsearch.indextype}")
	    private String indextype;
	
	 public String getHost() {
			return host;
		}

		public void setHost(String host) {
			this.host = host;
		}

		public int getPort() {
			return port;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getIndexname() {
			return indexname;
		}

		public void setIndexname(String indexname) {
			this.indexname = indexname;
		}

		public String getIndextype() {
			return indextype;
		}

		public void setIndextype(String indextype) {
			this.indextype = indextype;
		}
		

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.yash.es.controller")).paths(regex("/employees.*"))
				.build();
	}

	@Bean(destroyMethod = "close")
	public RestHighLevelClient getClient() {
		RestHighLevelClient client = null;
		try {
			client= new RestHighLevelClient(RestClient.builder(new HttpHost(host, 9200, "http")));
			// new HttpHost("localhost", 9201, "http")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return client;
	}
	 
	/* @Bean(destroyMethod = "close")
	    public RestHighLevelClient restClient() {

	        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
	       // credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, password));

	        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port))
	                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider));

	        RestHighLevelClient client = new RestHighLevelClient(builder);


	        return client;

	    }*/

	@Bean
	public Gson getGson() {

		Gson gson = null;
		try {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.serializeNulls();
			gson = gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
		} catch (Exception e) {
			// TODO: handle exception
		}

		return gson;

	}
}
