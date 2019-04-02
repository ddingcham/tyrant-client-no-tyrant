package pe.msbaek.mock;

import java.util.LinkedList;
import java.util.List;

public class TyrantSocketFile {

    private static List<Integer> file = new LinkedList<Integer>();

    public static void write(int b) {
        file.add(b);
    }
}
