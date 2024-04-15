package ScoreManager.M.model;

import java.io.Serializable;
import java.util.Objects;

public class TestId implements Serializable {
    private String studentNo;
    private String subjectCd;
    private String schoolCd;
    private Integer no;

    // デフォルトコンストラクタ
    public TestId() {
    }

    // 全フィールドを含むコンストラクタ
    public TestId(String studentNo, String subjectCd, String schoolCd, Integer no) {
        this.studentNo = studentNo;
        this.subjectCd = subjectCd;
        this.schoolCd = schoolCd;
        this.no = no;
    }

    // ゲッターとセッター
    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getSubjectCd() {
        return subjectCd;
    }

    public void setSubjectCd(String subjectCd) {
        this.subjectCd = subjectCd;
    }

    public String getSchoolCd() {
        return schoolCd;
    }

    public void setSchoolCd(String schoolCd) {
        this.schoolCd = schoolCd;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    // equalsメソッド
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestId testId = (TestId) o;
        return Objects.equals(studentNo, testId.studentNo) &&
               Objects.equals(subjectCd, testId.subjectCd) &&
               Objects.equals(schoolCd, testId.schoolCd) &&
               Objects.equals(no, testId.no);
    }

    // hashCodeメソッド
    @Override
    public int hashCode() {
        return Objects.hash(studentNo, subjectCd, schoolCd, no);
    }
}
