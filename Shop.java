/**
 * Class that represents a Shop with multiple counters.
 * 
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */

public class Shop {

  private final String shopName;

  private Array<ShopCounter> counters;
  private Queue<Customer> queue;

  /**
   * Constructor for a Shop.
   * 
   * @param shopName           name of the shop.
   * @param numberOfCounters   number of counters the shop will have.
   * @param queueLength        length of the queue allowed outside the shop.
   * @param counterQueueLength length of queue allowed at each counter.
   */
  public Shop(String shopName, int numberOfCounters, int queueLength, int counterQueueLength) {

    this.shopName = shopName;

    // Generate counters and designate a staff to each counter.
    counters = new Array<ShopCounter>(numberOfCounters);
    for (int i = 0; i < numberOfCounters; i++) {
      counters.set(i, new ShopCounter(new CounterStaff(Integer.toString((i)),
          "Staff " + (i + 1)), i, 1, counterQueueLength));
    }

    queue = new Queue<Customer>(queueLength);
  }

  /**
   * Accessed by other classes to retrieve counters in the Shop.
   * 
   * @return an array of ShopCounters that exists in a Shop.
   */
  public Array<ShopCounter> getCounters() {
    return counters;
  }

  /**
   * Accessed by ShopArrivalEvent to get available counter
   * to pass on to subsequent events and to serve customers.
   * 
   * @return an available counter.
   *         Returns null if there are no available counter.
   */
  public ShopCounter getAvailableCounter() {
    Array<ShopCounter> counters = getCounters();
    ShopCounter counter = null;
    for (int i = 0; i < counters.size(); i += 1) {
      if (counters.get(i).isAvailable()) {
        counter = counters.get(i);
        break;
      }
    }

    return counter;
  }

  /**
   * If there are no counters available, check if
   * there are any counter queues that can be joined
   * by the customer.
   * 
   * @return a counter with available queue.
   */
  public ShopCounter getCounterWithAvailableQueue() {
    ShopCounter counter = counters.min();
    if (!counter.isCounterQueueFull()) {
      return counter;
    }
    return null;
  }

  public String getShopName() {
    return shopName;
  }

  public String getQueue() {
    return queue.toString();
  }

  public boolean isQueueFull() {
    return queue.isFull();
  }

  public Customer retrieveCustomerFromQueue() {
    return (Customer) queue.deq();
  }

  /**
   * Add customer to current queue if it is not full.
   * 
   * @param customer a customer class object.
   * @return whether adding customer to queue was successful.
   */
  public boolean addCustomerToQueue(Customer customer) {
    return queue.enq(customer);
  }
}
