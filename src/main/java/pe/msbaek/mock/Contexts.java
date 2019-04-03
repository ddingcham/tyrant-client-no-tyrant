package pe.msbaek.mock;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Contexts {
    public static final int PUT_OPERATION = 0x10;
    public static final int GET_OPERATION = 0x30;
    public static final int VANISH_OPERATION = 0x72;
    public static final int REMOVE_OPERATION = 0x20;
    public static final int SIZE_OPERATION = 0x80;
    public static final int RESET_OPERATION = 0x50;
    public static final int GET_NEXT_KEY_OPERATION = 0x51;
    public static final int OPERATION_PREFIX = 0xC8;
}