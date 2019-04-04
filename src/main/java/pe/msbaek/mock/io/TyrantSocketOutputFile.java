package pe.msbaek.mock.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TyrantSocketOutputFile {

    private static List<int[]> tyrantOperations = new ArrayList<>();

    public static void clear() {
        tyrantOperations.clear();
    }

    public static void write(int[] chunk) {
        tyrantOperations.add(chunk);
    }

    public static int size() {
        return tyrantOperations.size();
    }
}
