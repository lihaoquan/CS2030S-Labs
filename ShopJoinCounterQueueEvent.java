/**
 * File to handle customers joining queue.
 *
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */
class ShopJoinCounterQueueEvent extends Event {

  // Customer associated with this event.
  private Customer customer;
  // Counter associated with this event
  private ShopCounter counter;

  /**
   * Event that will be triggered when adding
   * customer to the queue if all counters
   * are not available and there are
   * still spaces for queuing in the line.
   * 
   * @param time     the time when the event occured
   * @param customer the customer object that is passed
   *                 on to this event for referencing/passing on to the next
   *                 event.
   * @param counter  the counter queue that the customer will join.
   */
  public ShopJoinCounterQueueEvent(double time, Customer customer, ShopCounter counter) {
    super(time);
    this.customer = customer;
    this.counter = counter;
  }

  /**
   * Returns the string representation of the event,
   * depending on the type of event.
   *
   * @return A string representing the event.
   */
  @Override
  public String toString() {
    return super.toString() + ": " +
        this.customer.toString() +
        " joined counter queue (at S" +
        this.counter.getCounterNo() +
        " " +
        this.counter.getCounterQueue() +
        ")";
  }

  /**
   * The logic that the simulation should follow when simulating
   * this event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {

    // Add customer to counter queue.
    this.counter.addCustomerToCounterQueue(this.customer);

    return new Event[] {};
  }
}
