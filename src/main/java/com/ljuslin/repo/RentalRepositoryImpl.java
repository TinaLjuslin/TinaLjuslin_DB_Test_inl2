package com.ljuslin.repo;

import com.ljuslin.entity.Member;
import com.ljuslin.entity.Rental;
import com.ljuslin.entity.RentalObject;
import com.ljuslin.entity.RentalType;
import com.ljuslin.exception.DatabaseException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
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
    public Rental save(Rental rental) {
        Rental tempRental = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            tempRental = session.merge(rental);
            String hql = "UPDATE ";
            switch (rental.getRentalType()) {
                case TIE -> hql += "Tie SET available = false WHERE id = :id";
                case BOWTIE -> hql += "Bowtie SET available = false WHERE id = :id";
                case POCKET_SQUARE -> hql += "PocketSquare SET available = false WHERE id = :id";
            }
            session.createMutationQuery(hql)
                    .setParameter("id", rental.getItemId())
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Uthyrningen kunde inte sparas");
        }
        return tempRental;
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

    @Override
    public BigDecimal getTotalRevenue() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT SUM(totalRevenue) FROM Rental", BigDecimal.class)
                    .getSingleResult();
        } catch (Exception e) {
            throw new DatabaseException("Fel vid hämtning av totalRevenue");
        }
    }

    public BigDecimal getRevenuePerRentalObject(RentalType rentalType, long itemId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("SELECT SUM(totalRevenue) FROM Rental WHERE rentalType " +
                                    "=:rentalType AND itemId = :itemId",
                            BigDecimal.class)
                    .setParameter("rentalType", rentalType)
                    .setParameter("itemId", itemId)
                    .getSingleResult();
        } catch (Exception e) {
            throw new DatabaseException("Fel vid hämtning av totalRevenue");
        }
    }
}
