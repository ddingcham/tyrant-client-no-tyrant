package pe.msbaek.mock.operation;

public class PairOperation implements TyrantOperation {

    private TyrantOperations operation;
    private String key;
    private String value;

    PairOperation(int operationCode, String key, String value) {
        operation = TyrantOperations.valueOf(operationCode);
        this.key = key;
        this.value = value;
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
