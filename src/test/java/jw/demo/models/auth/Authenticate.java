package jw.demo.models.auth;

import lombok.Data;

@Data
public class Authenticate {

    int resultType;
    String errorMessage;
    User user;

}
