package pe.msbaek.mock.operation;

import pe.msbaek.mock.contexts.TyrantOperations;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

        if (operator.isOperatorWithKey()) {
            int keyLength = mergeBytesToInt(operationCodes, 2, 6);

            operationCodes = mergeArrays(
                    Arrays.copyOfRange(operationCodes, 0, 2),
                    new int[]{keyLength},
                    Arrays.copyOfRange(operationCodes, 6, operationCodes.length));
            validateOperationCodesWithKey(operationCodes);

            int[] keySource = Arrays.copyOfRange(operationCodes, 3, operationCodes.length);
            return TyrantOperationFactory.of(operator, convertToString(keySource));

        } else if (operator.isOperatorWithPair()) {
            int keyLength = mergeBytesToInt(operationCodes, 2, 6);
            int valueLength = mergeBytesToInt(operationCodes, 6, 10);

            operationCodes = mergeArrays(Arrays.copyOfRange(operationCodes, 0, 2),
                    new int[]{keyLength, valueLength},
                    Arrays.copyOfRange(operationCodes, 10, operationCodes.length));
            validateOperationWithPair(operationCodes);

            int[] keySource = Arrays.copyOfRange(operationCodes, 4, operationCodes.length - operationCodes[3]);
            int[] valueSource = Arrays.copyOfRange(operationCodes, operationCodes.length - operationCodes[3], operationCodes.length);
            return TyrantOperationFactory.of(operator, convertToString(keySource), convertToString(valueSource));

        }
        return TyrantOperationFactory.of(operator);
    }

    private static int[] mergeArrays(int[]... arrays) {
        return Stream.of(arrays).flatMapToInt(IntStream::of).toArray();
    }

    private static void validateOperationCodesWithKey(int[] operationCodes) {
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

    private static int mergeBytesToInt(int[] operationCodes, int fromIndex, int toIndex) {
        int result = 0;
        if(operationCodes.length < toIndex) {
            throw new IllegalFormatException(INVALID_OPERATION_CODE_FORMAT);
        }
        for (int i = fromIndex; i < toIndex - 1; i++) {
            result += operationCodes[i];
            result = result << 8;
        }
        result += operationCodes[toIndex - 1];
        return result;
    }

    static class IllegalFormatException extends IllegalArgumentException {
        IllegalFormatException(String message) {
            super(message);
        }
    }
}