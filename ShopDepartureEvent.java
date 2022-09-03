/**
 * Class to handle departure event.
 * 
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */
class ShopDepartureEvent extends Event {

  // Customer associated with this event.
  private Customer customer;

  /**
   * Constructor for a shop event.
   *
   * @param time     The time this event occurs.
   * @param customer The customer associated with this
   *                 event.
   */
  public ShopDepartureEvent(double time, Customer customer) {
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
    return super.toString() + String.format(": %s departed", this.customer.toString());
  }

  /**
   * The logic that the simulation should follow when simulating
   * this event.
   *
   * @return An array of new events to be simulated.
   */
  @Override
  public Event[] simulate() {

    // Find the first available counter.
    ShopCounter counter = ShopSimulation.shop.getAvailableCounter();

    if (counter != null) {
      Customer customerInQueue = ShopSimulation.shop.retrieveCustomerFromQueue();
      if (customerInQueue != null) {
        counter.acceptCustomer();
        return new Event[] {
          new ShopServiceBeginEvent(this.getTime(), customerInQueue, counter)
        };
      }
    }

    return new Event[] {};
  }
}
