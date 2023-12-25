package jw.demo.models.auth;

import lombok.Data;

@Data
public class JwtToken {

    String accessToken;
    String refreshToken;

}
