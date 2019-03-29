package pe.msbaek;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;

class TyrantMap implements Iterable<byte []> {
	private static final int OPERATION_PREFIX = 0xC8;
	private static final int PUT_OPERATION = 0x10;
	private static final int GET_OPERATION = 0x30;
	private static final int VANISH_OPERATION = 0x72;
	private static final int REMOVE_OPERATION = 0x20;
	private static final int SIZE_OPERATION = 0x80;
	private static final int RESET_OPERATION = 0x50;
	private static final int GET_NEXT_KEY_OPERATION = 0x51;

	private static final int NOT_FOUND = 1;
	private static final int SUCCESS = 0;
	private Socket socket;
	private DataOutputStream writer;
	private DataInputStream reader;

	public TyrantMap() {}

	public TyrantMap(Socket socket) {
		if(socket == null) {
			throw new IllegalArgumentException();
		}
		this.socket = socket;
	}

	public void put(byte[] key, byte[] value) throws IOException {
		writeOperation(PUT_OPERATION);
		writer.writeInt(key.length);
		writer.writeInt(value.length);
		writer.write(key);
		writer.write(value);
		int status = reader.read();
		if (status != 0)
			throw new IllegalStateException("status[" + status + "] is not 0");
	}

	public void open() throws IOException {
		if(socket == null) {
			socket = new Socket("localhost", 1978);
		}
		writer = new DataOutputStream(socket.getOutputStream());
		reader = new DataInputStream(socket.getInputStream());
	}

	public void close() throws IOException {
		socket.close();
	}

	public byte[] get(byte[] key) {
		try {
			writeOperation(GET_OPERATION);
			writer.writeInt(key.length);
			writer.write(key);
			return readBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void clear() throws IOException {
		writeOperation(VANISH_OPERATION);
		int status = reader.read();
		if (status != 0)
			throw new IllegalStateException("status[" + status + "] is not 0");
	}

	public void remove(byte[] key) throws IOException {
		if(key == null)
			throw new IllegalArgumentException();
		writeOperation(REMOVE_OPERATION);
		writer.writeInt(key.length);
		writer.write(key);
		int status = reader.read();
		if(status == NOT_FOUND)
			return;
		if(status != SUCCESS)
			throw new IllegalStateException("status[" + status + "] is not 0");
	}

	public long size() throws IOException {
		writeOperation(SIZE_OPERATION);
		int status = reader.read();
		if(status != SUCCESS)
			throw new IllegalStateException("status[" + status + "] is not 0");
		return reader.readLong();
	}

	public Iterator<byte[]> iterator() {
		try {
			reset();
			final byte[] firstKey = getNextKey();
			return new Iterator<byte[]>() {
				public byte[] previousKey;
				byte [] nextKey = firstKey;
				public boolean hasNext() {
					return nextKey != null;
				}

				public byte[] next() {
					byte[] results = get(nextKey);
					previousKey = nextKey;
					nextKey = getNextKey();
					return results;
				}

				public void remove() {
					try {
						TyrantMap.this.remove(previousKey);
					} catch (IllegalArgumentException e) {
						throw new IllegalStateException(e);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			};
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void reset() throws IOException {
		writeOperation(RESET_OPERATION);
		int status = reader.read();
		if (status != 0)
			throw new IllegalStateException("status[" + status + "] is not 0");
	}

	private void writeOperation(int operationCode) throws IOException {
		writer.write(OPERATION_PREFIX);
		writer.write(operationCode);
	}

	private byte[] getNextKey() {
		try {
			writeOperation(GET_NEXT_KEY_OPERATION);
			return readBytes();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] readBytes() throws IOException {
		int status = reader.read();
		if (status == NOT_FOUND)
			return null;
		else if(status != SUCCESS)
			throw new IllegalStateException("status[" + status + "] is not 0");
		int length = reader.readInt();
		byte [] results = new byte [length];
		reader.read(results);
		return results;
	}
}
