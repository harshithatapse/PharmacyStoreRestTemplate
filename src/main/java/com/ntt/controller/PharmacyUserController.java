package com.ntt.controller;

import java.net.URI;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.ntt.configuration.RestResponsePage;
import com.ntt.exception.UserNotFoundException;
import com.ntt.model.User;

@RestController
@RequestMapping("/pharmacyuser")
public class PharmacyUserController {
	
	@Autowired
	private RestTemplate restTemplate;
	Logger logger = LoggerFactory.getLogger(PharmacyUserController.class);
	
	@GetMapping(value = "/allUsers")
	public void getAllUsersByExchange() {
		URI targetUrl = UriComponentsBuilder.fromUriString("http://localhost:9092").path("/user").path("/")
				.build().toUri();
		ParameterizedTypeReference<RestResponsePage<User>> responsePage = new ParameterizedTypeReference<RestResponsePage<User>>() {
		};
		ResponseEntity<RestResponsePage<User>> response = restTemplate.exchange(targetUrl, HttpMethod.GET, null,
				responsePage);
		if (Objects.isNull(response)) {
			logger.error("error found in getAllUsersByExchange");
			throw new UserNotFoundException("No Users Found.!");
		}

		System.out.println(response.getBody());
		Page<User> pageOfUser = response.getBody();
		logger.info("ShopNowAdmin getAllUsersByExchange() response{}", pageOfUser);
		
		System.out.println("Size " + pageOfUser.getSize());
		System.out.println(pageOfUser.getContent());
		List<User> userList = pageOfUser.getContent();
		System.out.println("Users List Data" + userList);
		/*
		 * for (User temp : userList) { if (temp.getRolename().contains("Admin"))
		 * System.out.println(temp); }
		 */
	}

}
