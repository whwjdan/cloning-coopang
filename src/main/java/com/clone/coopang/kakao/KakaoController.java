package com.clone.coopang.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class KakaoController {

    private KakaoLoginService kakaoLoginService;

    @Autowired
    public KakaoController(KakaoLoginService kakaoLoginService){
        this.kakaoLoginService = kakaoLoginService;
    }

    @RequestMapping("/kakao")
    public String kakaoRequestCode(@RequestParam(value = "code", required = false) String code){
        return "request_kakao_auth_code";
    }

    @RequestMapping("/kakao/code")
    @ResponseBody
    public ResponseEntity<KakaoResponse> kakaoRequestToken(@RequestParam(value = "code", required = false) String code){
        ResponseEntity<KakaoResponse> token = kakaoLoginService.getKakaoToken(code);
        return token;
    }
}