package pe.msbaek;

import org.junit.Test;
import pe.msbaek.mock.TyrantOperations;
import pe.msbaek.mock.TyrantResponseProperties;
import pe.msbaek.mock.TyrantSocket;

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

    @Test
    public void request_property_specification() {
        assertSame(TyrantOperations.PUT, TyrantOperations.valueOf(0x10));
        assertSame(TyrantOperations.GET, TyrantOperations.valueOf(0x30));
        assertSame(TyrantOperations.VANISH, TyrantOperations.valueOf(0x72));
        assertSame(TyrantOperations.REMOVE, TyrantOperations.valueOf(0x20));
        assertSame(TyrantOperations.SIZE, TyrantOperations.valueOf(0x80));
        assertSame(TyrantOperations.RESET, TyrantOperations.valueOf(0x50));
        assertSame(TyrantOperations.GET_NEXT_KEY, TyrantOperations.valueOf(0x51));
        try{
            TyrantOperations.valueOf(-999999);
            fail();
        } catch(Exception e) {
            // pass
        }
    }

    @Test
    public void response_property_specification() {
        assertSame(TyrantResponseProperties.SUCCESS, TyrantResponseProperties.valueOf(0));
        assertSame(TyrantResponseProperties.NOT_FOUND, TyrantResponseProperties.valueOf(1));
        try{
            TyrantResponseProperties.valueOf(-9999);
            fail();
        } catch(Exception e) {
            // pass
        }
    }
}
