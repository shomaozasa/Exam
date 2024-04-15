package ScoreManager.M.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "STUDENT")
public class Student {
    @Id
    @Column(name = "NO", nullable = false, length = 10)
    private String no;

    @Column(name = "NAME", length = 10)
    private String name;

    @Column(name = "ENT_YEAR")
    private Integer entYear;

    @Column(name = "CLASS_NUM", length = 3)
    private String classNum;

    @Column(name = "IS_ATTEND")
    private Boolean isAttend;

    @Column(name = "SCHOOL_CD", length = 3)
    private String schoolCd;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEntYear() {
        return entYear;
    }

    public void setEntYear(Integer entYear) {
        this.entYear = entYear;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public Boolean getIsAttend() {
        return isAttend;
    }

    public void setIsAttend(Boolean isAttend) {
        this.isAttend = isAttend;
    }

    public String getSchoolCd() {
        return schoolCd;
    }

    public void setSchoolCd(String schoolCd) {
        this.schoolCd = schoolCd;
    }
}

