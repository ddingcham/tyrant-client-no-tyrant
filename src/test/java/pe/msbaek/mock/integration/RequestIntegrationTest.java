package pe.msbaek.mock.integration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pe.msbaek.mock.io.TyrantSocket;
import pe.msbaek.mock.io.TyrantSocketOutputFile;

import java.io.DataOutputStream;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static pe.msbaek.mock.Contexts.*;
import static pe.msbaek.mock.TestContexts.*;

public class RequestIntegrationTest {

    TyrantSocket socket;
    DataOutputStream writer;

    @Before
    public void setUp() {
        socket = new TyrantSocket();
        writer = new DataOutputStream(socket.getOutputStream());
    }

    @Test
    public void put() throws IOException {
        assertThat(TyrantSocketOutputFile.size(), is(0));
        writeOperation(PUT_OPERATION);
        writer.writeInt(KEY.length());
        writer.writeInt(VALUE.length());
        writer.write(KEY.getBytes());
        writer.write(VALUE.getBytes());
        assertThat(TyrantSocketOutputFile.size(), is(1));
    }

    @Test
    public void get() throws IOException {
        assertThat(TyrantSocketOutputFile.size(), is(0));
        writeOperation(GET_OPERATION);
        writer.writeInt(KEY.length());
        writer.write(KEY.getBytes());
        assertThat(TyrantSocketOutputFile.size(), is(1));
    }

    @Test
    public void remove() throws IOException {
        assertThat(TyrantSocketOutputFile.size(), is(0));
        writeOperation(REMOVE_OPERATION);
        assertThat(TyrantSocketOutputFile.size(), is(1));
    }

    @Test
    public void vanish() throws IOException {
        assertThat(TyrantSocketOutputFile.size(), is(0));
        writeOperation(VANISH_OPERATION);
        assertThat(TyrantSocketOutputFile.size(), is(1));
    }

    @Test
    public void size() throws IOException {
        assertThat(TyrantSocketOutputFile.size(), is(0));
        writeOperation(SIZE_OPERATION);
        assertThat(TyrantSocketOutputFile.size(), is(1));
    }

    @Test
    public void reset() throws IOException {
        assertThat(TyrantSocketOutputFile.size(), is(0));
        writeOperation(RESET_OPERATION);
        assertThat(TyrantSocketOutputFile.size(), is(1));
    }

    @Test
    public void get_next_key() throws IOException {
        assertThat(TyrantSocketOutputFile.size(), is(0));
        writeOperation(GET_NEXT_KEY_OPERATION);
        assertThat(TyrantSocketOutputFile.size(), is(1));
    }

    @After
    public void tearDown() throws IOException {
        socket.close();
        writer.close();
        TyrantSocketOutputFile.clear();
    }

    private void writeOperation(int operationCode) throws IOException {
        writer.write(OPERATION_PREFIX);
        writer.write(operationCode);
    }
}
