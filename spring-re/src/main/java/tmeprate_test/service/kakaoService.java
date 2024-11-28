package tmeprate_test.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import io.netty.handler.codec.http.HttpHeaderValues;

import org.springframework.http.HttpHeaders;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import tmeprate_test.domain.KakaoTokenResponseDto;
import tmeprate_test.domain.KakaoUserInfoResponseDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class kakaoService {

	
	private final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";
    private final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";
    private final WebClient.Builder webClientBuilder;

    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    public String getAccessTokenFromKakao(String code) {
        KakaoTokenResponseDto kakaoTokenResponseDto = webClientBuilder.baseUrl(KAUTH_TOKEN_URL_HOST)
                .build()
                .post()
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("code", code)
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .retrieve()
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();

        log.info("Access Token: {}", kakaoTokenResponseDto.getAccessToken());

        return kakaoTokenResponseDto.getAccessToken();
    }

    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
        KakaoUserInfoResponseDto userInfo = webClientBuilder.baseUrl(KAUTH_USER_URL_HOST)
                .build()
                .get()
                .uri("/v2/user/me")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // 액세스 토큰을 이용한 인증
                .retrieve()
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

        log.info("User Info: {}", userInfo);
        return userInfo;
    }
	
	/*
	 * private String clientId; private final String KAUTH_TOKEN_URL_HOST =
	 * "https://kauth.kakao.com"; private final String KAUTH_USER_URL_HOST =
	 * "https://kapi.kakao.com"; private final WebClient.Builder webClientBuilder;
	 * 
	 * @Autowired public kakaoService(@Value("${kakao.client_id}") String clientId,
	 * WebClient.Builder webClientBuilder) {
	 * 
	 * this.clientId = clientId; this.webClientBuilder = webClientBuilder; }
	 * 
	 * public KakaoUserInfoResponseDto getUserInfo(String accessToken) {
	 * KakaoUserInfoResponseDto userInfo =
	 * webClientBuilder.baseUrl(KAUTH_USER_URL_HOST) .build() .get() .uri(uriBuilder
	 * -> uriBuilder .scheme("https") .path("/v2/user/me") .build(true))
	 * .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) //엑세스 토큰 인가
	 * .header(HttpHeaders.CONTENT_TYPE,
	 * HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString()) .retrieve()
	 * //TODO : Custom Exxception .onStatus(HttpStatusCode::is4xxClientError,
	 * clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
	 * .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new
	 * RuntimeException("Internal ServerError")))
	 * .bodyToMono(KakaoUserInfoResponseDto.class) .block();
	 * 
	 * log.info("[Kakao Service ] Auth ID ---> {} ", userInfo.getId());
	 * log.info("[Kakao Service ] NickName ---> {} ",
	 * userInfo.getKakaoAccount().getProfile().getNickName());
	 * log.info("[Kakao Service ] ProfileImageUrl ---> {}",
	 * userInfo.getKakaoAccount().getProfile().getProfileImageUrl()); return
	 * userInfo; }
	 * 
	 * public String getAccessTokenFromKakao(String code) { KakaoTokenResponseDto
	 * kakaoTokenResponseDto = webClientBuilder.baseUrl(KAUTH_TOKEN_URL_HOST)
	 * .build() .post() .uri(uriBuilder -> uriBuilder .path("/oauth/token")
	 * .queryParam("grant_type", "authorization_code") .queryParam("client_id",
	 * clientId) .queryParam("code", code) .build())
	 * //.header(HttpHeaders.CONTENT_TYPE,
	 * HttpHeaderValues.APPLICATION_X_WWW_FROM_URLENCODED.toString())
	 * .header(org.springframework.http.HttpHeaders.CONTENT_TYPE,
	 * "application/x-www-form-urlencoded") .retrieve() //TODO : Custom Exception
	 * .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->Mono.error(new
	 * RuntimeException("Invalid Parameter")))
	 * .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->Mono.error(new
	 * RuntimeException("Internal Server Error")))
	 * .bodyToMono(KakaoTokenResponseDto.class) .block();
	 * 
	 * log.info("[Kakao Service] Access Token ------> {}",
	 * kakaoTokenResponseDto.getAccessToken());
	 * log.info("[Kakao Service] Refresh Token ------> {}",
	 * kakaoTokenResponseDto.getRefreshToken()); //제공 조건 : OpenID Connect가 활성화 된 앱의
	 * 토큰 발급 요청인 경우 또는 scope에 openid를 포함한 추가 항목 동의 받기 요청을 거친 토큰 발급 요청인 경우
	 * log.info(" [Kakao Service] Id Token ------> {}",
	 * kakaoTokenResponseDto.getIdToken());
	 * log.info(" [Kakao Service] Scope ------> {}",
	 * kakaoTokenResponseDto.getScope());
	 * 
	 * return kakaoTokenResponseDto.getAccessToken(); }
	 */
}
