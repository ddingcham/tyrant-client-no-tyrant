package pe.msbaek.mock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

//TyrantOperationBuilder bytecode spec : TyrantMap
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestContexts {
    public static final String KEY = "key";
    public static final String VALUE = "value";
    public static final int[] BYTES_OF_KEY = new int[]{'k', 'e', 'y'};
    public static final int[] BYTES_OF_VALUE = new int[]{'v', 'a', 'l', 'u', 'e'};
}
