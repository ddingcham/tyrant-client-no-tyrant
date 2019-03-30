package pe.msbaek.mock;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum TyrantResponseProperties {

    SUCCESS(0),
    NOT_FOUND(1);

    private final int property;

    TyrantResponseProperties(int property) {
        this.property = property;
    }

    public static TyrantResponseProperties valueOf(int property) {
        return Arrays.stream(values())
                .filter(responseProperties -> responseProperties.matchedProperty(property))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    private boolean matchedProperty(int property) {
        return this.property == property;
    }
}
