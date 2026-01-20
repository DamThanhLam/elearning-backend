package com.elearning.user.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveUserRequest {
    private String displayName;
    private String email;
    private String phone;
    private String role;
    private String password;
}
