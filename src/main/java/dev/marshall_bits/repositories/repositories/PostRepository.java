package dev.marshall_bits.repositories.repositories;

import dev.marshall_bits.repositories.models.Post;
import dev.marshall_bits.repositories.models.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {

    Optional<Post> findByTitle(String title);

    List<Post> findByCategory(PostCategory category);

    //Metodo para obtener una lista de posts que tengan más de 100 vistas. Llámalo findPostsWithMoreThan100Views().
    @Query("SELECT p FROM Post p WHERE p.viewCount >100")
    List<Post> findPostsWithMoreThan100Views();

    //Metodo para obtener una lista de publicaciones ordenadas por fecha de creación, desde la más reciente a la más antigua. Llámalo findAllByCreatedAt().
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllByCreatedAt();

    //Metodo para obtener una lista de publicaciones que contengan una palabra clave en su título. Llámalo findByTitleContaining(String keyword).
    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% ")
    List<Post> findByTitleContaining(String keyword);
}
