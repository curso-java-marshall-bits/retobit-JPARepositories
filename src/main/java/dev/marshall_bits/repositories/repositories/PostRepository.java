
package dev.marshall_bits.repositories.repositories;

import dev.marshall_bits.repositories.models.Post;
import dev.marshall_bits.repositories.models.PostCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PostRepository extends JpaRepository<Post,Long> {
    
    //FindByTitle
    List<Post> findByTitle(String Title);
    
    //FindByCategory
    List<Post> findByCategory(PostCategory category);
    
    //JPQL
    @Query("SELECT p FROM Post p WHERE p.viewCount > 100")
    List<Post> findPostsWithMoreThan100Views();
    
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllByCreatedAt();
    
    @Query("SELECT p FROM Post p WHERE p.title LIKE CONCAT('%', :keyword, '%')")
    List<Post> findByTitleContaining(@Param("keyword") String keyword);
}
