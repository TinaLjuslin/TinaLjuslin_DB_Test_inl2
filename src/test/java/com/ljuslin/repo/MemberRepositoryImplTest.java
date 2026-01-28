package com.ljuslin.repo;

import com.ljuslin.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void save() {
    }

    @Test
    void getById() {
    }

    @Test
    void change() {
    }
}