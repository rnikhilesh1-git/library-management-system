package com.school.library.controller;

import com.school.library.dto.MemberRequest;
import com.school.library.entity.Member;
import com.school.library.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/create")
    public Member createMember(@Valid @RequestBody MemberRequest request) {
        return memberService.addMember(request);
    }

    @PutMapping("/{memberId}/update")
    public Member updateMember(@PathVariable Long memberId,
                               @Valid @RequestBody MemberRequest request) {
        return memberService.updateMember(memberId, request);
    }

    @GetMapping("/list")
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{memberId}/details")
    public Member getMemberDetails(@PathVariable Long memberId) {
        return memberService.getMember(memberId);
    }

    @PatchMapping("/{memberId}/deactivate")
    public Member deactivateMember(@PathVariable Long memberId) {
        return memberService.deactivateMember(memberId);
    }
}
