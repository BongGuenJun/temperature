package tmeprate_test.service;

import java.net.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tmeprate_test.domain.KakaoTokenResponseDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class kakaoService {

	private String clientId;
	private final String KAUTH_TOKEN_URL_HOST;
	private final String KAUTH_USER_URL_HOST;
	
	@Autowired
	public kakaoService(@Value("${kakao.client_id}") String clientId) {
		this.clientId = clientId;
		KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
		KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
	}
	
	public String getAccessTokenFromKakao(String code) {
		KakaoTokenResponseDto kakaoTokenResponseDto = WebClient.create(KAUTH_TOKEN_URL_HOST).post()
				.uri(uriiBuilder -> UriBuilder
						.scheme("https")
						.path("/oauth/token")
						.queryParam("grant_type", authorization_code)
						.queryParam("client_id", clientId)
						.queryParam("code", code)
						.buid(true))
				.header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FROM_URLENCODED.toString())
				.retrieve()
				//TODO : Custom Exception
				.onStatus(HttpStatusCode::is4xxClientError, clientResponse ->Mono.error(new RuntimeExcception("Invalid Parameter")))
				.onStatus(HttpStatusCode::is5xxServerError, clientResponse ->Mono.error(new RutimeException("Internal Server Error")))
				.bodyToMono(KakaoTokenResponseDto.class)
				.block();
				
		log.info("[Kakao Service] Access Token ------> {}", kakaoTokenResponseDto.getAccessToken());
		log.info("[Kakao Service] Refresh Token ------> {}", kakaoTokenResponseDto.getRefreshToken());
		//제공 조건 : OpenID Connect가 활성화 된 앱의 토큰 발급 요청인 경우 또는 scope에 openid를 포함한 추가 항목 동의 받기 요청을 거친 토큰 발급 요청인 경우
		log.info(" [Kakao Service] Id Token ------> {}", kakaoTokenResponseDto.getIdToken());
        log.info(" [Kakao Service] Scope ------> {}", kakaoTokenResponseDto.getScope());

        return kakaoTokenResponseDto.getAccessToken();
	}
}
