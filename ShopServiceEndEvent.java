/**
 * Class to handle service end event.
 * 
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */
class ShopServiceEndEvent extends Event {

  // Customer associated with this event.
  private Customer customer;

  // Counter associated with this event.
  private ShopCounter counter;

  /**
   * Constructor for a shop event.
   *
   * @param time     The time this event occurs.
   * @param customer The customer associated with this event.
   * @param counter  The counter that is associated with this event.
   */
  public ShopServiceEndEvent(double time, Customer customer, ShopCounter counter) {
    super(time);
    this.customer = customer;
    this.counter = counter;
  }

  /**
   * Constructor for a shop event.
   *
   * @param time     The time this event occurs.
   * @param customer The customer associated with this
   *                 event.
   */
  public ShopServiceEndEvent(double time, Customer customer) {
    super(time);
    this.customer = customer;
  }

  /**
   * Returns the string representation of the event,
   * depending on the type of event.
   *
   * @return A string representing the event.
   */
  @Override
  public String toString() {
    return super.toString()
        + String.format(": %s service done (by S%d %s)",
            this.customer.toString(),
            this.counter.getCounterNo(),
            this.counter.getCounterQueue());
  }

  /**
   * The logic that the simulation should follow when simulating
   * this event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {
    // The current event is a service-end event.
    // Mark the counter is available, then schedule
    // a departure event at the current time.
    this.counter.sendOffCustomer();

    Customer customerInShopQueue = ShopSimulation.shop.retrieveCustomerFromQueue();
    Customer customerInCounterQueue = this.counter.retrieveCustomerFromCounterQueue();

    // If there are customer in shop queue and customer in counter queue.
    if (customerInShopQueue != null && customerInCounterQueue != null) {
      return new Event[] {
          new ShopDepartureEvent(this.getTime(), this.customer),
          new ShopServiceBeginEvent(this.getTime(), customerInCounterQueue, counter),
          new ShopJoinCounterQueueEvent(this.getTime(), customerInShopQueue, counter)
      };
    }

    // If there are no customer in shop queue, but have customer in counter queue.
    if (customerInShopQueue == null && customerInCounterQueue != null) {
      return new Event[] {
          new ShopDepartureEvent(this.getTime(), this.customer),
          new ShopServiceBeginEvent(this.getTime(), customerInCounterQueue, counter)
      };
    }

    // If there are no customer in counter queue, but have customer in shop queue
    if (customerInShopQueue != null && customerInCounterQueue == null) {
      return new Event[] {
          new ShopDepartureEvent(this.getTime(), this.customer),
          new ShopJoinCounterQueueEvent(this.getTime(), customerInShopQueue, counter),
      };
    }

    // If there are no customer in both counter queue and shop queue
    if (customerInShopQueue == null && customerInCounterQueue == null) {
      return new Event[] {
        new ShopDepartureEvent(this.getTime(), this.customer)
      };
    }

    return new Event[] {};
  }
}
