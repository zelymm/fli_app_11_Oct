package com.ll.exam.fli_app_11_oct.app.song.entity;

import com.ll.exam.fli_app_11_oct.app.base.entity.BaseEntity;
import com.ll.exam.fli_app_11_oct.app.member.entity.Member;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(access = PROTECTED)
public class Song extends BaseEntity {
    private String subject;
    private String content;
    @ManyToOne(fetch = LAZY)
    private Member author;
}
