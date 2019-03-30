package pe.msbaek;

import org.junit.Test;
import pe.msbaek.mock.TyrantRequestProperties;
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
        assertSame(TyrantRequestProperties.PUT, TyrantRequestProperties.valueOf(0x10));
        assertSame(TyrantRequestProperties.GET, TyrantRequestProperties.valueOf(0x30));
        assertSame(TyrantRequestProperties.VANISH, TyrantRequestProperties.valueOf(0x72));
        assertSame(TyrantRequestProperties.REMOVE, TyrantRequestProperties.valueOf(0x20));
        assertSame(TyrantRequestProperties.SIZE, TyrantRequestProperties.valueOf(0x80));
        assertSame(TyrantRequestProperties.RESET, TyrantRequestProperties.valueOf(0x50));
        assertSame(TyrantRequestProperties.GET_NEXT_KEY, TyrantRequestProperties.valueOf(0x51));
        try{
            TyrantRequestProperties.valueOf(-999999);
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
