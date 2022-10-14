package com.ll.exam.fli_app_11_oct.app.base.initData;

import com.ll.exam.fli_app_11_oct.app.member.entity.Member;
import com.ll.exam.fli_app_11_oct.app.member.service.MemberService;
import com.ll.exam.fli_app_11_oct.app.song.service.SongService;

public interface InitDataBefore {
    default void before(MemberService memberService, SongService songService) {
        Member member1 = memberService.join("user1", "1234", "user1@test.com");
        Member member2 = memberService.join("user2", "1234", "user2@test.com");

        songService.create(member1, "music1", "lyrics1");
        songService.create(member1, "music2", "lyrics2");
        songService.create(member2, "music3", "lyrics3");
        songService.create(member2, "music4", "lyrics4");
    }
}
