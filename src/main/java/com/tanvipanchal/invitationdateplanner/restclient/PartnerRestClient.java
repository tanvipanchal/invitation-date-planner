package com.tanvipanchal.invitationdateplanner.restclient;

import com.tanvipanchal.invitationdateplanner.model.Countries;
import com.tanvipanchal.invitationdateplanner.model.Partners;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PartnerRestClient {
    private final String uriGet = "https://candidate.hubteam.com/candidateTest/v3/problem/dataset?userKey=2b2170cb010698af4ae0d55d4e4e";
    private final String uriPost = "https://candidate.hubteam.com/candidateTest/v3/problem/dataset?userKey=2b2170cb010698af4ae0d55d4e4e";
    private final RestTemplate restTemplate;

    public PartnerRestClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Partners getListOfPartners() {
        Partners partners = restTemplate.getForObject(
                uriGet, Partners.class);

        return partners;
    }

    public HttpStatusCode postInvitationPlanPerCountry()
    {
        ResponseEntity<Countries> response = restTemplate.postForEntity(uriPost, null, Countries.class);
        HttpStatusCode statusCode = response.getStatusCode();
        String restCall = response.getBody().toString();
        System.out.println(restCall);

        return statusCode;
    }
}