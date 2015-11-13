package sample;

/**
 * Created by chiruno on 2015. 11. 12..
 */
public class Student {
    String name;
    int onion;

    public Student(String name, int onion) {
        this.name = name;
        this.onion = onion;
    }

    public String getName() {
        return name;
    }

    public int getOnion() {
        return onion;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOnion(int onion) {
        this.onion = onion;
    }
}
