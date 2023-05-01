package myApp.App;

import myApp.Persistence.Repository;
import myApp.models.RecordHib;
import myApp.models.UserHib;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;

import java.util.List;

public class MyDbWork {
    Configuration configuration;
    public static Repository mem;
    Session session;
    public MyDbWork(){
        MyConfiguration();
        CheckUsersInDb();
    }
    public  void MyConfiguration() {
        configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://ubmicro.lan:5432/testdb");
        configuration.setProperty("hibernate.connection.username", "tanya");
        configuration.setProperty("hibernate.connection.password", "Q123456");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.addAnnotatedClass(UserHib.class);
        configuration.addAnnotatedClass(RecordHib.class);
        configuration.setProperty("hibernate.show_sql", "true");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

    }
    public void CheckUsersInDb(){
        mem = new Repository();
        UserHib oneUserHib = null;
        List<UserHib> userHibList = session.createQuery("from UserHib user ", UserHib.class).getResultList();//список объектов user из таблицы(БД)
        if (userHibList.isEmpty()) {
            session.save((mem.addFakeDataUser()));
            session.getTransaction().commit();
            oneUserHib = session.createQuery("from UserHib user ", UserHib.class).getSingleResult();
         }

    }
    public List<UserHib> GetUserHibList(){
          return session.createQuery("from UserHib user ", UserHib.class).getResultList();//список объектов user из таблицы(БД)
         }

    public List<RecordHib> GetRecHibList(){

          return session.createQuery("from RecordHib rec", RecordHib.class).getResultList();

    }

    public void SaveSessionRec(RecordHib rec) {
        Transaction tx;
        session.save(rec);
                tx = session.getTransaction();
        if (tx.getStatus().equals(TransactionStatus.ACTIVE)) {
            tx.commit();
        } else if (tx.getStatus().equals(TransactionStatus.COMMITTED)) {
            session.beginTransaction();
            tx.commit();
        }
    }

    public void SaveSessionUser(UserHib user) {
        Transaction tx;
        session.save(user);
        tx = session.getTransaction();
        if (tx.getStatus().equals(TransactionStatus.ACTIVE)) {
            tx.commit();
        } else if (tx.getStatus().equals(TransactionStatus.COMMITTED)) {
            session.beginTransaction();
            tx.commit();
        }
    }
}
