package sample;

/**
 * Created by chiruno on 2015. 11. 12..
 */
public class Student {
    private int num;
    private String name;
    private int onion;

    public Student(int num, String name, int onion) {
        this.num = num;
        this.name = name;
        this.onion = onion;
    }

    public Student(String num, String name, int onion) {
        this.num = Integer.parseInt(num);
        this.name = name;
        this.onion = onion;
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

    public int getOnion() {
        return onion;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNum(int onion) {
        this.num = num;
    }

    public void setOnion(int onion) {
        this.onion = onion;
    }
}
