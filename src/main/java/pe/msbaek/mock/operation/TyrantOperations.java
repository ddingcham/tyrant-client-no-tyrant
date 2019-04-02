package pe.msbaek.mock.operation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum TyrantOperations {
    PUT(0x10),
    GET(0x30),
    VANISH(0x72),
    REMOVE(0x20),
    SIZE(0x80),
    RESET(0x50),
    GET_NEXT_KEY(0x51),
    NOT_SUPPORTED(0x00);

    private static final Set<TyrantOperations> SINGULAR_OPERATION_SET;
    private static final Set<TyrantOperations> KEY_OPERATION_SET;
    private static final Set<TyrantOperations> PAIR_OPERATION_SET;
    private final int operationCode;

    static {
        SINGULAR_OPERATION_SET = Stream.of(VANISH, SIZE, RESET, GET_NEXT_KEY)
                .collect(Collectors.toSet());
        KEY_OPERATION_SET = Stream.of(GET, REMOVE)
                .collect(Collectors.toSet());
        PAIR_OPERATION_SET = Stream.of(PUT)
                .collect(Collectors.toSet());
    }

    TyrantOperations(int operationCode) {
        this.operationCode = operationCode;
    }

    public static TyrantOperations valueOf(int operationCode) {
        return Arrays.stream(values())
                .filter(operations -> operations.matchedOperation(operationCode))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    public boolean matchedOperation(int operationCode) {
        return this.operationCode == operationCode;
    }

    public boolean isSingularOperator() {
        return SINGULAR_OPERATION_SET.contains(this);
    }

    public boolean isOperatorWithKey() {
        return KEY_OPERATION_SET.contains(this);
    }

    public boolean isOperatorWithPair() {
        return PAIR_OPERATION_SET.contains(this);
    }
}
