package com.miracle.itx.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ITXInvoker {

	public static String transform(String input,String MapName) {

		String host = "23.99.137.255";
		String port = "8080";

		String baseURL = "http://" + host + ":" + port
				+ "/tx-rest/v1/itx/maps/direct/"+MapName+"?output=1";

		RestTemplate restTemplate = new RestTemplate();

		Map<Integer, String> params = new HashMap<Integer, String>();
		params.put(1, input);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("1", input);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		ITXStandardResponse itxStandardResponse = restTemplate
				.postForEntity(baseURL, requestEntity, ITXStandardResponse.class).getBody();
		System.out.println(itxStandardResponse);

		while (true) {
			String message = restTemplate.getForObject(itxStandardResponse.getHref(), String.class);
			System.out.println(message);
			if (message.contains("Map completed successfully")) {
				System.out.println(message);
				break;
			}
		}
		String response = restTemplate.getForObject(itxStandardResponse.getHref().replace("status", "outputs/1"),
				String.class);
		System.out.println(response);
		return response;
	}

}
