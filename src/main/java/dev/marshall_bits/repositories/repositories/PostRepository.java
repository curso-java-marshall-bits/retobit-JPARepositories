package dev.marshall_bits.repositories.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

import dev.marshall_bits.repositories.models.Post;
import dev.marshall_bits.repositories.models.PostCategory;

public interface PostRepository extends JpaRepository<Post, Long> {

    // 🔹 Métodos derivados
    Optional<Post> findByTitle(String title);

    List<Post> findByCategory(PostCategory category);
    // 🔹 JPQL

    // Posts con más de 100 vistas
    @Query("SELECT p FROM Post p WHERE p.viewCount > 100")
    List<Post> findPostsWithMoreThan100Views();

    // Ordenados por fecha (más recientes primero)
    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllByCreatedAt();

    // Buscar por palabra en el título
    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Post> findByTitleContaining(@Param("keyword") String keyword);
}