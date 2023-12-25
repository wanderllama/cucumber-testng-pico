package jw.demo.models.auth;

import lombok.Data;

@Data
public class Token {

    String dp_refresh_token;
    String dp_user_data;
    String dp_access_token;
    String dp_user_permissions;
    String dp_rfl;

    public Token(String dp_refresh_token, String dp_user_data, String dp_access_token, String dp_user_permissions) {
        this.dp_refresh_token = dp_refresh_token;
        this.dp_user_data = dp_user_data;
        this.dp_access_token = dp_access_token;
        this.dp_user_permissions = dp_user_permissions;
        this.dp_rfl = "false";
    }

}
