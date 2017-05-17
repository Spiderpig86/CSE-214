import java.util.*;

/**
 * The <code>DownloadQueue</code> class extends the <code>LinkedList</code>
 * class to allow it to store <code>DownloadJob</code> objects with methods
 * that make this act like a <code>Queue</code>.
 *
 * @param <E>
 *     The generic data type used in this <code>DownloadQueue</code> class.
 *
 * @author: Stanley Lim
 *      Email: stanley.lim@stonybrook.edu
 *      Stony Brook ID: 110869393
 *
 */
public class DownloadQueue<E> extends LinkedList<E> {

    /**
     * Adds or enqueues <code>DownloadJob</code> objects to this collection
     * and returns a
     * boolean showing if the object was successfully added.
     *
     * @param downloadItem
     *      Adds a <code>DownloadJob</code> to the queue.
     * @return
     *      Boolean denoting if the item was successfully added or not.
     */
    public boolean enqueue(E downloadItem) {
        return super.add(downloadItem);
    }

    /**
     * Removes a <code>DownloadObject</code> from the queue and returns the
     * object that was removed.
     *
     * @return
     *      Returns the <code>DownloadObject</code> that was removed.
     *
     * @throws EmptyQueueException
     *      Exception is thrown when the program tries to remove a
     *      <code>DownloadJob</code> but the queue is empty.
     */
    public E dequeue() throws EmptyQueueException {
        if (isEmpty())
            throw new EmptyQueueException();
        return super.remove();
    }

    /**
     * Returns the element at the head of the queue (or the first one that
     * was added to the queue).
     *
     * @return
     *      The <code>DownloadJob</code> at the head of the list.
     */
    public E peek() throws EmptyQueueException {
        if (isEmpty())
            throw new EmptyQueueException("Error: Queue is empty.");
        return super.peek();
    }

    /**
     * Returns a boolean showing if the queue if it is empty.
     *
     * @return
     *      Returns true if the queue is empty and returns false if the queue
     *      has at least 1 <code>DownloadJob</code>.
     */
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Returns the string representation for the <code>DownloadJob</code>.
     *
     * @return
     *      Returns the string representation of the object with job id and
     *      download size. Returns "empty" if the queue has no objects.
     */
    public String toString() { // Return String representation with all the
        // items in queue
        String curQueue = "";

        // Return "empty" if the queue is empty
        if (this.size() == 0) {
            return (curQueue = "empty");
        }

        // Loop over queue if not empty
        for (int i = 0; i < this.size(); i++) {
            DownloadJob curJob = (DownloadJob) this.get(i);
            curQueue += String.format("[#%d:%smb] ", curJob.getId(), curJob
                    .getDownloadSize());
        }

        return curQueue;
    }
}
