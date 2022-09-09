/**
 * Class that represents a counter at a shop.
 * 
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */

public class ShopCounter implements Comparable<ShopCounter> {

  // Personnel at the counter
  private CounterStaff personnel;

  // Counter information
  private int counterNo = 0;
  private int queueLength = 0;

  // Availability. By default, each counter can only accept 1 customer, this can
  // be configured.
  private int maxCapacity = 1;
  private int currentCapacity = 0;

  // Queue availability.
  private Queue<Customer> counterQueue;

  /**
   * Shop counter that will be inserted into Shop for the simulation.
   * 
   * @param personnel   the staff who is designated to this counter
   * @param counterNo   the counter's running number (0, 1, 2, ...).
   * @param maxCapacity maximum capacity (number of customers) that the counter
   *                    can serve.
   * @param queueLength maximum length of the queue infront of the counter.
   */
  public ShopCounter(CounterStaff personnel, int counterNo, int maxCapacity, int queueLength) {
    this.personnel = personnel;
    this.counterNo = counterNo;
    this.maxCapacity = maxCapacity;
    this.queueLength = queueLength;

    this.counterQueue = new Queue<Customer>(queueLength);
  }

  // If counter can serve customer
  public boolean isAvailable() {
    return this.currentCapacity < this.maxCapacity;
  }

  // If counter queue is full
  public boolean isCounterQueueFull() {
    return this.counterQueue.isFull();
  }

  public boolean canQueueAtCounter() {
    return this.queueLength != 0;
  }

  // Retrieve customer from counter queue
  public Customer retrieveCustomerFromCounterQueue() {
    return (Customer) this.counterQueue.deq();
  }

  // Add customer to counter queue and return
  // whether the operation was successful or not.
  public boolean addCustomerToCounterQueue(Customer customer) {
    return this.counterQueue.enq(customer);
  }

  /**
   * accept 1 additional customer.
   */
  public void acceptCustomer() {
    if (this.currentCapacity == this.maxCapacity) {
      return;
    }
    this.currentCapacity++;
  }

  /**
   * send off a customer and free up 1 slot.
   */
  public void sendOffCustomer() {
    if (this.currentCapacity == 0) {
      return;
    }
    this.currentCapacity--;
  }

  public int getCounterNo() {
    return this.counterNo;
  }

  public String getCounterQueue() {
    return this.counterQueue.toString();
  }

  // Override compareTo method to
  // return the shop that:
  // 1) has the smallest id (default/tie-breaker)
  // 2) or the counter with the shortest queue
  @Override
  public int compareTo(ShopCounter o) {

    // If this counter queue is full, forfeit
    if (this.counterQueue.isFull())
      return 1;
    // If this counter queue has more space, compete
    if (this.counterQueue.length() < o.counterQueue.length())
      return -1;

    // If two counters have same queue length
    if (this.counterQueue.length() == o.counterQueue.length()) {
      // Break ties with their id/counter no.
      if (this.counterNo < o.counterNo) {
        return -1;
      }
      if (this.counterNo > o.counterNo) {
        return 1;
      }
    }

    // Default case where they are the same
    return 0;
  }

  @Override
  public String toString() {
    return "ShopCounter [" + this.counterNo + "]";
  }
}
