package pe.msbaek.mock.operation;

import pe.msbaek.mock.TyrantOperation;
import pe.msbaek.mock.TyrantOperationDecoder;

import java.util.ArrayList;
import java.util.List;

public class TyrantOperationBuilder {
    private List<Integer> codes = new ArrayList<>();

    public TyrantOperationBuilder with(int code) {
        add(code);
        return this;
    }

    public TyrantOperationBuilder with(int[] codes) {
        for (int code : codes) {
            with(code);
        }
        return this;
    }

    private void add(int code) {
        codes.add(code);
    }

    public TyrantOperation build(TyrantOperationDecoder decoder) {
        int[] operationCodes = codes.stream()
                .mapToInt(Integer::intValue)
                .toArray();
        TyrantOperation operation = decoder.decode(operationCodes);
        return operation;
    }
}
