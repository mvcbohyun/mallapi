package com.kinsight.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.kinsight.properties.KakaoProperties;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/kakao/")
@RequiredArgsConstructor
public class KakaoController {

	private final KakaoProperties kakaoProperties;
	
	@GetMapping("/search")
	public String search(@RequestParam String query) {
		
		System.out.println("kakaoProperties.getKakao_RESTAPI() : "+kakaoProperties.getKakao_RESTAPI());
		Mono<String>mono= WebClient.builder().baseUrl("dapi.kakao.com")
		.build().get().uri(builder -> builder.path("/v2/local/search/address.json")
				.queryParam("query", query)
				.build()
				).header("Authorization", "KakaoAK "+ kakaoProperties.getKakao_RESTAPI())
				 .exchangeToMono(response ->{
					 return response.bodyToMono(String.class);
				 });
		
		return mono.block();
		
	}
}
