package com.elearning.user.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class VerifyForgotPasswordRequest {
    private String email;
    private String newPassword;
    private String otp;
}
