package stack;

import java.util.ArrayList;

public final class Stack<T> {
    private final ArrayList<T> contents;

    public Stack() {
        contents = new ArrayList<>();
    }

    public Stack(final ArrayList<T> contents) {
        this.contents = contents;
    }

    /**
     * Add an element to the top of the stack.
     *
     * @param element the element to be added to the stack
     */
    public void push(final T element) {
        contents.add(element);
    }

    /**
     * Remove the top element from the stack
     *
     * @return the element at the top of the stack
     */
    public T pop() {
        if (contents.isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }

        return contents.remove(contents.size() - 1);
    }

    /**
     * @return the top element in the stack, null if the stack is empty
     */
    public T peek() {
        if (contents.isEmpty()) {
            return null;
        }

        return contents.get(contents.size() - 1);
    }

    /**
     * @return true if the stack is empty, false otherwise
     */
    public boolean isEmpty() {
        return contents.isEmpty();
    }

    /**
     * Clear the contents of the stack.
     */
    public void clear() {
        contents.clear();
    }
}
