package com.ljuslin.repo;

import com.ljuslin.entity.Rental;
import com.ljuslin.exception.DatabaseException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class RentalRepositoryImpl implements RentalRepository {
    private final SessionFactory sessionFactory;

    public RentalRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Rental> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Rental", Rental.class).list();
        } catch (Exception e) {
            throw new DatabaseException("Fel vid hämtning av uthyrningar");
        }
    }

    @Override
    public Optional<Rental> getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Rental.class, id));
        } catch (Exception e) {
            throw new DatabaseException("Fel vid hämtning av uthyrning med id: " + id);
        }
    }

    @Override
    public void save(Rental rental) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(rental);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Uthyrningen kunde inte sparas");
        }
    }

    @Override
    public void change(Rental rental) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(rental);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Uthyrningen kunde inte ändras");
        }
    }
}
