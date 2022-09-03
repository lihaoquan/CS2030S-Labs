/**
 * Class that represents a Shop with multiple counters.
 * 
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */

public class Shop {

    private final String shopName;

    private ShopCounter[] counters;
    private Queue queue;

    /**
     * Constructor for a Shop
     * 
     * @param shopName         name of the shop.
     * @param numberOfCounters number of counters the shop will have.
     */
    public Shop(String shopName, int numberOfCounters, int queueLength) {

        this.shopName = shopName;

        /**
         * Generate counters and designate a staff to each counter.
         */
        counters = new ShopCounter[numberOfCounters];
        for (int i = 0; i < numberOfCounters; i++) {
            counters[i] = new ShopCounter(new CounterStaff(Integer.toString((i)), "Staff " + (i + 1)), i, 1);
        }

        queue = new Queue(queueLength);
    }

    /**
     * @return an array of ShopCounters that exists in a Shop.
     */
    public ShopCounter[] getCounters() {
        return counters;
    }

    /**
     * @return an available counter. Returns null if there are no available counter
     */
    public ShopCounter getAvailableCounter() {
        ShopCounter[] counters = getCounters();
        ShopCounter counter = null;
        for (int i = 0; i < counters.length; i += 1) {
            if (counters[i].isAvailable()) {
                counter = counters[i];
                counters[i].acceptCustomer();
                break;
            }
        }
        return counter;
    }

    /**
     * @return a String of the shop's name
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * @return a String representation of the customers inside the queue
     */
    public String getQueue() {
        return queue.toString();
    }

    /**
     * @return a bool, true if queue is full, false if queue have space
     */
    public boolean isQueueFull() {
        return queue.isFull();
    }

    /**
     * @return a customer from the queue
     */
    public Customer retrieveCustomerFromQueue() {
        return (Customer) queue.deq();
    }

    /**
     * Add customer to current queue if it is not full
     */
    public boolean addCustomerToQueue(Customer customer) {
        if (!queue.isFull()) {
            queue.enq(customer);
            return true;
        }
        return false;
    }
}
