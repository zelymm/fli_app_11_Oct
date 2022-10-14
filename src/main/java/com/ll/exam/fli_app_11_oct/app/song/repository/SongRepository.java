package com.ll.exam.fli_app_11_oct.app.song.repository;


import com.ll.exam.fli_app_11_oct.app.song.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {

}
