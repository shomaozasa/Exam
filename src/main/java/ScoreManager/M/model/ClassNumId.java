package ScoreManager.M.model;

import java.io.Serializable;
import java.util.Objects;

public class ClassNumId implements Serializable {
    private String schoolCd;
    private String classNum;

    // デフォルトコンストラクタ
    public ClassNumId() {
    }

    // 全フィールドを含むコンストラクタ
    public ClassNumId(String schoolCd, String classNum) {
        this.schoolCd = schoolCd;
        this.classNum = classNum;
    }

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

    // equalsメソッド
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassNumId that = (ClassNumId) o;
        return Objects.equals(schoolCd, that.schoolCd) &&
               Objects.equals(classNum, that.classNum);
    }

    // hashCodeメソッド
    @Override
    public int hashCode() {
        return Objects.hash(schoolCd, classNum);
    }
}
