package jw.demo.models.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtToken {

    String accessToken;
    String refreshToken;

}
