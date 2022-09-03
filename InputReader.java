import java.util.Scanner;

/**
 * A file to handle input reading from users of the program.
 *
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */

public class InputReader {

  /**
   * This function reads multiple user input and returns it as an object.

   * @param sc Scanner ref for reading user input. 
   * @return an object array with user input 
   *         of different types. Method is static so 
   *         that it can be re-used as a utility 
   *         function.
   */
  public static Object[] readUserInput(Scanner sc) { 

    Event[] initEvents = new Event[sc.nextInt()];
    int numOfCounters = sc.nextInt();
    int lengthOfQueue = sc.nextInt();

    int id = 0;
    while (sc.hasNextDouble()) {
      double arrivalTime = sc.nextDouble();
      double serviceTime = sc.nextDouble();

      Customer customer = new Customer(Integer.toString(id), 
          "Customer " + id, 
          arrivalTime, 
          serviceTime);

      initEvents[id] = new ShopArrivalEvent(
          arrivalTime, customer);
      id += 1;
    }

    sc.close();

    return new Object[] { initEvents, numOfCounters, lengthOfQueue };
  }

}
