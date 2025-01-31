package com.parkee.library.service;

import com.parkee.library.model.Member;
import com.parkee.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> getMemberByIdCardNumber(Long idCardNumber) {
        return memberRepository.findByIdCardNumber(idCardNumber);
    }

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

}
