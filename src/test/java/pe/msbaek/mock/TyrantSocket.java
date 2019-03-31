package pe.msbaek.mock;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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

        @Override
        public void write(int b) {
            TyrantSocketFile.write(b);
        }
    }
}
