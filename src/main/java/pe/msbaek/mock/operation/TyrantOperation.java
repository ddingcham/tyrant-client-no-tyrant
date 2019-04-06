package pe.msbaek.mock.operation;


import java.time.LocalDateTime;

public abstract class TyrantOperation implements Comparable<TyrantOperation> {

    private LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public int compareTo(TyrantOperation others) {
        return this.createdAt.compareTo(others.createdAt);
    }
}
