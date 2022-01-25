import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Named
@ApplicationScoped
public class DataBaseManager implements Serializable {
    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("points");
    private final EntityManager entityManager = factory.createEntityManager();

    public boolean addHits(PointsData pointsData) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(pointsData);
            transaction.commit();
            return true;
        } catch (Exception e) {
            System.err.println("Unable to add to DB: " + e.getMessage());
            transaction.rollback();
            return false;
        }
    }

    public List<PointsData> getHits() {
        try {
            return entityManager
                .createQuery("SELECT h FROM PointsData h", PointsData.class)
                .getResultList();
        } catch (Exception e) {
            System.err.println("Unable to get data from DB: " + e.getMessage());
            return Collections.emptyList();
        }
    }
    public void deleteBase(){
        try {
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("DELETE FROM PointsData");
            query.executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
        }
    }
}
