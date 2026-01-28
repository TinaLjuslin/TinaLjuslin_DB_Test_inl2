package com.ljuslin.service;

import com.ljuslin.entity.History;
import com.ljuslin.entity.Level;
import com.ljuslin.entity.Member;
import com.ljuslin.exception.EntityNotFoundException;
import com.ljuslin.exception.ValidationException;
import com.ljuslin.exception.IllegalActionException;
import com.ljuslin.repo.HistoryRepositoryImpl;
import com.ljuslin.repo.MemberRepositoryImpl;
import com.ljuslin.util.ValidationUtil;

import java.util.List;
import java.util.Optional;

public class MemberService {
    MemberRepositoryImpl memberRepository;
    HistoryRepositoryImpl historyRepository;

    public MemberService(MemberRepositoryImpl memberRepository, HistoryRepositoryImpl historyRepository) {
        this.memberRepository = memberRepository;
        this.historyRepository = historyRepository;
    }

    public void newMember(String firstName, String lastName, String email, Level level) {
        checkMemberData(firstName, lastName, email, level);
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            if (optionalMember.get().isActive()) {
                throw new IllegalActionException("This member already exists.");
            }
            Member member = optionalMember.get();
            member.setActive(true);
            member.setFirstName(firstName);
            member.setLastName(lastName);
            member.setLevel(level);
            Member m = memberRepository.save(member);
            History history =
                    new History((ValidationUtil.getNow() + ": " + m.toString()
                            + ": skapad"), m);
                    historyRepository.save(history);
        } else {
            Member member = new Member(firstName, lastName, email, level);
            member.setActive(true);
            Member m = memberRepository.save(member);
            History history = new History((ValidationUtil.getNow() + ": " + m.toString()
                    + ": skapad"), m);
            historyRepository.save(history);
        }
    }

    private boolean checkMemberData(String firstName, String lastName, String email, Level level) {
        if (firstName == null || firstName.isBlank()) {
            throw new ValidationException("Vänligen ange ett förnamn.");
        } else if (firstName == null || firstName.isBlank()) {
            throw new ValidationException("Vänligen ange ett efternamn.");
        } else if (email == null || !email.contains("@")) {
            throw new ValidationException("Vänligen ange en giltig email.");
        } else if (level == null) {
            throw new ValidationException("Vänligen välj en level.");
        }
        return true;
    }
    public void changeMember(Member member) {
        checkMemberData(member.getFirstName(), member.getLastName(), member.getEmail(), member.getLevel());
        Member tempMember = memberRepository.getById(member.getMemberId()).get();
        if (!member.getEmail().equals(tempMember.getEmail())) {
            Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());

            if (optionalMember.isPresent()) {
                throw new ValidationException("Email är inte unikt, vänligen välj en annan " +
                        "emailadress.");
            }
        }
        memberRepository.change(member);
        History history = new History((ValidationUtil.getNow() + ": " + member.toString()
                + ": uppdaterad"), member);
        historyRepository.save(history);

    }

    public void removeMember(Member member) {
        //kolla om medlem har aktiva uthyrningar
        member.setActive(false);
        memberRepository.change(member);
        History history = new History((ValidationUtil.getNow() + ": " + member.toString()
                + ": satt till ej aktiv"), member);
        historyRepository.save(history);

    }

    public List<Member> searchMembers(String searchText) {
        if(searchText == null || searchText.isBlank()) {
            throw new ValidationException("Skriv i en söksträng.");
        }
        return memberRepository.search(searchText);
    }

    public List<History> getHistory(Member member) {
        List<History>list = historyRepository.getHistory(member);
        if(list.isEmpty()) {
            throw new EntityNotFoundException("Ingen history funnen");
        }
        return list;
    }

    public List<Member> getAllMembers() {
        return memberRepository.getAll();
    }
}
