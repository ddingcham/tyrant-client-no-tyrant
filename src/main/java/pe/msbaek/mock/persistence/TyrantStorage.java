package pe.msbaek.mock.persistence;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
    Tyrant Clients [N] : MockTyrant [1] : TyrantStorage [1]
 */
public class TyrantStorage {

    private static Map<String, String> storage;

    static {
        storage = new ConcurrentHashMap<>();
    }
}
