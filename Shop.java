/**
 * Class that represents a Shop with multiple counters.
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */

public class Shop {

  private final String shopName;

  private ShopCounter[] counters;
  private Queue queue;

  /**
   * Constructor for a Shop.
   * 
   * @param shopName         name of the shop.
   * @param numberOfCounters number of counters the shop will have.
   * @param queueLength length of the queue allowed outside the shop.
   */
  public Shop(String shopName, int numberOfCounters, int queueLength) {

    this.shopName = shopName;

    //  Generate counters and designate a staff to each counter.
    counters = new ShopCounter[numberOfCounters];
    for (int i = 0; i < numberOfCounters; i++) {
      counters[i] = new ShopCounter(new CounterStaff(Integer.toString((i)), 
                                    "Staff " + (i + 1)), i, 1);
    }

    queue = new Queue(queueLength);
  }

  /**
   * Accessed by other classes to retrieve counters in the Shop.
   * @return an array of ShopCounters that exists in a Shop.
   */
  public ShopCounter[] getCounters() {
    return counters;
  }

  /**
   * Accessed by ShopArrivalEvent to get available counter
   * to pass on to subsequent events and to serve customers.
   * @return an available counter. 
   *         Returns null if there are no available counter.
   */
  public ShopCounter getAvailableCounter() {
    ShopCounter[] counters = getCounters();
    ShopCounter counter = null;
    for (int i = 0; i < counters.length; i += 1) {
      if (counters[i].isAvailable()) {
        counter = counters[i];
        break;
      }
    }
    return counter;
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
   * @param customer a customer class object.
   * @return whether adding customer to queue was successful.
   */
  public boolean addCustomerToQueue(Customer customer) {
    if (!queue.isFull()) {
      queue.enq(customer);
      return true;
    }
    return false;
  }
}
