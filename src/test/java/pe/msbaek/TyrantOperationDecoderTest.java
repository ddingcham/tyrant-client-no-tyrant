package pe.msbaek;

import org.junit.Test;
import pe.msbaek.mock.TyrantOperations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TyrantOperationDecoderTest {

    private static final String KEY = "key";
    private static final int[] BYTES_OF_KEY = new int[]{'k', 'e', 'y'};
    private static final String VALUE = "value";
    private static final int[] BYTES_OF_VALUE = new int[]{'v', 'a', 'l', 'u', 'e'};

    //operation bytecode spec : TyrantMap
    private static final int OPERATION_PREFIX = 0xC8;
    private static final int PUT_OPERATION = 0x10;
    private static final int GET_OPERATION = 0x30;
    private static final int VANISH_OPERATION = 0x72;
    private static final int REMOVE_OPERATION = 0x20;
    private static final int SIZE_OPERATION = 0x80;
    private static final int RESET_OPERATION = 0x50;
    private static final int GET_NEXT_KEY_OPERATION = 0x51;
    private TyrantOperationDecoder decoder;

    @Test
    public void put_with_valid_input() {
        TyrantOperation operation = validOperation(
                PUT_OPERATION,
                BYTES_OF_KEY.length,
                BYTES_OF_VALUE.length,
                BYTES_OF_KEY,
                BYTES_OF_VALUE
        );

        assertEquals(TyrantOperations.PUT, operation.getOperation());
    }

    @Test
    public void get_with_valid_input() {
        TyrantOperation operation = validOperation(
                GET_OPERATION,
                BYTES_OF_KEY.length,
                BYTES_OF_KEY
        );

        assertEquals(TyrantOperations.GET, operation.getOperation());
    }

    @Test
    public void remove_with_valid_input() {
        TyrantOperation operation = validOperation(
                REMOVE_OPERATION,
                BYTES_OF_KEY.length,
                BYTES_OF_KEY
        );

        assertEquals(TyrantOperations.REMOVE, operation.getOperation());
    }

    @Test
    public void vanish_with_valid_input() {
        TyrantOperation operation = validOperation(
                VANISH_OPERATION
        );

        assertEquals(TyrantOperations.VANISH, operation.getOperation());
    }

    @Test
    public void size_with_valid_input() {
        TyrantOperation operation = validOperation(
                SIZE_OPERATION
        );

        assertEquals(TyrantOperations.SIZE, operation.getOperation());
    }

    @Test
    public void reset_with_valid_input() {
        TyrantOperation operation = validOperation(
                RESET_OPERATION
        );

        assertEquals(TyrantOperations.RESET, operation.getOperation());
    }

    @Test
    public void get_next_key_with_valid_input() {
        TyrantOperation operation = validOperation(
                GET_NEXT_KEY_OPERATION
        );

        assertEquals(TyrantOperations.PUT, operation.getOperation());
    }

    private TyrantOperation validOperation(int... bytes) {
        TyrantOperation operation = decoder.decode(OPERATION_PREFIX + bytes);
        if(!operation.isValid()) {
            fail();
        }
        return operation;
    }

}
