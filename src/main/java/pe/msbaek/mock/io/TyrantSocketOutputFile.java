package pe.msbaek.mock.io;

import java.util.LinkedList;
import java.util.List;

public class TyrantSocketOutputFile {

    private static List<Integer> file = new LinkedList<>();

    public static synchronized void write(int b) {
        file.add(b);
    }
}
