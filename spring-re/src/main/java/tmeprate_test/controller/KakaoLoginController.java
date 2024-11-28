package tmeprate_test.controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tmeprate_test.domain.KakaoUserInfoResponseDto;
import tmeprate_test.service.kakaoService;

//restController 와 @Controller의 차이 공부할것

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {
	
	private final kakaoService kakaoService;
	
	@GetMapping("/callback")
	//위의 경우 코드를 받아올때 사용헀음 화면출력을 위해 밑의 코드로 변환
	//public ResponseEntity<?> callback(@RequestParam("code") String code, Model model) throws IOException{
	public String callback(@RequestParam("code") String code, Model model) throws IOException {
		//카카오로부터 받은 엑세스 토큰을 이용해 사용자 정보 가져오기
		String accessToken = kakaoService.getAccessTokenFromKakao(code);
		
		//엑세스 토큰을 이용해 사용자 정보 가져오기
		KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
		
		//사용자 정보를 Model에 추가하여 뷰로 전달
		model.addAttribute("userInfo", userInfo);
		
		log.info("User Info: {}", userInfo);
		//User 로그인, 또는 회원가입 로직 추가
		//return new ResponseEntity<>(HttpStatus.OK);
		return "user_info";
	}

}
