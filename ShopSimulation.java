import java.util.Scanner;

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
   * Takes in a scanner and passes it to InputReader 
   * to format the inputs and initialize events based 
   * on set input.
   * @param sc An input scanner given by LabN.java file where N is lab number.
   */
  public ShopSimulation(Scanner sc) {

    /*
     * Get user input and create a shop based on it.
     */
    Object[] inputs = InputReader.readUserInput(sc);
    shop = new Shop("Default Shop", (int) inputs[1], (int) inputs[2]);
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
