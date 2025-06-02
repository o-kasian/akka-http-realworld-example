package realworld.com.articles;

import java.util.Objects;

/**
 * Represents a tag in the system.
 */
public class TagV {
    private Long id;
    private String name;

    /**
     * Default constructor required by JPA
     */
    public TagV() {
    }

    /**
     * Constructor with all fields
     * 
     * @param id   the tag ID
     * @param name the tag name
     */
    public TagV(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Creates a new tag with the given name and a placeholder ID
     * 
     * @param tagName the name of the tag
     * @return a new TagV instance
     */
    public static TagV create(String tagName) {
        return new TagV(-1L, tagName);
    }

    /**
     * Gets the tag ID
     * 
     * @return the tag ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the tag ID
     * 
     * @param id the tag ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the tag name
     * 
     * @return the tag name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the tag name
     * 
     * @param name the tag name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagV tagV = (TagV) o;
        return Objects.equals(id, tagV.id) && Objects.equals(name, tagV.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TagV{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}