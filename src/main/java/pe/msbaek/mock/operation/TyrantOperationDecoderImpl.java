package pe.msbaek.mock.operation;

import pe.msbaek.mock.TyrantOperation;
import pe.msbaek.mock.TyrantOperationDecoder;

import java.util.Arrays;

public class TyrantOperationDecoderImpl implements TyrantOperationDecoder {

    private static final int OPERATION_PREFIX = 0xC8;
    private static final String NO_PREFIX = "operationCodes should start with prefix(0xC8)";
    private static final String INVALID_OPERATION_CODE_FORMAT = "invalid operationCodes format";
    private static final String INVALID_OPERATION_CODE_FORMAT_INVALID_LENGTH = INVALID_OPERATION_CODE_FORMAT + " : invalid length";
    private static final String INVALID_OPERATION_CODE_FORMAT_NOT_MATCHED_LENGTH = INVALID_OPERATION_CODE_FORMAT + " : not matched length";

    @Override
    public TyrantOperation decode(int... operationCodes) {
        if (operationCodes[0] != OPERATION_PREFIX) {
            throw new IllegalArgumentException(NO_PREFIX);
        }

        TyrantOperations operator = TyrantOperations.valueOf(operationCodes[1]);

        int length = operationCodes.length;

        if (operator.isOperatorWithKey()) {
            validateKeyOperationCodes(operationCodes);

            int[] keySource = Arrays.copyOfRange(operationCodes, 3, length);
            return TyrantOperationFactory.of(operator, convertToString(keySource));

        } else if (operator.isOperatorWithPair()) {
            validateOperationWithPair(operationCodes);

            int[] keySource = Arrays.copyOfRange(operationCodes, 4, length - operationCodes[3]);
            int[] valueSource = Arrays.copyOfRange(operationCodes, length - operationCodes[3], length);
            return TyrantOperationFactory.of(operator, convertToString(keySource), convertToString(valueSource));

        }
        return TyrantOperationFactory.of(operator);
    }

    private static void validateKeyOperationCodes(int[] operationCodes) {
        if (operationCodes.length < 3) {
            throw new IllegalFormatException(INVALID_OPERATION_CODE_FORMAT);
        } else if (operationCodes[2] <= 0) {
            throw new IllegalFormatException(INVALID_OPERATION_CODE_FORMAT_INVALID_LENGTH);
        } else if (operationCodes[2] != operationCodes.length - 3) {
            throw new IllegalFormatException(INVALID_OPERATION_CODE_FORMAT_NOT_MATCHED_LENGTH);
        }
    }

    private static void validateOperationWithPair(int[] operationCodes) {
        if (operationCodes.length < 4) {
            throw new IllegalFormatException(INVALID_OPERATION_CODE_FORMAT);
        } else if (operationCodes[2] <= 0 || operationCodes[3] <= 0) {
            throw new IllegalFormatException(INVALID_OPERATION_CODE_FORMAT_INVALID_LENGTH);
        } else if (operationCodes[2] + operationCodes[3] != operationCodes.length - 4) {
            throw new IllegalFormatException(INVALID_OPERATION_CODE_FORMAT_NOT_MATCHED_LENGTH);
        }
    }

    private String convertToString(int[] source) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(source).forEach(item -> builder.append((char) item));
        return builder.toString();
    }

    static class IllegalFormatException extends IllegalArgumentException {
        IllegalFormatException(String message) {
            super(message);
        }
    }
}