package com.ll.exam.fli_app_11_oct.app.member.repository;

import com.ll.exam.fli_app_11_oct.app.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}
