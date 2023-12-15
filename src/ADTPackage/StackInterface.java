package ADTPackage;

public interface StackInterface<T> {

    public void push(T newEntry);

    public T pop();

    public T peek();

    public boolean isEmpty();

    public int size();

    public void clear();
}
