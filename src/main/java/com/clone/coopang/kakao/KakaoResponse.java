package com.clone.coopang.kakao;

import lombok.Data;

@Data
public class KakaoResponse{

    String token_type;
    String access_token;
    int expires_in;
    String refresh_token;
    int refresh_token_expires_in;
    String scope;
}
