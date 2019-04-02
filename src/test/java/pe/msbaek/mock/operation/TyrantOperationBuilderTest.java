package pe.msbaek.mock.operation;

import org.junit.Before;
import org.junit.Test;
import pe.msbaek.mock.TyrantOperation;
import pe.msbaek.mock.TyrantOperationDecoder;

import static org.junit.Assert.assertEquals;
import static pe.msbaek.mock.operation.TyrantOperationDecoderImpl.OPERATION_PREFIX;
import static pe.msbaek.mock.operation.TyrantOperations.*;
import static pe.msbaek.mock.Contexts.*;

public class TyrantOperationBuilderTest {

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
                .build(decoder);

        assertEquals(new PairOperation(PUT, KEY, VALUE), operation);
    }

    @Test
    public void get_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(GET_OPERATION)
                .with(BYTES_OF_KEY.length)
                .with(BYTES_OF_KEY)
                .build(decoder);

        assertEquals(new KeyOperation(GET, KEY), operation);
    }

    @Test
    public void remove_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(REMOVE_OPERATION)
                .with(BYTES_OF_KEY.length)
                .with(BYTES_OF_KEY)
                .build(decoder);

        assertEquals(new KeyOperation(REMOVE, KEY), operation);
    }

    @Test
    public void vanish_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(VANISH_OPERATION)
                .build(decoder);

        assertEquals(new SingularOperation(VANISH), operation);
    }

    @Test
    public void size_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(SIZE_OPERATION)
                .build(decoder);

        assertEquals(new SingularOperation(SIZE), operation);
    }

    @Test
    public void reset_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(RESET_OPERATION)
                .build(decoder);

        assertEquals(new SingularOperation(RESET), operation);
    }

    @Test
    public void get_next_key_with_valid_input() {
        TyrantOperation operation = prefixedOperationBuilder()
                .with(GET_NEXT_KEY_OPERATION)
                .build(decoder);

        assertEquals(new SingularOperation(GET_NEXT_KEY), operation);
    }

    private TyrantOperationBuilder prefixedOperationBuilder() {
        return new TyrantOperationBuilder().with(OPERATION_PREFIX);
    }

}
