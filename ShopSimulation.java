/**
 * This class implements a shop simulation.
 *
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */
class ShopSimulation extends Simulation {

  private Event[] initEvents;
  public static Shop shop;

  /**
   * @param inputs An array of input received from InputReader class.
   */
  public ShopSimulation(Object[] inputs) {
    shop = new Shop("Default Shop", (int) inputs[1]);
    initEvents = (Event[]) inputs[0];
  }

  /**
   * Retrieve an array of events to populate the
   * simulator with.
   *
   * @return An array of events for the simulator.
   */
  @Override
  public Event[] getInitialEvents() {
    return initEvents;
  }
}
