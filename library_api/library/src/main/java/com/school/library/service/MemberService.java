package com.school.library.service;

import com.school.library.dto.MemberRequest;
import com.school.library.entity.Member;
import com.school.library.enums.MemberStatus;
import com.school.library.exception.LibraryException;
import com.school.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member addMember(MemberRequest request) {

        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new LibraryException("Member email already exists");
        }

        Member member = new Member();
        member.setName(request.getName());
        member.setEmail(request.getEmail());
        member.setStatus(MemberStatus.ACTIVE);

        return memberRepository.save(member);
    }

    public Member updateMember(Long id, MemberRequest request) {

        Member member = getMember(id);

        if (!member.getEmail().equals(request.getEmail()) &&
                memberRepository.existsByEmail(request.getEmail())) {
            throw new LibraryException("Member email already exists");
        }

        member.setName(request.getName());
        member.setEmail(request.getEmail());

        return memberRepository.save(member);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new LibraryException("Member not found"));
    }

    public Member deactivateMember(Long id) {
        Member member = getMember(id);
        member.setStatus(MemberStatus.INACTIVE);
        return memberRepository.save(member);
    }
}
