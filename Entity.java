
/**
 * Class that represents an entity which can be Customer or Counter Representitive, or more Entities in the future
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
     * @param id id of the entity.
     * @param name name of the entity.
     * @param entityType 0: service counter personnel, 1: customer
     */
    public Entity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * @return name of an entity.
     */
    public String getName() {
        return name;
    }

    /**
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
