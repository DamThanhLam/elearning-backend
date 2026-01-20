package com.elearning.user.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SaveUserInformationRequest {
    private String displayName;
    private String phone;
}
