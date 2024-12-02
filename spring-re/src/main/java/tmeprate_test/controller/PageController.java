package tmeprate_test.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/login"})
public class PageController {

    @Value("${kakao.client_id}")
    private String client_id;
    
    @Value("${kakao.redirect_uri}")
    private String redirect_uri;

    @GetMapping({"/", "/page"})
    public String loginPage(Model model) throws UnsupportedEncodingException {
        // URL 인코딩을 통해 안전하게 파라미터 처리
        String encodedRedirectUri = URLEncoder.encode(redirect_uri, "UTF-8");
        
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code"
                + "&client_id=" + client_id 
                + "&redirect_uri=" + encodedRedirectUri;
        
        model.addAttribute("location", location);
        
        return "login";  // login.html로 리턴
    }
}