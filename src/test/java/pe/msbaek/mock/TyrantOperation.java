package pe.msbaek.mock;

import pe.msbaek.mock.operation.TyrantOperations;

public interface TyrantOperation {
    TyrantOperations getOperation();
    boolean isValid();
}
