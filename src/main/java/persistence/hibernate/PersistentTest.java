package persistence.hibernate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Tarek on 30.03.2016. Be grateful for this superior Code!
 */
@Entity
@Table(name="PhaseX")
public class PersistentTest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="PHASE_X_STRING")
    private String testString;


    public PersistentTest() {}

    public Integer getStringID() {
        return id;
    }

    public void setStringID(Integer stringID) {
        this.id = stringID;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }
}
