package ScoreManager.M.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SUBJECT")
public class Subject {
    @Id
    @Column(name = "CD", nullable = false, length = 3)
    private String cd;

    @Column(name = "NAME", length = 20)
    private String name;

    @Column(name = "SCHOOL_CD", length = 3)
    private String schoolCd;

    // ゲッター
    public String getCd() {
        return cd;
    }

    public String getName() {
        return name;
    }

    public String getSchoolCd() {
        return schoolCd;
    }

    // セッター
    public void setCd(String cd) {
        this.cd = cd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchoolCd(String schoolCd) {
        this.schoolCd = schoolCd;
    }
}
