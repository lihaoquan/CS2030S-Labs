/**
 * Class that represents a customer. 
 * This class will encapsulate most 
 * of customer's behaviour.
 *
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */

public class Customer extends Entity {

  // Information about when he arrive at 
  // the shop, as well as how long it will
  // take to service them.
  private double arrivalTime;
  private double serviceTime;

  /**
   * Constructor for creating a customer.
   * 
   * @param id          unique identifier of the customer
   * @param name        name of the customer
   * @param arrivalTime arrival time of the customeri
   * @param serviceTime the time it takes to service this particular customer
   */
  public Customer(String id, String name, double arrivalTime, double serviceTime) {
    super(id, name);
    this.arrivalTime = arrivalTime;
    this.serviceTime = serviceTime;
  }

  @Override
  public String getEntityType() {
    return "Customer";
  }

  @Override
  public String toString() {
    return String.format("C%s", this.id);
  }

  /**
   * For setting the service time required for a customer.
   * 
   * @param time of type double to specify how long it will take to service a
   *             customer.
   */
  public void setServiceTime(double time) {
    this.serviceTime = time;
  }

  /**
   * Service time needed for a particular customer.
   * @return service time required for this particular customer.
   */
  public double getServiceTime() {
    return this.serviceTime;
  }
}
