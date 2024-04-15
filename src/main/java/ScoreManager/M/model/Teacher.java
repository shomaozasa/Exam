package ScoreManager.M.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "TEACHER")
public class Teacher {
    @Id
    @Column(name = "ID", nullable = false, length = 10)
    private String id;

    @Column(name = "PASSWORD", length = 30)
    private String password;

    @Column(name = "NAME", length = 10)
    private String name;

    @Column(name = "SCHOOL_CD", length = 3)
    private String schoolCd;

    // ゲッター
    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSchoolCd() {
        return schoolCd;
    }

    // セッター
    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchoolCd(String schoolCd) {
        this.schoolCd = schoolCd;
    }
}
