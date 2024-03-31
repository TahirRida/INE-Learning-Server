package br.com.fms.authentication.payload.request;

import lombok.Data;

@Data
public class LoginRequest {
//    @NotBlank
    private String username;

//    @NotBlank
    private String password;

}
