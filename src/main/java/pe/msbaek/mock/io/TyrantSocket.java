package pe.msbaek.mock.io;


import lombok.NonNull;
import pe.msbaek.mock.operation.TyrantOperationBuilder;
import pe.msbaek.mock.operation.TyrantOperationDecoderImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static pe.msbaek.mock.Contexts.OPERATION_PREFIX;

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
        public void write(int i) throws IOException {
            if(i == OPERATION_PREFIX && !buffer.isEmpty()) {
                flush();
            }
            buffer.add(i);
        }

        @Override
        public void write(@NonNull byte[] bytes) throws IOException {
            for(byte b: bytes) {
                write(Byte.toUnsignedInt(b));
            }
        }

        @Override
        public void flush() throws IOException {
            // TODO : synchronized write operation to file
            TyrantOperationBuilder builder = new TyrantOperationBuilder();
            buffer.forEach(code -> builder.with(code));
            TyrantSocketOutputFile.write(builder.build(new TyrantOperationDecoderImpl()));
            buffer.clear();
        }

        public int getCurrentBufferLength() {
            return buffer.size();
        }
    }
}
