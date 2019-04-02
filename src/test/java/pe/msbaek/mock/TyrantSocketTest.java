package pe.msbaek.mock;

import org.junit.Test;
import pe.msbaek.mock.operation.TyrantOperations;
import pe.msbaek.mock.TyrantResponseProperties;
import pe.msbaek.mock.TyrantSocket;

import java.io.IOException;
import java.util.NoSuchElementException;

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
