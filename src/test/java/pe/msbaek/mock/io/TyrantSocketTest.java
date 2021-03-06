package pe.msbaek.mock.io;

import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TyrantSocketTest {

    @Test
    public void close() throws IOException {
        TyrantSocket socket = new TyrantSocket();

        assertThat(socket.isClosed(), is(false));
        socket.close();
        assertThat(socket.isClosed(), is(true));
    }
}
