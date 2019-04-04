package pe.msbaek.mock.operation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class TyrantOperationFactory {

    private static final String NOT_SUPPORTED = "operator in operationCodes is not supported";

    static TyrantOperation of(TyrantOperations operator) {
        validateOperator(operator);
        return new SingularOperation(operator);
    }

    static TyrantOperation of(TyrantOperations operator, String key) {
        validateOperator(operator);
        return new KeyOperation(operator, key);
    }

    static TyrantOperation of(TyrantOperations operator, String key, String value) {
        validateOperator(operator);
        return new PairOperation(operator, key, value);
    }

    private static void validateOperator(TyrantOperations operator) {
        if (operator == TyrantOperations.NOT_SUPPORTED) {
            throw new UnsupportedOperationException(NOT_SUPPORTED);
        }
    }

}