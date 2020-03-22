package project.server;

import project.client.сlassModel.Organization;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.Objects;
@XmlRootElement(name = "Organizations")
public class CollectionManager {
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

    @XmlElement(name = "Organization")
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

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (Organization elem : orgCollection) {
            stringBuffer.append(elem + "\n");
        }
        return stringBuffer.toString();
    }

}
