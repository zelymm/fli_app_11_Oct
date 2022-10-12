package com.ll.exam.fli_app_11_oct.app.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.exam.fli_app_11_oct.app.base.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private boolean emailVerified;

    public String getName() {
        return username;
    }
    public Member(long id) {
        super(id);
    }
}
