package sample;

import javafx.scene.control.TextField;

/**
 * Created by chiruno on 2015. 11. 16..
 */
public class NumberTextField extends TextField {
    @Override
    public void replaceText(int i, int i1, String string){
        if((string.matches("[0-9]") && string.length() < 10) || string.isEmpty()) {
            super.replaceText(i, i1, string);
        }
    }
}
