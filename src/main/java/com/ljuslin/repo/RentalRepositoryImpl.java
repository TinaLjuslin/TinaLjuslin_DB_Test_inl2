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
    public void save(Rental rental) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(rental);
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
    }

    @Override
    public Rental change(Rental rental) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Rental updatedRental = session.merge(rental);
            transaction.commit();
            return updatedRental;
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

    @Override
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

    @Override
    public boolean checkMemberHasActiveRental(Member member) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("""
                            SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
                            FROM Rental r
                            WHERE r.member = :member
                            AND r.returnDate IS NULL
                            """, Boolean.class)
                    .setParameter("member", member)
                    .getSingleResult();

        } catch (Exception e) {
            throw new DatabaseException("Fel vid hämtning av totalRevenue");
        }
    }

    @Override
    public List<Rental> search(String searchText) {
        String sql = """
                SELECT DISTINCT r.* FROM rental r
                left JOIN member m ON r.member_id = m.member_id
                LEFT JOIN tie t ON r.rental_item_id = t.tie_id AND r.rental_type = 'TIE'
                LEFT JOIN bowtie bt ON r.rental_item_id = bt.bowtie_id AND r.rental_type = 'BOWTIE'
                LEFT JOIN pocket_square ps ON r.rental_item_id = ps.pocket_square_id AND r.rental_type = 'POCKET_SQUARE'
                WHERE LOWER(m.first_name) LIKE :search
                   OR LOWER(m.last_name) LIKE :search
                   OR LOWER(t.color) LIKE :search
                   OR LOWER(bt.color) LIKE :search
                   OR LOWER(ps.color) LIKE :search
                   OR LOWER(t.material) LIKE :search
                   OR LOWER(bt.material) LIKE :search
                   OR LOWER(ps.material) LIKE :search
                """;

        try (Session session = sessionFactory.openSession()) {
            return session.createNativeQuery(sql, Rental.class)
                    .setParameter("search", "%" + searchText.toLowerCase() + "%")
                    .getResultList();
        } catch (Exception e) {
            throw new DatabaseException("Kunde inte genomföra sökningen");
        }
    }
}

