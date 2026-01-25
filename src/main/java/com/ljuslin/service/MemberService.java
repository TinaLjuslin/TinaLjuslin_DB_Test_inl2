package com.ljuslin.service;

import com.ljuslin.entity.History;
import com.ljuslin.entity.Level;
import com.ljuslin.entity.Member;
import com.ljuslin.exception.InvalidDataException;
import com.ljuslin.exception.MemberAlreadyExistsException;
import com.ljuslin.repo.HistoryRepositoryImpl;
import com.ljuslin.repo.MemberRepositoryImpl;

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
            //kolla om optmember är aktiv eller inte
            if (optionalMember.get().isActive()) {
                throw new MemberAlreadyExistsException("This member already exists.");
            }
            //medlemmen finns i databasen men är inaktiv, sätt till aktiv och uppdatera
            Member member = optionalMember.get();
            member.setActive(true);
            member.setFirstName(firstName);
            member.setLastName(lastName);
            member.setLevel(level);
            memberRepository.change(member);
        } else {
            Member member = new Member(firstName, lastName, email, level);
            member.setActive(true);
            memberRepository.save(member);
        }
    }

    private boolean checkMemberData(String firstName, String lastName, String email, Level level) {
        if (firstName == null || firstName.isBlank()) {
            throw new InvalidDataException("Vänligen ange ett förnamn.");
        } else if (firstName == null || firstName.isBlank()) {
            throw new InvalidDataException("Vänligen ange ett efternamn.");
        } else if (email == null || !email.contains("@")) {
            throw new InvalidDataException("Vänligen ange en giltig email.");
        } else if (level == null) {
            throw new InvalidDataException("Vänligen välj en level.");
        }
        return true;
    }

    public Member getMemberById(String memberId) {
        Long id;
        try {
            id = Long.parseLong(memberId);
        } catch (Exception e) {
            throw new InvalidDataException("Detta är inte ett korrekt id.");
        }
        return memberRepository.getById(id).get();
    }

    public void changeMember(Member member) {
        checkMemberData(member.getFirstName(), member.getLastName(), member.getEmail(), member.getLevel());
        //KOLLA FÖRST OM EMAIL ÄR ÄNDRAT
        Member tempMember = memberRepository.getById(member.getMemberId()).get();
        if (!member.getEmail().equals(tempMember.getEmail())) {
            //kolla så email är unikt
            Optional<Member> optionalMember = memberRepository.findByEmail(member.getEmail());

            if (optionalMember.isPresent()) {
                throw new InvalidDataException("Email är inte unikt, vänligen välj en annan " +
                        "emailadress.");
            }
        }
        memberRepository.change(member);
       // addToHistory(member, ("Member changed: " + member.toString()));
    }

    public void removeMember(Member member) {
        //kolla om medlem har aktiva uthyrningar
        member.setActive(false);
        memberRepository.change(member);
    }
    public List<Member> searchMembers(String searchText) {
        return memberRepository.search(searchText);
    }
    public List<History> getHistory(Member member) {
        return historyRepository.getHistory(member);
    }
    public List<Member> getAllMembers() {
        return memberRepository.getAll();
    }
}
