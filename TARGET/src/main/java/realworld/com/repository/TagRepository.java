package realworld.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import realworld.com.model.Tag;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    Optional<Tag> findByName(String name);
    
    List<Tag> findByNameIn(List<String> names);
    
    boolean existsByName(String name);
}