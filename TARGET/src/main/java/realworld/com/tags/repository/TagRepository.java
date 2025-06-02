package realworld.com.tags.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import realworld.com.tags.model.Tag;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    /**
     * Find a tag by its name
     */
    Optional<Tag> findByName(String name);
    
    /**
     * Find tags by a list of names
     */
    List<Tag> findByNameIn(List<String> names);
}