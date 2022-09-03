/**
 * Class that represents a staff at a particular counter.
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */

public class CounterStaff extends Entity {

  /**
   * Constructor for creating a staff for a counter. 
   * A staff is an Entity which will have an id, and name.
   * @param id unique id for each staff
   * @param name name of the staff
   * 
   */
  public CounterStaff(String id, String name) {
    super(id, name);
  }

  @Override
  public String getEntityType() {
    return "Counter Staff";
  }
}
