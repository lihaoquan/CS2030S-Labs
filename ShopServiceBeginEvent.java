/**
 * Class to handle the start of a service
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */
class ShopServiceBeginEvent extends Event {

	// Customer associated with this event.
	private Customer customer;

	// Counter associated with this event.
	private ShopCounter counter;

	/**
	 * Constructor for a shop event.
	 *
	 * @param time       The time this event occurs.
	 * @param customer   The customer associated with this event.
	 * @param counter 	 The counter that is associated with this event.
	 */
	public ShopServiceBeginEvent(double time, Customer customer, ShopCounter counter) {
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
		return super.toString() + String.format(": Customer %s service begin (by Counter %d)",
				this.customer.getId(), this.counter.getCounterNo());
	}

	/**
	 * The logic that the simulation should follow when simulating
	 * this event.
	 *
	 * @return An array of new events to be simulated.
	 */
	@Override
	public Event[] simulate() {
		// The current event is a service-begin event.
		// schedule a service-end event at the current time + service time.
		double endTime = this.getTime() + this.customer.getServiceTime();
		return new Event[] {
				new ShopServiceEndEvent(endTime, this.customer, this.counter)
		};
	}
}
