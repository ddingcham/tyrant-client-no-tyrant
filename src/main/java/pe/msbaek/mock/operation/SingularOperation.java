package pe.msbaek.mock.operation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pe.msbaek.mock.TyrantOperation;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
public class SingularOperation implements TyrantOperation {

    private TyrantOperations operation;

    @Override
    public void run() {

    }
}