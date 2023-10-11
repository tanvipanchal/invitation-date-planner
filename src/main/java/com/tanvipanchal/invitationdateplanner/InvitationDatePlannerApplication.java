package com.tanvipanchal.invitationdateplanner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tanvipanchal.invitationdateplanner.model.Countries;
import com.tanvipanchal.invitationdateplanner.model.Partners;
import com.tanvipanchal.invitationdateplanner.service.InvitationPlanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class InvitationDatePlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvitationDatePlannerApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws JsonProcessingException {
		String uriGet = "https://candidate.hubteam.com/candidateTest/v3/problem/dataset?userKey=2b2170cb010698af4ae0d55d4e4e";
		Partners partners = restTemplate.getForObject(
				uriGet, Partners.class);

		System.out.print(partners);
		String uriPost = "https://candidate.hubteam.com/candidateTest/v3/problem/result?userKey=2b2170cb010698af4ae0d55d4e4e";

		InvitationPlanner invitationPlanner = new InvitationPlanner();

		Countries countries = invitationPlanner.getInvitationPlanPerCountry(partners);
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = mapper.writeValueAsString(countries);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(jsonString, headers);
		System.out.println("Output:");
		System.out.println(jsonString);

		return args -> {
			ResponseEntity<Countries> response = restTemplate.postForEntity(uriPost, entity, Countries.class);

			System.out.print(response.getBody().toString());
		};
	}
}
