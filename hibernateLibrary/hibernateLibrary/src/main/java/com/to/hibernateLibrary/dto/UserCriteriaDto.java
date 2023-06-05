package com.to.hibernateLibrary.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCriteriaDto {

    private String email;

    private String status;

    private String password;

    private String otpCodeVerified;
}
