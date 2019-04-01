package pe.msbaek.mock.operation;

public class TyrantOperationFactory {

    public static TyrantOperation of(int operationCode) {
        return new SingularOperation(operationCode);
    }

    public static TyrantOperation of(int operationCode, int keyLength, int[] properties) {
        return new KeyOperation(operationCode, String.valueOf(properties));
    }

    public static TyrantOperation of(int operationCode, int keyLength, int valueLength, int[] properties) {
        return new PairOperation(operationCode, String.valueOf(properties), String.valueOf(properties));
    }
}
