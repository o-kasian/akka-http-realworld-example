package realworld.com.articles.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags", uniqueConstraints = {
    @UniqueConstraint(name = "tag_name_unique", columnNames = "name")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "tagId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticleTag> articleTags = new ArrayList<>();
    
    /**
     * Factory method to create a new tag with a given name
     * @param tagName The name of the tag
     * @return A new Tag instance with id set to -1 (to be assigned by the database)
     */
    public static TagEntity create(String tagName) {
        return new TagEntity(-1L, tagName, new ArrayList<>());
    }
    
    // Constructor matching the Scala case class
    public TagEntity(Long id, String name) {
        this.id = id;
        this.name = name;
        this.articleTags = new ArrayList<>();
    }
}