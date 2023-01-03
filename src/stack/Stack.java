package stack;

import java.util.ArrayList;

public final class Stack<T> {
    private final ArrayList<T> contents;

    public Stack() {
        contents = new ArrayList<>();
    }

    public Stack(ArrayList<T> contents) {
        this.contents = contents;
    }

    public void push(final T element) {
        contents.add(element);
    }

    public T pop() {
        if (contents.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }

        return contents.remove(contents.size() - 1);
    }

    public T peek() {
        if (contents.isEmpty()) {
            return null;
        }

        return contents.get(contents.size() - 1);
    }

    public boolean isEmpty() {
        return contents.isEmpty();
    }

    public void clear() {
        contents.clear();
    }
}
