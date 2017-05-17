import java.util.*;

/**
 * The <code>JSStack</code> handles storage of the data in the
 * by storing instances of the different enums in <code>JSStack</code>
 * here. Generics are used here, but they mainly operate with
 * <code>BlockTypes</code>.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 */
public class JSStack<E> extends Stack<E> {

    /**
     * Pushes or adds the <code>BlockType</code> on top of the stack
     * so it is placed where the last element was added.
     *
     * @param b
     *      A generic object that gets pushed into <code>JSStack</code> of
     *      type <code>BlockType</code>.
     *
     * @return
     *      Returns a generic type object in the stack which is a
     *      <code>BlockType</code> enum constant.
     */
    public E push(E b) {
        return super.push(b);
    }

    /**
     * Pops or removes the <code>BlockType</code> enum constant at the top of
     * the
     * <code>JSStack</code>.
     *
     * @return
     *      Returns the <code>BlockType</code> enum constant that was just
     *      popped
     *      or removed from the stack. This throws an
     *      <code>EmptyStackException</code> when the stack is empty.
     */
    public E pop() {
        if (isEmpty())
            throw new EmptyStackException("Error: The Stack is empty.");
        return super.pop();
    }

    /**
     * This method returns the <code>BlockType</code> enum constant at the top
     * of the stack without modifying the order. It would return nu
     *
     * @return
     *      This returns the <code>BlockType</code> enum constant at the top of
     *      the
     *      stack without modifying the order. This would throw an
     *      <code>EmptyStackException</code> if the stack is empty.
     */
    public E peek() {
        if (isEmpty())
            return null;
        return super.peek();
    }

    /**
     * Returns whether if the stack is empty or not. If the
     * stack is empty, it returns true. Else, it returns
     * false.
     *
     * @return
     *      Returns true if the stack is empty and false if the stack
     *      has at least 1 object.
     */
    public boolean isEmpty() {
        return super.isEmpty();
    }
}
