package DAO;

import MODEL.Product;
import REPOSITORY.DAOInterface;
import UTILS.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ProductDAO implements DAOInterface<Product, Integer> {
    private final SessionFactory sessionFactory= HibernateUtil.getFactory();
    public boolean create(Product product) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(product);

            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return false;
    }

    public Product read(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            Product obj = session.get(Product.class, id);
            session.close();
            return obj;
        } catch (HibernateException e) {
            return null;
        }
    }

    public List<Product> readAll() {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM Product");
            List<Product> list = query.getResultList();

            session.close();
            return list;
        } catch (HibernateException e) {
            return null;
        }
    }

    public boolean update(Product product) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(product);

            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return false;
    }

    public boolean delete(Integer id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Product obj = session.get(Product.class, id);
            session.remove(obj);

            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return false;
    }
}
