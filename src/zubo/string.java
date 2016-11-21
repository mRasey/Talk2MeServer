package zubo;

import java.util.ArrayList;

/**
 * Created by billy on 2016/11/20.
 */
public class string {
    public static void main(String[] args) {
        String a = "hello";
        String b = "hello";
        ArrayList<String> arraylist = new ArrayList<>();
        arraylist.add(a);
        System.out.println(arraylist.contains(b));
    }
}
