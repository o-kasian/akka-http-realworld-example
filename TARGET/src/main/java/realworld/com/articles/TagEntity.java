package realworld.com.articles;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * JPA Entity for tags table.
 */
@Entity
@Table(name = "tags")
public class TagEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * Default constructor required by JPA
     */
    public TagEntity() {
    }

    /**
     * Constructor with name
     * 
     * @param name the tag name
     */
    public TagEntity(String name) {
        this.name = name;
    }

    /**
     * Constructor with all fields
     * 
     * @param id   the tag ID
     * @param name the tag name
     */
    public TagEntity(Long id, String name) {
        this.id = id;
        this.name = name;
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

    /**
     * Converts this entity to a TagV model
     * 
     * @return a TagV instance
     */
    public TagV toTagV() {
        return new TagV(id, name);
    }

    /**
     * Creates a TagEntity from a TagV model
     * 
     * @param tagV the TagV model
     * @return a TagEntity instance
     */
    public static TagEntity fromTagV(TagV tagV) {
        return new TagEntity(tagV.getId(), tagV.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagEntity tagEntity = (TagEntity) o;
        return Objects.equals(id, tagEntity.id) && Objects.equals(name, tagEntity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TagEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}