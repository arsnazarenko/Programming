package server.business;


import library.сlassModel.Organization;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.Objects;
public class CollectionManager {

    public static long OBJECT_ID_COUNTER = 0;

    /**
     * Класс, содержащий коллекцию, с которой ведется работа.
     */


    /**
     * Сама коллекция
     */
    private Deque<Organization> orgCollection;
    /**
     * Дата инициализации коллекции
     */
    private Date creationCollectionDate;


    public CollectionManager() {
        this.orgCollection = new ArrayDeque<>();
        this.creationCollectionDate = new Date();
    }

    public Date getCreationCollectionDate() {
        return creationCollectionDate;
    }

    public void setCreationCollectionDate(Date creationCollectionDate) {
        this.creationCollectionDate = creationCollectionDate;
    }

    public Deque<Organization> getOrgCollection() {
        return orgCollection;
    }

    public void setOrgCollection(Deque<Organization> orgCollection) {
        this.orgCollection = orgCollection;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CollectionManager that = (CollectionManager) o;
        return Objects.equals(orgCollection, that.orgCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orgCollection);
    }


}
