package pe.msbaek.mock.io;


import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import static pe.msbaek.mock.contexts.Contexts.OPERATION_PREFIX;

public class TyrantSocket extends Socket {

    @Override
    public InputStream getInputStream() {
        return new TyrantInputStream();
    }

    @Override
    public OutputStream getOutputStream() {
        return new TyrantOutputStream();
    }

    static class TyrantInputStream extends InputStream {

        @Override
        public int read() {
            return 0;
        }
    }

    static class TyrantOutputStream extends OutputStream {

        List<Integer> buffer = new LinkedList<>();

        @Override
        public void write(int i) throws IOException {
            if (i == OPERATION_PREFIX && !buffer.isEmpty()) {
                flush();
            }
            buffer.add(i);
        }

        @Override
        public void write(@NonNull byte[] bytes) throws IOException {
            for (byte b : bytes) {
                write(Byte.toUnsignedInt(b));
            }
        }

        @Override
        public void flush() throws IOException {
            if (buffer.isEmpty()) {
                return;
            }
            TyrantInput.write(buffer
                    .stream()
                    .mapToInt(Integer::byteValue)
                    .toArray());
            buffer.clear();
        }

        public int getCurrentBufferLength() {
            return buffer.size();
        }
    }
}
