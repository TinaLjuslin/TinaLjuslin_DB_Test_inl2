package com.ljuslin.repo;

import com.ljuslin.entity.Member;
import com.ljuslin.exception.DatabaseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class MemberRepositoryImpl implements MemberRepository {
    private final SessionFactory sessionFactory;

    public MemberRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Member save(Member member) {
        Transaction transaction = null;
        Member savedMember = null; //för att det inte går att spara member och history efter
        // varandra, hibernate tror inte membern är skapad
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            savedMember = session.merge(member);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Medlemmen kunde inte sparas");
        }
        return savedMember;
    }

    @Override
    public List<Member> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "SELECT DISTINCT m FROM Member m LEFT JOIN FETCH m.history WHERE m.active = true",
                    Member.class).list();
        } catch (Exception e) {
            throw new DatabaseException("Fel vid hämtning av medlemmar");
        }
    }
    @Override
    public Optional<Member> getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("""
                            FROM Member
                            WHERE memberId = :id
                            AND active = true
                            """, Member.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DatabaseException("Fel vid hämtning av medlem med id: " + id);
        }
    }

    @Override
    public void change(Member member) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(member);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Member kunde inte ändras");
        }
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Member WHERE email = :email", Member.class)
                    .setParameter("email", email)
                    .uniqueResultOptional();
        }
    }

    @Override
    public List<Member> search(String searchText) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hql = new StringBuilder("FROM Member WHERE active = true");

            if (searchText != null && !searchText.isBlank()) {
                hql.append(" AND (firstName LIKE :searchText"
                        + " OR lastName LIKE :searchText"
                        + " OR email LIKE :searchText"
                        + " OR CAST(level as string) LIKE :searchText)"
                );
                var query = session.createQuery(hql.toString(), Member.class);
                query.setParameter("searchText", "%" + searchText + "%");

                return query.list();
            }
            return session.createQuery("FROM Member WHERE active = true", Member.class).list();
        } catch (Exception e) {
            throw new DatabaseException("Fel vid sökning av medlemmar.");
        }
    }
}
