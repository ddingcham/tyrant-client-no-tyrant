package pe.msbaek;

import org.junit.Before;
import org.junit.Test;
import pe.msbaek.mock.operation.TyrantOperationFactory;
import pe.msbaek.mock.operation.TyrantOperation;
import pe.msbaek.mock.operation.TyrantOperationDecoder;
import pe.msbaek.mock.operation.TyrantOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static pe.msbaek.mock.operation.TyrantOperations.*;

public class TyrantOperationDecoderTest {

    private static final int[] BYTES_OF_KEY = new int[]{'k', 'e', 'y'};
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

    @Before
    public void setUp() {
        decoder = new TyrantOperationDecoder(){
            @Override
            public TyrantOperation decode(int... operationCodes) {
                if(operationCodes[0] != OPERATION_PREFIX) {
                    throw new IllegalArgumentException("operationCodes should start with prefix(0xC8)");
                }

                TyrantOperation operation;

                if(isSingularOperator(operationCodes[1])) {
                    operation = TyrantOperationFactory.of(operationCodes[1]);
                }
                else if(isOperatorWithKey(operationCodes[1])) {
                    operation = TyrantOperationFactory.of(operationCodes[1], operationCodes[2], Arrays.copyOfRange(operationCodes, 3, operationCodes.length));
                }
                else if(isOperatorWithPair(operationCodes[1])) {
                    operation = TyrantOperationFactory.of(operationCodes[1], operationCodes[2], operationCodes[3], Arrays.copyOfRange(operationCodes, 4, operationCodes.length));
                }
                else {
                    throw new IllegalArgumentException("operation in operationCodes is not supported");
                }

                return operation;
            }

            private boolean isSingularOperator(int operator) {
                return Stream
                        .of(VANISH, SIZE, RESET, GET_NEXT_KEY)
                        .anyMatch(operation -> operation.matchedOperation(operator));
            }

            private boolean isOperatorWithKey(int operator) {
                return Stream
                        .of(GET, REMOVE)
                        .anyMatch(operation -> operation.matchedOperation(operator));
            }

            private boolean isOperatorWithPair(int operator) {
                return PUT.matchedOperation(operator);

            }
        };
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

    static class TyrantOperationBuilder {
        List<Integer> codes = new ArrayList<>();
        TyrantOperationBuilder with(int code) {
            add(code);
            return this;
        }

        TyrantOperationBuilder with(int[] codes) {
            for(int code : codes) {
                with(code);
            }
            return this;
        }

        private void add(int code) {
            codes.add(code);
        }

        public TyrantOperation validOperation(TyrantOperationDecoder decoder) {
            int[] operationCodes = codes.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();
            TyrantOperation operation = decoder.decode(operationCodes);
            if(!operation.isValid()) {
                fail();
            }
            return operation;
        }
    }

}
