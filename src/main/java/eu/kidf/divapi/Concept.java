package eu.kidf.divapi;

/**
 *
 * @author Gábor Bella
 */
public class Concept {
    
    /* an optional object that may contain an arbitrary representation of the concept */
    private Object container;
    /* a unique identifier for the concept */
    private String id;
    
    public Concept(Object c, String id) {
        this.container = c;
        this.id = id;
    }
    
    public Object getContainer() {
        return container;
    }

    public String getID() {
        return id;
    }
}
