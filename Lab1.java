/**
 * The main class for CS2030S Lab 1.
 *
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */
class Lab1 {
  public static void main(String[] args) {

    /**
     * 1. Accept user input
     * 2. Feed input to simulation
     * 3. Simulation will generate the necessary shops, counters, and customers based on user input
    */
    InputReader reader = new InputReader();
    Object[] inputs = reader.readUserInput();
    Simulation simulation = new ShopSimulation(inputs);

    /** 
     * Create a new simulator and run the simulation
     */
    new Simulator(simulation).run();
  }
}
