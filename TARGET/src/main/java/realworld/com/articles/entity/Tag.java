package realworld.com.articles.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags", uniqueConstraints = {
    @UniqueConstraint(name = "tag_name_unique", columnNames = "name")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    /**
     * Factory method to create a new tag with a given name
     * @param tagName The name of the tag
     * @return A new Tag instance with id set to -1 (to be assigned by the database)
     */
    public static Tag create(String tagName) {
        return new Tag(-1L, tagName);
    }
}