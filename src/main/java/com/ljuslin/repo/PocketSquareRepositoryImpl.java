package com.ljuslin.repo;

import com.ljuslin.entity.Bowtie;
import com.ljuslin.entity.Material;
import com.ljuslin.entity.PocketSquare;
import com.ljuslin.exception.DatabaseException;
import com.ljuslin.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class PocketSquareRepositoryImpl implements PocketSquareRepository {
    private final SessionFactory sessionFactory;

    public PocketSquareRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<PocketSquare> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM PocketSquare WHERE active = true",
                    PocketSquare.class).list();
        } catch(Exception e){
            throw new DatabaseException("Fel vid hämtning av näsdukar");
        }
    }

    @Override
    public Optional<PocketSquare> getById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("""
                            FROM PocketSquare 
                            WHERE itemId = :id
                            AND active = true
                            """, PocketSquare.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } catch (Exception e) {
            throw new DatabaseException("Fel vid hämtning av näsduk med id: " + id);
        }
    }

    @Override
    public void save(PocketSquare pocketSquare) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(pocketSquare);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Näsduken kunde inte sparas");
        }
    }

    @Override
    public void change(PocketSquare pocketSquare) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(pocketSquare);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Näsduken kunde inte ändras");
        }
    }

    @Override
    public List<PocketSquare> getByMaterial(Material material) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery
                            ("""
                        FROM PocketSquare 
                        WHERE material = :material
                        AND active = true
                        """, PocketSquare.class)
                    .setParameter("material", material)
                    .list();
        }catch(Exception e){
            throw new DatabaseException("Fel vid hämtning av näsdukar");
        }
    }

    public List<PocketSquare> search(String searchText) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hql = new StringBuilder("FROM PocketSquare WHERE active = true");
            hql.append(" AND (color LIKE :searchText"
                    + " OR CAST(pricePerDay as string) LIKE :searchText"
                    + " OR CAST(material as string) LIKE :searchText)"
            );
            var query = session.createQuery(hql.toString(), PocketSquare.class);
            query.setParameter("searchText", "%" + searchText + "%");
            return query.list();
        } catch (Exception e) {
            throw new DatabaseException("Fel vid sökning av näsdukar.");
        }
    }

}
