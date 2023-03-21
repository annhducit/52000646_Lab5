package UTILS;

import MODEL.Product;
import MODEL.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory FACTORY;

    static {
        Properties props = new Properties();
        props.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        props.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        props.put(Environment.URL, "jdbc:mysql://localhost:3306/managementproduct");
        props.put(Environment.USER, "");
        props.put(Environment.PASS, "");
        props.put(Environment.SHOW_SQL, "false");
        props.put(Environment.HBM2DDL_AUTO, "update");

        Configuration conf = new Configuration()
                .setProperties(props)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Product.class);

        ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(conf.getProperties()).build();
        FACTORY = conf.buildSessionFactory();
    }

    public static SessionFactory getFactory() {
        return FACTORY;
    }
}
