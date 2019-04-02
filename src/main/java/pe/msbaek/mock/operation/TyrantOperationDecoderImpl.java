package pe.msbaek.mock.operation;

import pe.msbaek.mock.TyrantOperation;
import pe.msbaek.mock.TyrantOperationDecoder;

import java.util.Arrays;

public class TyrantOperationDecoderImpl implements TyrantOperationDecoder {

    public static final int OPERATION_PREFIX = 0xC8;

    @Override
    public TyrantOperation decode(int... operationCodes) {
        if (operationCodes[0] != OPERATION_PREFIX) {
            throw new IllegalArgumentException("operationCodes should start with prefix(0xC8)");
        }

        TyrantOperations operator = TyrantOperations.valueOf(operationCodes[1]);

        if (operator == TyrantOperations.NOT_SUPPORTED) {
            throw new IllegalArgumentException("operator in operationCodes is not supported");
        }

        int length = operationCodes.length;

        if (operator.isSingularOperator()) {

            return TyrantOperationFactory.of(operator);

        } else if (operator.isOperatorWithKey() && operationCodes[2] == length - 3) {

            int[] keySource = Arrays.copyOfRange(operationCodes, 3, length);
            return TyrantOperationFactory.of(operator, convertToString(keySource));

        } else if (operator.isOperatorWithPair() && operationCodes[2] + operationCodes[3] == length - 4) {

            int[] keySource = Arrays.copyOfRange(operationCodes, 4, length - operationCodes[3]);
            int[] valueSource = Arrays.copyOfRange(operationCodes, length - operationCodes[3], length);
            return TyrantOperationFactory.of(operator, convertToString(keySource), convertToString(valueSource));

        } else {
            throw new IllegalArgumentException("invalid operationCodes format");
        }
    }

    private String convertToString(int[] source) {
        StringBuilder builder = new StringBuilder();
        Arrays.stream(source).forEach(item -> builder.append((char)item));
        return builder.toString();
    }
}
