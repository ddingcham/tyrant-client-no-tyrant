package pe.msbaek.mock.operation;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pe.msbaek.mock.TyrantOperationDecoder;

import java.util.IllegalFormatException;

import static pe.msbaek.mock.Contexts.*;


public class TyrantOperationDecoderTest {

    private static final String NO_PREFIX = "operationCodes should start with prefix(0xC8)";
    private static final String NOT_SUPPORTED = "operator in operationCodes is not supported";
    private static final String INVALID_OPERATION_CODE_FORMAT = "invalid operationCodes format";
    private static final String INVALID_OPERATION_CODE_FORMAT_INVALID_LENGTH = INVALID_OPERATION_CODE_FORMAT + " : invalid length";
    private static final String INVALID_OPERATION_CODE_FORMAT_NOT_MATCHED_LENGTH = INVALID_OPERATION_CODE_FORMAT + " : not matched length";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private TyrantOperationDecoder decoder = new TyrantOperationDecoderImpl();

    @Test
    public void no_prefix() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage(NO_PREFIX);
        decoder.decode(RESET_OPERATION);
    }

    @Test
    public void not_supported() {
        expectedException.expect(UnsupportedOperationException.class);
        expectedException.expectMessage(NOT_SUPPORTED);
        decoder.decode(OPERATION_PREFIX, -999999);

    }

    @Test
    public void invalid_format_key_operation_no_key() {
        expectedException.expect(IllegalFormatException.class);
        expectedException.expectMessage(INVALID_OPERATION_CODE_FORMAT);
        decoder.decode(OPERATION_PREFIX, GET_OPERATION);
    }

    @Test
    public void invalid_format_key_operation_key_length_not_positive() {
        expectedException.expect(IllegalFormatException.class);
        expectedException.expectMessage(INVALID_OPERATION_CODE_FORMAT_INVALID_LENGTH);
        decoder.decode(OPERATION_PREFIX, GET_OPERATION, 0);
    }

    @Test
    public void invalid_format_key_operation_not_matched_length() {
        expectedException.expect(IllegalFormatException.class);
        expectedException.expectMessage(INVALID_OPERATION_CODE_FORMAT_NOT_MATCHED_LENGTH);
        decoder.decode(OPERATION_PREFIX, GET_OPERATION, 2, 'k');
    }

    @Test
    public void invalid_format_pair_operation_no_key_value() {
        expectedException.expect(IllegalFormatException.class);
        expectedException.expectMessage(INVALID_OPERATION_CODE_FORMAT);
        decoder.decode(OPERATION_PREFIX, PUT_OPERATION);
    }

    @Test
    public void invalid_format_pair_operation_key_length_not_positive() {
        expectedException.expect(IllegalFormatException.class);
        expectedException.expectMessage(INVALID_OPERATION_CODE_FORMAT_INVALID_LENGTH);
        decoder.decode(OPERATION_PREFIX, PUT_OPERATION, 0, 1, 'v');
    }

    @Test
    public void invalid_format_pair_operation_value_length_not_positive() {
        expectedException.expect(IllegalFormatException.class);
        expectedException.expectMessage(INVALID_OPERATION_CODE_FORMAT_INVALID_LENGTH);
        decoder.decode(OPERATION_PREFIX, PUT_OPERATION, 1, 0, 'k');
    }

    @Test
    public void invalid_format_pair_operation_not_matched_key_length() {
        expectedException.expect(IllegalFormatException.class);
        expectedException.expectMessage(INVALID_OPERATION_CODE_FORMAT_NOT_MATCHED_LENGTH);
        decoder.decode(OPERATION_PREFIX, GET_OPERATION, 2, 1, 'k', 'v');
    }

    @Test
    public void invalid_format_pair_operation_not_matched_value_length() {
        expectedException.expect(IllegalFormatException.class);
        expectedException.expectMessage(INVALID_OPERATION_CODE_FORMAT_NOT_MATCHED_LENGTH);
        decoder.decode(OPERATION_PREFIX, GET_OPERATION, 1, 2, 'k', 'v');
    }
}
