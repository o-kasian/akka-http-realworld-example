package realworld.com.articles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for TagEntity.
 */
@Repository
public interface TagRepository extends JpaRepository<TagEntity, Long> {
    
    /**
     * Find tags by their names
     * 
     * @param names collection of tag names
     * @return list of matching tags
     */
    List<TagEntity> findByNameIn(Collection<String> names);
}