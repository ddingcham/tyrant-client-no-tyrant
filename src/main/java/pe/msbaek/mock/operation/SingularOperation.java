package pe.msbaek.mock.operation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import pe.msbaek.mock.contexts.TyrantOperations;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
public class SingularOperation extends TyrantOperation {

    private TyrantOperations operation;

}
