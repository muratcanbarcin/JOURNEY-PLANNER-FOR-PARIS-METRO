package ADTPackage;

public interface QueueInterface<T> {

    public void enqueue(T newEntry);

    public T dequeue();

    public T getFront();

    public boolean isEmpty();

    public int size();

    public void clear();
}

