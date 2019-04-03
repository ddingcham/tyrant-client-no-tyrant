package pe.msbaek.mock.operation;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
@ToString
public class KeyOperation implements TyrantOperation {

    private TyrantOperations operation;
    private String key;

    @Override
    public void run() {

    }
}
