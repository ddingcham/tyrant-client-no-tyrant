package pe.msbaek.mock;

import org.junit.Test;
import pe.msbaek.mock.operation.TyrantOperations;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class TyrantSpecificationTest {
    @Test
    public void operation_property_specification() {
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
        } catch(NoSuchElementException e) {
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
        } catch(NoSuchElementException e) {
            // pass
        }
    }
}
