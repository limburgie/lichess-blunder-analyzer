package be.webfactor.lcanalyzer;

import be.webfactor.lcanalyzer.service.AnalysisService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LCAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LCAnalyzerApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(AnalysisService analysisService) {
		return args -> analysisService.go();
	}
}
