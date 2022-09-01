
/**
 * Class that represents an entity which can be Customer or Counter Representitive, or more Entities in the future
 * @author Li Haoquan (Group 10A)
 * @version CS2030S AY22/23 Semester 1
 */

public class Entity {

    /** 
     * ID and name is set once on creation of an entity, and cannot be modified.
    */ 
    private final String id;
    private final String name;

    /**
     * Entity type
     * 0: service counter personnel
     * 1: customer
     */
    private final int entityType;

    /**
     * @param id id of the entity.
     * @param name name of the entity.
     * @param entityType 0: service counter personnel, 1: customer
     */
    public Entity(String id, String name, int entityType) {
        this.id = id;
        this.name = name;
        this.entityType = entityType;
    }

    /**
     * @return id of an entity.
     */
    public String getId() {
        return id;
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
    public String getEntityType() {
        if (this.entityType == 0)
            return "Service Counter Personnel";
        if (this.entityType == 1)
            return "Customer";

        return null;
    }

    /**
     * For debugging purposes, prints out a formatted message.
     */
    @Override
    public String toString() {
        return "Entity: [" + this.getId() + "]: is a " + getEntityType();
    }
}
