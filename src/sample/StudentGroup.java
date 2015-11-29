package sample;

import javafx.scene.image.ImageView;

/**
 * Created by 재원 on 2015-11-16.
 */

public class StudentGroup {
    private Student[] students = new Student[7];
    private int onion;
    private int onion_cnt;
    private int NumOfStudents = 0;

    public Student getStudent(int i) {
        return students[i];
    }

    public int getOnion() {
        return onion;
    }

    public void setOnion(int onion) {
        this.onion = onion;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }

    public void addStudent(Student student) { students[NumOfStudents] = student; NumOfStudents++; }

    public int getNumOfStudents() { return NumOfStudents; }

    public int getOnion_cnt() {
        return onion_cnt;
    }

    public void setOnion_cnt(int onion_cnt) { this.onion_cnt = onion_cnt; }

    public void plusOnion(ImageView o) {
        switch(o.getId()) {
            case "imgOnion1":
                onion += 1;
                break;
            case "imgOnion2":
                onion += 2;
                break;
            case "imgOnion3":
                onion += 3;
                break;
            case "imgOnion4":
                onion -= 1;
                break;
        }
        onion_cnt ++;
    }
}