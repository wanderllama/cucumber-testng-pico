package jw.demo.models.auth;

import lombok.Data;

import java.util.List;

@Data
public class User {

    int id;
    String firstName;
    String lastName;
    String username;
    String emailAddress;
    String departmentId;
    String leadIdHash;
    String isLeadOnboardingCompleted;
    String isTaxEmployee;
    List<String> roles;
    List<String> permissions;
    JwtToken jwtToken;

}
