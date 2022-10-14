package com.ll.exam.fli_app_11_oct.app.song.controller;

import com.ll.exam.fli_app_11_oct.app.member.entity.Member;
import com.ll.exam.fli_app_11_oct.app.security.dto.MemberContext;
import com.ll.exam.fli_app_11_oct.app.song.entity.Song;
import com.ll.exam.fli_app_11_oct.app.song.exception.ActorCannotModifyException;
import com.ll.exam.fli_app_11_oct.app.song.form.SongForm;
import com.ll.exam.fli_app_11_oct.app.song.service.SongService;
import com.ll.exam.fli_app_11_oct.util.Ut;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/song")
@Slf4j
public class SongController {
    private final SongService songService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String showWrite() {
        return "song/create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String write(@AuthenticationPrincipal MemberContext memberContext, @Valid SongForm songForm) {
        Member author = memberContext.getMember();
        Song song = songService.create(author, songForm.getSubject(), songForm.getContent());
        return "redirect:/song/" + song.getId() + "?msg=" + Ut.url.encode("%번 음원이 생성되었습니다.".formatted(song.getId()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/modify")
    public String showModify(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long id, Model model) {
        Song song = songService.findForPrintById(id).get();

        Member actor = memberContext.getMember();
        if (songService.actorCanModify(actor, song) == false) {
            throw new ActorCannotModifyException();
        }
        model.addAttribute("song", song);

        return "song/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{id}/modify")
    public String modify(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long id, @Valid SongForm songForm) {
        Song song = songService.findById(id).get();
        Member actor = memberContext.getMember();

        if(songService.actorCanModify(actor, song) == false) {
            throw new ActorCannotModifyException();
        }

        songService.modify(song, songForm.getSubject(), songForm.getContent());
        return "redirect:/song/" + song.getId() + "?msg=" + Ut.url.encode("%번 음원이 수정되었습니다.".formatted(song.getId()));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping ("/{id}")
    public String detail(@AuthenticationPrincipal MemberContext memberContext, @PathVariable long id, Model model) {
        Song song = songService.findForPrintById(id).get();
        Member actor = memberContext.getMember();
        if(songService.actorCanModify(actor, song) == false) {
            throw new ActorCannotModifyException();
        }
        model.addAttribute("song", song);
        return "song/detail";
    }

}
