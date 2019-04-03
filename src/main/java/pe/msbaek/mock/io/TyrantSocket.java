package pe.msbaek.mock.io;


import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TyrantSocket extends Socket {
    @Override
    public InputStream getInputStream() {
        return new TyrantInputStream();
    }

    @Override
    public OutputStream getOutputStream() {
        return new TyrantOutputStream();
    }

    @Override
    public synchronized void close() throws IOException {
        super.close();
    }

    static class TyrantInputStream extends InputStream {

        private int offset;

        @Override
        public int read() {
            return 0;
        }
    }

    static class TyrantOutputStream extends OutputStream {

        List<Integer> buffer = new ArrayList<>();

        @Override
        public void write(int b) {
            TyrantSocketOutputFile.write(b);
        }

        @Override
        public void write(@NonNull byte[] b) throws IOException {

        }

        @Override
        public void flush() throws IOException {

        }

        public int getCurrentBufferLength() {
            return buffer.size();
        }
    }
}
