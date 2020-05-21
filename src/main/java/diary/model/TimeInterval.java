package diary.model;

import javax.persistence.*;

@Entity
@Table(name = "time_interval")
public class TimeInterval {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "value")
    private String value;

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
