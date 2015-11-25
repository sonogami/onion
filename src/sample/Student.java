package sample;

/**
 * Created by chiruno on 2015. 11. 12..
 */
public class Student {
    String name;
    int num;

    public Student(int num, String name) {
        this.name = name;
        this.num = num;
    }

    public Student(String num, String name) {
        this.name = name;
        this.num = Integer.parseInt(num);
    }

    public Student(){
        // empty
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int onion) {
        this.num = num;
    }
}
