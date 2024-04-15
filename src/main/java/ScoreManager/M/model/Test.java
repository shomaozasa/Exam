package ScoreManager.M.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "TEST")
@IdClass(TestId.class)
public class Test {
    @Id
    @Column(name = "STUDENT_NO", nullable = false, length = 10)
    private String studentNo;

    @Id
    @Column(name = "SUBJECT_CD", nullable = false, length = 3)
    private String subjectCd;

    @Id
    @Column(name = "SCHOOL_CD", nullable = false, length = 10)
    private String schoolCd;

    @Id
    @Column(name = "NO", nullable = false)
    private Integer no;

    @Column(name = "POINT")
    private Integer point;

    @Column(name = "CLASS_NUM", length = 5)
    private String classNum;

    // ゲッター
    public String getStudentNo() {
        return studentNo;
    }

    public String getSubjectCd() {
        return subjectCd;
    }

    public String getSchoolCd() {
        return schoolCd;
    }

    public Integer getNo() {
        return no;
    }

    public Integer getPoint() {
        return point;
    }

    public String getClassNum() {
        return classNum;
    }

    // セッター
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public void setSubjectCd(String subjectCd) {
        this.subjectCd = subjectCd;
    }

    public void setSchoolCd(String schoolCd) {
        this.schoolCd = schoolCd;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }
}
