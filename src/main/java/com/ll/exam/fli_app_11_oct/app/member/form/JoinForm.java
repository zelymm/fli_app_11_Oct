package com.ll.exam.fli_app_11_oct.app.member.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class JoinForm {
    @NotEmpty
    private String username;

    @NotEmpty
    private  String password;

    @NotEmpty
    private String email;
}
