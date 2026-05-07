package dev.marshall_bits.repositories.repositories;

import dev.marshall_bits.repositories.models.Post;
import dev.marshall_bits.repositories.models.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);
    List<Post> findByCategory(PostCategory category);
    @Query("SELECT p FROM Post p WHERE p.viewCount > 100")
    List<Post> findPostsWithMoreThan100Views();
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllByCreatedAt();
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%")
    List<Post> findByTitleContaining(String keyword);

}
