package com.ljuslin.repo;

import com.ljuslin.entity.Bowtie;
import com.ljuslin.entity.Material;
import com.ljuslin.entity.Tie;
import com.ljuslin.exception.DatabaseException;
import com.ljuslin.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class TieRepositoryImpl implements TieRepository {
    private final SessionFactory sessionFactory;
    public TieRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<Tie> getAll(){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Tie WHERE active = true", Tie.class).list();
        } catch (Exception e) {
            throw new DatabaseException("Fel vid hämtning av slipsar");
        }
    }
    @Override
    public Optional<Tie> getById(Long id){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("""
                FROM Tie
                WHERE itemId = :id
                AND active = true
                """, Tie.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } catch(Exception e){
            throw new DatabaseException("Fel vid hämtning av slips med id: " + id);
        }
    }
    @Override
    public void save(Tie tie){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(tie);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Slipsen kunde inte sparas");
        }
    }
    @Override
    public void change(Tie tie){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(tie);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Slipsen kunde inte ändras");
        }
    }

    @Override
    public List<Tie> getByMaterial(Material material){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery
                            ("""
                        FROM Tie 
                        WHERE material = :material
                        AND active = true
                        """, Tie.class)
                    .setParameter("material", material)
                    .list();
        }catch(Exception e){
            throw new DatabaseException("Fel vid hämtning av slipsar");
        }
    }

    public List<Tie> search(String searchText) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder hql = new StringBuilder("FROM Tie WHERE active = true");
            hql.append(" AND (color LIKE :searchText"
                    + " OR CAST(pricePerDay as string) LIKE :searchText"
                    + " OR CAST(material as string) LIKE :searchText)"
            );
            var query = session.createQuery(hql.toString(), Tie.class);
            query.setParameter("searchText", "%" + searchText + "%");
            return query.list();
        } catch (Exception e) {
            throw new DatabaseException("Fel vid sökning av slipsar.");
        }
    }
}

