package com.ll.exam.fli_app_11_oct.service;

import com.ll.exam.fli_app_11_oct.app.member.entity.Member;
import com.ll.exam.fli_app_11_oct.app.member.repository.MemberRepository;
import com.ll.exam.fli_app_11_oct.app.song.entity.Song;
import com.ll.exam.fli_app_11_oct.app.song.service.SongService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class SongServiceTests {
    @Autowired
    private SongService songService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("음원 업로드")
    void t1() {
        Member author = memberRepository.findByUsername("user1").get();

        Song song = songService.create(author, "sub", "cont");

        assertThat(song).isNotNull();
        assertThat(song.getSubject()).isEqualTo("sub");
        assertThat(song.getContent()).isEqualTo("cont");
    }

    @Test
    @DisplayName("음원 수정")
    void t2() {
        Song song = songService.findById(1).get();
        songService.modify(song, "sub new", "cont new");

        assertThat(song).isNotNull();
        assertThat(song.getSubject()).isEqualTo("sub new");
        assertThat(song.getContent()).isEqualTo("cont new");
    }
}
