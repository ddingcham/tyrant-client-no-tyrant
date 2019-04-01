package pe.msbaek;

import org.junit.Before;
import org.junit.Test;
import pe.msbaek.mock.TyrantOperation;
import pe.msbaek.mock.TyrantOperationDecoder;
import pe.msbaek.mock.operation.TyrantOperationBuilder;
import pe.msbaek.mock.operation.*;

import static org.junit.Assert.assertEquals;
import static pe.msbaek.mock.operation.TyrantOperationDecoderImpl.OPERATION_PREFIX;
import static pe.msbaek.mock.operation.TyrantOperations.*;

public class TyrantOperationDecoderTest {

    private static final int[] BYTES_OF_KEY = new int[]{'k', 'e', 'y'};
    private static final int[] BYTES_OF_VALUE = new int[]{'v', 'a', 'l', 'u', 'e'};

    //TyrantOperationBuilder bytecode spec : TyrantMap
    private static final int PUT_OPERATION = 0x10;
    private static final int GET_OPERATION = 0x30;
    private static final int VANISH_OPERATION = 0x72;
    private static final int REMOVE_OPERATION = 0x20;
    private static final int SIZE_OPERATION = 0x80;
    private static final int RESET_OPERATION = 0x50;
    private static final int GET_NEXT_KEY_OPERATION = 0x51;
    private TyrantOperationDecoder decoder;

    @Before
    public void setUp() {
        decoder = new TyrantOperationDecoderImpl();
    }

    @Test
    public void put_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(PUT_OPERATION)
                .with(BYTES_OF_KEY.length)
                .with(BYTES_OF_VALUE.length)
                .with(BYTES_OF_KEY)
                .with(BYTES_OF_VALUE)
                .validOperation(decoder);

        assertEquals(TyrantOperations.PUT, operation.getOperation());
    }

    @Test
    public void get_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(GET_OPERATION)
                .with(BYTES_OF_KEY.length)
                .with(BYTES_OF_KEY)
                .validOperation(decoder);

        assertEquals(TyrantOperations.GET, operation.getOperation());
    }

    @Test
    public void remove_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(REMOVE_OPERATION)
                .with(BYTES_OF_KEY.length)
                .with(BYTES_OF_KEY)
                .validOperation(decoder);

        assertEquals(TyrantOperations.REMOVE, operation.getOperation());
    }

    @Test
    public void vanish_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(VANISH_OPERATION)
                .validOperation(decoder);

        assertEquals(VANISH, operation.getOperation());
    }

    @Test
    public void size_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(SIZE_OPERATION)
                .validOperation(decoder);

        assertEquals(SIZE, operation.getOperation());
    }

    @Test
    public void reset_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(RESET_OPERATION)
                .validOperation(decoder);

        assertEquals(TyrantOperations.RESET, operation.getOperation());
    }

    @Test
    public void get_next_key_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(GET_NEXT_KEY_OPERATION)
                .validOperation(decoder);

        assertEquals(TyrantOperations.GET_NEXT_KEY, operation.getOperation());
    }

    private TyrantOperationBuilder prefixedOperationBuilder() {
        return new TyrantOperationBuilder().with(OPERATION_PREFIX);
    }

}
