package com.clone.coopang.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class KakaoLoginService {

    private final RestTemplate restTemplate;

    private final String GRANT_TYPE = "authorization_code";
    private final String REDIRECT_URI = "http://localhost:8080/kakao/code";
    private final String ACCESS_TOKEN_URI = "https://kauth.kakao.com/oauth/token";
    private final String USER_INFO_URI = "https://kapi.kakao.com//v2/user/me";

    @Value("${oauth.kakao.client_id}")
    private String clientId;

    public ResponseEntity<KakaoResponse> getKakaoToken(String code){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", GRANT_TYPE);
        map.add("client_id", clientId);
        map.add("redirect_uri", REDIRECT_URI);
        map.add("code", code);
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(map, headers);
        ResponseEntity<KakaoResponse> kakaoTokenResponse = restTemplate.postForEntity(ACCESS_TOKEN_URI, kakaoTokenRequest, KakaoResponse.class);
        return kakaoTokenResponse;
    }

    /*
    curl -v -X POST "https://kauth.kakao.com/oauth/token" \
    -H "Content-Type: application/x-www-form-urlencoded" \
    -d "grant_type=authorization_code" \
    -d "client_id=${REST_API_KEY}" \
    --data-urlencode "redirect_uri=${REDIRECT_URI}" \
     -d "code=${AUTHORIZE_CODE}"
     */
}
