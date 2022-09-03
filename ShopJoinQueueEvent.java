/**
 * File to handle customers joining queue.
 *
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */
class ShopJoinQueueEvent extends Event {

  // Customer associated with this event.
  private Customer customer;

  /**
   * Event that will be triggered when adding 
   * customer to the queue if all counters
   * are not available and there are
   * still spaces for queuing in the line.
   * @param time the time when the event occured
   * @param customer the customer object that is passed
   *        on to this event for referencing/passing on to the next event.
   */
  public ShopJoinQueueEvent(double time, Customer customer) {
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
    return super.toString() + ": " + 
      this.customer.toString() + 
      " joined queue " + 
      ShopSimulation.shop.getQueue(); 
  }

  /**
   * The logic that the simulation should follow when simulating
   * this event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {

    ShopSimulation.shop.addCustomerToQueue(this.customer);

    return new Event[] {};
  }
}
