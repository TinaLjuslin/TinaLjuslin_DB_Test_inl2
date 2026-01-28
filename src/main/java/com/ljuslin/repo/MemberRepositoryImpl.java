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
    public void save(Member member) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(member);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Medlemmen kunde inte sparas");
        }
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
    public Member change(Member member) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Member updatedMember = session.merge(member);
            transaction.commit();
            return updatedMember;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Member kunde inte ändras");
        }
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        //returnerar även medlemmar som ej är aktiva
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
