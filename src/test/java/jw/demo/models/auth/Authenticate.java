package jw.demo.models.auth;

import jw.demo.util.Log;
import lombok.Data;

@Data
public class Authenticate {

    int resultType;
    String errorMessage;
    User user;

    public User getUser() {
        if (user == null) {
            try {
                throw new NullPointerException();
            } catch (NullPointerException e) {
                throw new NullPointerException(Log.exceptionErrorMsg("User obj has not been created", e));
            }
        }
        return user;
    }
}
