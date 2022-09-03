/**
 * Class that represents a counter at a shop.
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */

public class ShopCounter {

  // Personnel at the counter
  private CounterStaff personnel;

  // Counter information
  private int counterNo = 0;

  // Availability. By default, each counter can only accept 1 customer, this can be configured.
  private int maxCapacity = 1;
  private int currentCapacity = 0;

  /**
   * Shop counter that will be inserted into Shop for the simulation.
   * @param personnel the staff who is designated to this counter
   * @param counterNo the counter's running number (0, 1, 2, ...).
   * @param maxCapacity maximum capacity (number of customers) that the counter can serve.
   */ 
  public ShopCounter(CounterStaff personnel, int counterNo, int maxCapacity) {
    this.personnel = personnel;
    this.counterNo = counterNo;
    this.maxCapacity = maxCapacity;
  }

  public boolean isAvailable() {
    return currentCapacity < maxCapacity;
  }

  /**
   * accept 1 additional customer.
   */
  public void acceptCustomer() {
    if (currentCapacity == maxCapacity) {
      return;
    }
    currentCapacity++;
  }

  /**
   * send off a customer and free up 1 slot.
   */
  public void sendOffCustomer() {
    if (currentCapacity == 0) {
      return;
    }
    currentCapacity--;
  }

  public int getCounterNo() {
    return counterNo;
  }
}
