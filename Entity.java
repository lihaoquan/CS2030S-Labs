
/**
 * Class that represents an entity which can be Customer 
 * or Counter Representitive, or more Entities in the future.
 *
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */

public abstract class Entity {

  /** 
   * ID and name is set once on creation of an entity, and cannot be modified.
   */ 
  protected final String id;
  private final String name;

  /**
   * Constructor for an entity, will have id/name for
   * debugging purposes or referencing purposes in the
   * future.
   * @param id id of the entity.
   * @param name name of the entity.
   */
  public Entity(String id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Name for debugging purposes.
   * @return name of an entity.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Entity type will be overriden and determined
   * child class.
   * @return the entity type of an entity as a string
   */
  public abstract String getEntityType();

  /**
   * For debugging purposes, prints out a formatted message.
   */
  @Override
  public String toString() {
    return "Entity: [" + this.id + "]: is a " + getEntityType();
  }
}
