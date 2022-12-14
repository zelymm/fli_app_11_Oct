package com.ll.exam.fli_app_11_oct.app.base.initData;

import com.ll.exam.fli_app_11_oct.app.member.service.MemberService;
import com.ll.exam.fli_app_11_oct.app.song.service.SongService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestInitData implements InitDataBefore {
    @Bean
    CommandLineRunner initData(MemberService memberService, SongService songService) {
        return args -> {
            before(memberService, songService);
        };
    }
}
