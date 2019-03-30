package pe.msbaek.mock;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum TyrantRequestProperties {
    PUT(0x10),
    GET(0x30),
    VANISH(0x72),
    REMOVE(0x20),
    SIZE(0x80),
    RESET(0x50),
    GET_NEXT_KEY(0x51);

    private final int property;

    TyrantRequestProperties(int property) {
        this.property = property;
    }

    public static TyrantRequestProperties valueOf(int property) {
        return Arrays.stream(values())
                .filter(requestProperty -> requestProperty.matchedProperty(property))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    private boolean matchedProperty(int property) {
        return this.property == property;
    }
}
