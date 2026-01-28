package com.ljuslin.repo;

import com.ljuslin.entity.Level;
import com.ljuslin.entity.Member;
import com.ljuslin.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryImplTest {
    private static SessionFactory sessionFactory;
    private MemberRepositoryImpl memberRepository;

    @BeforeAll
    static void beforeAll() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @BeforeEach
    void beforeEach() {
        memberRepository = new MemberRepositoryImpl(sessionFactory);
    }

    @AfterAll
    static void afterAll() {
        HibernateUtil.shutdown();
    }

    @Test
    void save_aNewMemberShouldbeSavedProperly() {
        Member member = new Member("Anna", "Ljuslin", "tina.test1@email.com", Level.PREMIUM);
        member.setActive(true);
        memberRepository.save(member);

        assertNotNull(member);

        Optional<Member> memberById = memberRepository.getById(member.getMemberId());
        assertTrue(memberById.isPresent());
        assertEquals("Anna", memberById.get().getFirstName());

    }

    @Test
    void change_aMemberShouldBeChangesProperly() {
        Member member = new Member("Anna", "Ljuslin", "anna@mail.com", Level.STANDARD);
        memberRepository.save(member);
        assertNotNull(member);
        assertNotNull(member.getMemberId());
        member.setActive(true);
        member.setLastName("Källström");
        member.setLevel(Level.PREMIUM);
        memberRepository.change(member);
        assertNotNull(member);
        assertEquals("Anna", member.getFirstName());
        assertEquals("Källström", member.getLastName());
        assertEquals(Level.PREMIUM, member.getLevel());

    }
}