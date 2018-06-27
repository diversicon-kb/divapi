package eu.kidf.divapi;

/**
 *
 * @author Gábor Bella
 */
public class Concept {
    
    private Object resource;
    private String id;
    
    public Concept(Object r, String id) {
        this.resource = r;
        this.id = id;
    }
    
    public Object getResource() {
        return resource;
    }

    public Object getID() {
        return id;
    }
}
