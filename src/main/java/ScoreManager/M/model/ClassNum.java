package ScoreManager.M.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "CLASS_NUM")
@IdClass(ClassNumId.class)
public class ClassNum {
	@Id
    @Column(name = "SCHOOL_CD", nullable = false, length = 3)
    private String schoolCd;
	
	@Id
    @Column(name = "CLASS_NUM", nullable = false, length = 5)
    private String classNum;


    // ゲッターとセッター
    public String getSchoolCd() {
        return schoolCd;
    }

    public void setSchoolCd(String schoolCd) {
        this.schoolCd = schoolCd;
    }

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }
}
