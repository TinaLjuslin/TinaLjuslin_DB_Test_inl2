package com.ljuslin.service;

import com.ljuslin.entity.Member;
import com.ljuslin.repo.MemberRepositoryImpl;

import java.util.Optional;

public class MemberService {
    MemberRepositoryImpl memberRepository;
    public MemberService(MemberRepositoryImpl memberRepository) {
        this.memberRepository = memberRepository;
    }
    public void createMember(Member member) {
        //check email om medlemmen redan finns med email, kolla om medlemmen är activ
        //då kan den inte skapas
        //är medlemmen inte active så öppnar vi upp den och kör change
        //kollanamn och
        Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());
        if(optionalMember.isPresent()){
            member.setActive(true);
            memberRepository.change(member);
        } else {
            memberRepository.save(member);
        }
    }
}
