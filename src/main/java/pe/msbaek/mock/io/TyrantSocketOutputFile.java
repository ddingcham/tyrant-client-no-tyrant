package pe.msbaek.mock.io;

import pe.msbaek.mock.operation.TyrantOperation;

import java.util.LinkedList;
import java.util.List;

public class TyrantSocketOutputFile {

    private static List<TyrantOperation> tyrantOperations = new LinkedList<>();

    public static void clear() {
        tyrantOperations.clear();
    }

    public static void write(TyrantOperation operation) {
        tyrantOperations.add(operation);
    }

    public static int size() {
        return tyrantOperations.size();
    }
}
