package pe.msbaek.mock.operation;

import pe.msbaek.mock.TyrantOperation;
import pe.msbaek.mock.TyrantOperationDecoder;

import java.util.Arrays;
import java.util.stream.Stream;

import static pe.msbaek.mock.operation.TyrantOperations.*;

public class TyrantOperationDecoderImpl implements TyrantOperationDecoder {

    public static final int OPERATION_PREFIX = 0xC8;

    @Override
    public TyrantOperation decode(int... operationCodes) {
        if (operationCodes[0] != OPERATION_PREFIX) {
            throw new IllegalArgumentException("operationCodes should start with prefix(0xC8)");
        }

        TyrantOperation operation;

        if (isSingularOperator(operationCodes[1])) {
            operation = TyrantOperationFactory.of(operationCodes[1]);
        } else if (isOperatorWithKey(operationCodes[1])) {
            operation = TyrantOperationFactory.of(operationCodes[1], operationCodes[2], Arrays.copyOfRange(operationCodes, 3, operationCodes.length));
        } else if (isOperatorWithPair(operationCodes[1])) {
            operation = TyrantOperationFactory.of(operationCodes[1], operationCodes[2], operationCodes[3], Arrays.copyOfRange(operationCodes, 4, operationCodes.length));
        } else {
            throw new IllegalArgumentException("TyrantOperationBuilder in operationCodes is not supported");
        }

        return operation;
    }

    private boolean isSingularOperator(int operator) {
        return Stream
                .of(VANISH, SIZE, RESET, GET_NEXT_KEY)
                .anyMatch(operation -> operation.matchedOperation(operator));
    }

    private boolean isOperatorWithKey(int operator) {
        return Stream
                .of(GET, REMOVE)
                .anyMatch(operation -> operation.matchedOperation(operator));
    }

    private boolean isOperatorWithPair(int operator) {
        return PUT.matchedOperation(operator);

    }
}
