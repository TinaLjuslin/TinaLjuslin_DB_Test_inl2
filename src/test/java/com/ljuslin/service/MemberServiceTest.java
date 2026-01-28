package com.ljuslin.service;

import com.ljuslin.entity.Level;
import com.ljuslin.entity.Member;
import com.ljuslin.exception.ValidationException;
import com.ljuslin.repo.HistoryRepositoryImpl;
import com.ljuslin.repo.MemberRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {
    private MemberRepositoryImpl memberRepo;
    private MemberService memberService;
    private HistoryRepositoryImpl historyRepo;

    @BeforeEach
    void setUp() {
        //inte till riktiga databasen
        memberRepo = mock(MemberRepositoryImpl.class);
        historyRepo = mock(HistoryRepositoryImpl.class);
        memberService = new MemberService(memberRepo, historyRepo);
    }

    @Test
    void newMember_mustThrowValidationException_ifIncorrectData() {

        assertThrows(ValidationException.class,
                () -> memberService.newMember("förnamn", "efternamn", "emailUtanSnabelA", Level.PREMIUM));
    }

    @Test
    void changeMember_mustThrowDatabaseException_ifNewEmailNotUnique() {
        Member member1 = new Member("Anna", "Andersson", "anna@mail.se", Level.STANDARD);
        Member member2 = new Member("Peter", "petterson", "petter@mail.se", Level.STANDARD);
        setIdViaReflection(member1, 1L);
        setIdViaReflection(member2, 2L);
        when(memberRepo.getById(2L)).thenReturn(Optional.of(new Member("Peter", "petterson",
                "petter@mail.se", Level.STANDARD)));
        when(memberRepo.findByEmail("anna@mail.se")).thenReturn(Optional.of(member1));
        member2.setEmail("anna@mail.se");
        assertThrows(ValidationException.class, () -> memberService.changeMember(member2));
    }

    @Test
    void newMember_shoudActivateOldMember_ifInactiveMemberExistsByEmail() {

        Member oldMember = new Member("peter", "OldName", "petter@mail.se", Level.STANDARD);
        setIdViaReflection(oldMember, 1L);
        oldMember.setActive(false);
        when(memberRepo.findByEmail("petter@mail.se")).thenReturn(Optional.of(oldMember));
        when(memberRepo.save(oldMember)).thenReturn(oldMember);
        memberService.newMember("peter", "NewName", "petter@mail.se", Level.PREMIUM);

        assertTrue(oldMember.isActive(),"Member should be set to active again");
        assertTrue(oldMember.getLastName().equals("NewName"), "Member should have NewName as " +
                "lastName");
        //samma sak, annat sätt
        assertEquals("NewName", oldMember.getLastName(), "Old member should have new name");
        assertTrue(oldMember.getLevel().equals(Level.PREMIUM), "Member should be updated with new" +
                " Level");
    }

    private static void setIdViaReflection(Object entity, Long id) {
        try {
            var field = entity.getClass().getDeclaredField("memberId");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (Exception e) {
            throw new RuntimeException("Kunde inte sätta id via reflection", e);
        }
    }
}
