package pe.msbaek.mock.operation;

import pe.msbaek.mock.TyrantOperation;

public class SingularOperation implements TyrantOperation {

    private TyrantOperations operation;

    SingularOperation(int operationCode) {
        operation = TyrantOperations.valueOf(operationCode);
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
