/**
 * A file to handle input reading from users of the program.
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */

import java.util.Scanner;

public class InputReader {

    /**
     * @return an object array with user input of different types.
     */
    public Object[] readUserInput() {
        // Create a scanner to read from standard input.
        Scanner sc = new Scanner(System.in);

        Event[] initEvents = new Event[sc.nextInt()];
        int numOfCounters = sc.nextInt();

        int id = 0;
        while (sc.hasNextDouble()) {
            double arrivalTime = sc.nextDouble();
            double serviceTime = sc.nextDouble();
            Customer customer = new Customer(Integer.toString(id), "Customer " + id, arrivalTime, serviceTime);
            initEvents[id] = new ShopArrivalEvent(
                    arrivalTime, customer);
            id += 1;
        }

        sc.close();

        return new Object[] { initEvents, numOfCounters };
    }

}
