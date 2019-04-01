package pe.msbaek.mock.operation;

public class KeyOperation implements TyrantOperation {

    private TyrantOperations operation;
    private String key;

    KeyOperation(int operationCode, String key) {
        operation = TyrantOperations.valueOf(operationCode);
        this.key = key;
    }

    @Override
    public TyrantOperations getOperation() {
        return operation;
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
