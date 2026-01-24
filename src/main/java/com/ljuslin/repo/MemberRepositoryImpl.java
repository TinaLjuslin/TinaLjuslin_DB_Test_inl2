package com.ljuslin.repo;

import com.ljuslin.entity.Level;
import com.ljuslin.entity.Member;
import com.ljuslin.exception.DatabaseException;
import com.ljuslin.util.HibernateUtil;
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
    public void save(Member member){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(member);
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
            return session.createQuery("FROM Member WHERE active = true", Member.class).list();
        }catch(Exception e){
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
        } catch(Exception e){
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
    public List<Member> search(String firstName, String lastName, String email, Level level) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hql = new StringBuilder("FROM Member WHERE 1=1"); // 1=1 är ett trick för att kunna lägga till "AND" enkelt

            if (firstName != null && !firstName.isBlank())
                hql.append(" AND firstName LIKE :firstName");
            if (lastName != null && !lastName.isBlank()) hql.append(" AND lastName LIKE :lastName");
            if (email != null && !email.isBlank()) hql.append(" AND email LIKE :email");
            if (level != null) hql.append(" AND level = :level");
            hql.append("AND active = true");
            var query = session.createQuery(hql.toString(), Member.class);
            if (firstName != null && !firstName.isBlank())
                query.setParameter("firstName", "%" + firstName + "%");
            if (lastName != null && !lastName.isBlank())
                query.setParameter("lastName", "%" + lastName + "%");
            if (email != null && !email.isBlank()) query.setParameter("email", "%" + email + "%");
            if (level != null) query.setParameter("level", level);

            return query.list();
        } catch(Exception e) {
            throw new DatabaseException("Fel vid sökning av medlemmar.");
        }
    }
}
/*public void registerMember(Member newMember) {
    Optional<Member> existingOpt = memberRepository.findByEmail(newMember.getEmail());

    if (existingOpt.isPresent()) {
        Member existing = existingOpt.get();
        if (existing.isActive()) {
            throw new IllegalActionException("E-posten används redan av en aktiv medlem.");
        } else {
            // Medlemmen fanns men var inaktiv - vi aktiverar den igen!
            existing.setActive(true);
            existing.setName(newMember.getName()); // Uppdatera ev. namn
            memberRepository.change(existing);
            System.out.println("Välkommen tillbaka! Din gamla profil har återaktiverats.");
        }
    } else {
        // Helt ny medlem
        memberRepository.save(newMember);
    }
}*/