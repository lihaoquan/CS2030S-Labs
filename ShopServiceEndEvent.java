/**
 * Class to handle service end event.
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
   * @param time The time this event occurs.
   * @param customer The customer associated with this event.
   * @param counter The counter that is associated with this event.
   */
  public ShopServiceEndEvent(double time, Customer customer, ShopCounter counter) {
    super(time);
    this.customer = customer;
    this.counter = counter;
  }

  /**
   * Constructor for a shop event.
   *
   * @param time The time this event occurs.
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
      + String.format(": %s service done (by S%d)", 
          this.customer.toString(), 
          this.counter.getCounterNo());
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
    return new Event[] {
      new ShopDepartureEvent(this.getTime(), this.customer),
    };
  }
}
