package ScoreManager.M.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SCHOOL")
public class School {

    @Id
    @Column(name = "CD", nullable = false, length = 3)
    private String cd;

    @Column(name = "NAME", length = 20)
    private String name;


    public School() {
        // デフォルトコンストラクタ
    }

    public School(String cd, String name) {
        this.cd = cd;
        this.name = name;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

