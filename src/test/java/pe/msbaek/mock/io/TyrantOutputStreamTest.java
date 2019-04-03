package pe.msbaek.mock.io;

import org.junit.Before;
import org.junit.Test;
import pe.msbaek.mock.io.TyrantSocket.TyrantOutputStream;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static pe.msbaek.mock.Contexts.OPERATION_PREFIX;

public class TyrantOutputStreamTest {

    private TyrantOutputStream output;

    @Before
    public void setUp() {
        output = new TyrantSocket.TyrantOutputStream();
    }

    @Test
    public void write_int() throws IOException {
        assertThat(0, is(output.getCurrentBufferLength()));
        output.write(OPERATION_PREFIX);
        assertThat(1, is(output.getCurrentBufferLength()));
    }

    @Test
    public void write_int_when_prefix_then_flush() throws IOException {
        assertThat(0, is(output.getCurrentBufferLength()));
        output.write(0);
        output.write(0);
        assertThat(2, is(output.getCurrentBufferLength()));
        output.write(OPERATION_PREFIX);
        assertThat(1, is(output.getCurrentBufferLength()));
    }

    @Test
    public void write_bytes() throws IOException {
        assertThat(0, is(output.getCurrentBufferLength()));
        output.write(new byte[]{1, 2, 3});
        assertThat(3, is(output.getCurrentBufferLength()));
    }

    @Test
    public void write_bytes_when_prefix_then_flush() throws IOException {
        assertThat(0, is(output.getCurrentBufferLength()));
        output.write(new byte[]{1, 2, (byte) OPERATION_PREFIX, 4, 5});
        assertThat(3, is(output.getCurrentBufferLength()));
    }


}
