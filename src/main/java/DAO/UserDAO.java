package DAO;

import MODEL.User;
import REPOSITORY.DAOInterface;
import UTILS.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDAO implements DAOInterface<User, String> {
    private final SessionFactory sessionFactory = HibernateUtil.getFactory();
    public boolean create(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);

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

    @Override
    public User read(String id) {
        try (Session session = sessionFactory.openSession()) {
            User obj = session.get(User.class, id);
            session.close();
            return obj;
        } catch (HibernateException e) {
            return null;
        }
    }
    
    public List<User> readAll() {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("FROM User");
            List<User> list = query.getResultList();

            session.close();
            return list;
        } catch (HibernateException e) {
            return null;
        }
    }
    public User getUserByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);

            User obj = query.uniqueResult();
            session.close();
            return obj;
        } catch (HibernateException e) {
            return null;
        }
    }


    public boolean update(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(user);

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

    @Override
    public boolean delete(String id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User obj = session.get(User.class, id);
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
