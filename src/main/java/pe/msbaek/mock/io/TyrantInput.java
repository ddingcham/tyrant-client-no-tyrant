package pe.msbaek.mock.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/*
    Tyrant Clients [N] : TyrantInput [1] : MockTyrant [1]
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TyrantInput {

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
