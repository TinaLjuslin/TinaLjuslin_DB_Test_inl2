package com.ljuslin.repo;

import com.ljuslin.entity.Bowtie;
import com.ljuslin.entity.Material;
import com.ljuslin.exception.DatabaseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class BowtieRepositoryImpl implements BowtieRepository {
    private final SessionFactory sessionFactory;

    public BowtieRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<Bowtie> getAll(){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Bowtie WHERE active = true", Bowtie.class).list();
        } catch(Exception e){
            throw new DatabaseException("Fel vid hämtning av flugor");
        }
    }
    @Override
    public Optional<Bowtie> getById(Long id){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("""
                FROM Bowtie
                WHERE itemId = :id
                AND active = true
                """, Bowtie.class)
                    .setParameter("id", id)
                    .uniqueResultOptional();
        } catch(Exception e){
            throw new DatabaseException("Fel vid hämtning av fluga med id: " + id);
        }
    }
    @Override
    public void save(Bowtie bowtie){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(bowtie);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Flugan kunde inte sparas: ");
        }
    }
    @Override
    public void change(Bowtie bowtie){
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(bowtie);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Flugan kunde inte ändras: ");
        }
    }
    @Override
    public List<Bowtie> getByMaterial(Material material){
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery
                    ("""
                        FROM Bowtie 
                        WHERE material = :material
                        AND active = true
                        """, Bowtie.class)
                    .setParameter("material", material)
                    .list();
        }catch(Exception e){
            throw new DatabaseException("Fel vid hämtning av flugor");
        }
    }
}
