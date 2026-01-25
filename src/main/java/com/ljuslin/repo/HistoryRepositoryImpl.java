package com.ljuslin.repo;

import com.ljuslin.entity.History;
import com.ljuslin.entity.Member;
import com.ljuslin.exception.DatabaseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class HistoryRepositoryImpl implements HistoryRepository {
    private final SessionFactory sessionFactory;

    public HistoryRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public List<History> getHistory(Member member) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("""
                FROM History
                WHERE member = :id                              
                """, History.class)
                    .setParameter("id", member)
                    .list();
        } catch(Exception e){
            throw new DatabaseException("Fel vid hämtning av historia för medlem med id: "
                    + member.getMemberId());
        }

    }

}
