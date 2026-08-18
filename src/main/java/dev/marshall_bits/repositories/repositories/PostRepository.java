package dev.marshall_bits.repositories.repositories;

import dev.marshall_bits.repositories.models.Post;
import dev.marshall_bits.repositories.models.PostCategory;
import dev.marshall_bits.repositories.models.User;
import org.springframework.boot.autoconfigure.graphql.ConditionalOnGraphQlSchema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static javax.swing.text.html.HTML.Tag.SELECT;

public interface PostRepository extends JpaRepository<Post, Long> {

    // Metodo para buscar una publicación por su título.
    Post findByTitle(String title);

    // Metodo para buscar publicaciones por categoría, devuelve una lista de posts. *Ten en cuenta que la categoría es un ENUM.
    List<Post> findByCategory(PostCategory content);

    @Query("SELECT p FROM Post p WHERE p.viewCount > 100")
    List<Post> findPostsWithMoreThan100Views();

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllByCreatedAt();

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword%") // % permite que la palabra clave aparezca en cualquier parte del título.
    List<Post> findByTitleContaining(@Param("keyword") String keyword); //@Param permite que la query sepa qué valor recibido por el metodo debe utilizar.
}
