package pe.msbaek.mock;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum TyrantOperations {
    PUT(0x10),
    GET(0x30),
    VANISH(0x72),
    REMOVE(0x20),
    SIZE(0x80),
    RESET(0x50),
    GET_NEXT_KEY(0x51);

    private final int operationCode;

    TyrantOperations(int operationCode) {
        this.operationCode = operationCode;
    }

    public static TyrantOperations valueOf(int operationCode) {
        return Arrays.stream(values())
                .filter(operations -> operations.matchedOperation(operationCode))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    private boolean matchedOperation(int operationCode) {
        return this.operationCode == operationCode;
    }
}
