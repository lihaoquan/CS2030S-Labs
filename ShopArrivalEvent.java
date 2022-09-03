/**
 * File to handle customer arrival
 * 
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */
class ShopArrivalEvent extends Event {

    // Customer associated with this event.
    private Customer customer;

    /**
     * @param time     the time when the event occured
     * @param customer th customer object that is passed on to this event for
     *                 referencing/passing on to the next event.
     */
    public ShopArrivalEvent(double time, Customer customer) {
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
        return super.toString() + ": " + this.customer.toString() + " arrived " + ShopSimulation.shop.getQueue();
    }

    /**
     * The logic that the simulation should follow when simulating
     * this event.
     *
     * @return An array of new events to be simulated.
     */
    @Override
    public Event[] simulate() {

        // The current event is an arrival event.
        // Find the first available counter.
        ShopCounter counter = ShopSimulation.shop.getAvailableCounter();
        
        if (counter == null) {
            // If no such counter can be found, the customer
            // should depart.
            if (ShopSimulation.shop.isQueueFull()) {
                return new Event[] {
                        new ShopDepartureEvent(this.getTime(), this.customer)
                };
            } else {
                return new Event[] {
                        new ShopJoinQueueEvent(this.getTime(), this.customer)
                };
            }
        } else {
            // Else, the customer should go the the first
            // available counter and get served.
            return new Event[] {
                    new ShopServiceBeginEvent(this.getTime(), this.customer, counter)
            };
        }
    }
}
