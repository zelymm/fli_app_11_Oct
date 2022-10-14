package com.ll.exam.fli_app_11_oct.app.song.service;

import com.ll.exam.fli_app_11_oct.app.member.entity.Member;
import com.ll.exam.fli_app_11_oct.app.song.entity.Song;
import com.ll.exam.fli_app_11_oct.app.song.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SongService {
    private final SongRepository songRepository;

    @Transactional
    public Song create(Member author, String subject, String content) {
        Song song = Song.builder()
                .subject(subject)
                .content(content)
                .author(author)
                .build();
        songRepository.save(song);

        return song;
    }

    public boolean actorCanModify(Member actor, Song song) {
        return actor.getId().equals(song.getAuthor().getId());
    }

    public Optional<Song> findForPrintById(long id) {
        Optional<Song> opSong = findById(id);

        if(opSong.isEmpty()) return opSong;
        return opSong;
    }

    public Optional<Song> findById(long songId) {
        return songRepository.findById(songId);
    }

    @Transactional
    public void modify(Song song, String subject, String content) {
        song.setSubject(subject);
        song.setContent(content);
    }
}
