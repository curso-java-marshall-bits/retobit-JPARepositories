package dev.marshall_bits.repositories;
import dev.marshall_bits.repositories.models.Post;
import dev.marshall_bits.repositories.models.PostCategory;
import dev.marshall_bits.repositories.models.User;
import dev.marshall_bits.repositories.repositories.PostRepository;
import dev.marshall_bits.repositories.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Transactional
public class Task4JPQLQueriesTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private User testUser;
    private User anotherUser;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();

        testUser = new User("johndoe", "john@example.com", "password123");
        testUser = userRepository.save(testUser);

        anotherUser = new User("janedoe", "jane@example.com", "password456");
        anotherUser = userRepository.save(anotherUser);

        Post post1 = new Post("Advanced Spring Boot", "Deep dive into Spring Boot features", testUser, PostCategory.TECHNOLOGY);
        post1.setViewCount(150);
        postRepository.save(post1);

        Post post2 = new Post("Java Fundamentals", "Learn the basics of Java programming", testUser, PostCategory.TUTORIAL);
        post2.setViewCount(75);
        postRepository.save(post2);

        Post post3 = new Post("Microservices Architecture", "Building scalable microservices", anotherUser, PostCategory.TECHNOLOGY);
        post3.setViewCount(200);
        postRepository.save(post3);

        Post post4 = new Post("Database Design", "Best practices for database design", anotherUser, PostCategory.GUIDE);
        post4.setViewCount(50);
        postRepository.save(post4);
    }


    @Test
    @Order(1)
    @DisplayName("PostRepository: Existe un método para obtener posts con más de 100 vistas")
    void testFindPostsWithMoreThan100ViewsMethodExists() {
        try {
            Method method = PostRepository.class.getMethod("findPostsWithMoreThan100Views");

            assertNotNull(method, "El método para obtener posts con más de 100 vistas debe existir en PostRepository");
            assertEquals(List.class, method.getReturnType(), "El método debe retornar List<Post>");

            System.out.println("✅ Método para obtener posts con más de 100 vistas encontrado");

        } catch (NoSuchMethodException e) {
            fail("❌ Método findPostsWithMoreThan100Views no encontrado en PostRepository. " +
                 "Implementa un método que no tome parámetros y retorne List<Post> usando JPQL.");
        }
    }

    @Test
    @Order(2)
    @DisplayName("PostRepository: Buscar posts con más de 100 vistas funciona correctamente")
    void testFindPostsWithMoreThan100ViewsFunctionality() {
        try {
            Method method = PostRepository.class.getMethod("findPostsWithMoreThan100Views");
            Object result = method.invoke(postRepository);

            assertInstanceOf(List.class, result, "Debe retornar una List");
            List<Post> posts = (List<Post>) result;

            assertEquals(2, posts.size(), "Debe encontrar exactamente 2 posts con más de 100 vistas");

            for (Post post : posts) {
                assertTrue(post.getViewCount() > 100,
                          "Todos los posts retornados deben tener más de 100 vistas");
            }

            assertTrue(posts.stream().anyMatch(p -> p.getTitle().equals("Advanced Spring Boot") && p.getViewCount() == 150),
                      "Debe incluir 'Advanced Spring Boot' con 150 vistas");
            assertTrue(posts.stream().anyMatch(p -> p.getTitle().equals("Microservices Architecture") && p.getViewCount() == 200),
                      "Debe incluir 'Microservices Architecture' con 200 vistas");

            System.out.println("✅ findPostsWithMoreThan100Views funciona correctamente");

        } catch (Exception e) {
            fail("❌ Error al probar findPostsWithMoreThan100Views: " + e.getMessage());
        }
    }

    @Test
    @Order(3)
    @DisplayName("PostRepository: Existe un método para obtener todos los posts ordenados por fecha de creación descendente")
    void testFindAllOrderByCreatedDateDescMethodExists() {
        try {
            Method method = PostRepository.class.getMethod("findAllByCreatedAt");

            assertNotNull(method, "El método para obtener posts ordenados por fecha debe existir en PostRepository");
            assertEquals(List.class, method.getReturnType(), "El método debe retornar List<Post>");

            System.out.println("✅ Método para obtener posts ordenados por fecha encontrado");

        } catch (NoSuchMethodException e) {
            fail("❌ Método para obtener posts ordenados por fecha no encontrado en PostRepository. " +
                 "Implementa un método que no tome parámetros y retorne List<Post> usando JPQL.");
        }
    }

    @Test
    @Order(4)
    @DisplayName("PostRepository: Posts por fecha de creación ordenados descendente funciona correctamente")
    void testFindAllOrderByCreatedDateDescFunctionality() {
        try {
            Thread.sleep(10);

            Post recentPost = new Post("Latest Tutorial", "The most recent tutorial", testUser, PostCategory.TUTORIAL);
            postRepository.save(recentPost);

            Method method = PostRepository.class.getMethod("findAllByCreatedAt");
            Object result = method.invoke(postRepository);

            assertInstanceOf(List.class, result, "Debe retornar una List");
            List<Post> posts = (List<Post>) result;

            assertEquals(5, posts.size(), "Debe encontrar todos los 5 posts creados en setUp + 1 adicional");

            for (int i = 0; i < posts.size() - 1; i++) {
                assertTrue(
                    posts.get(i).getCreatedAt().isAfter(posts.get(i + 1).getCreatedAt()) ||
                    posts.get(i).getCreatedAt().isEqual(posts.get(i + 1).getCreatedAt()),
                    "Los posts deben estar ordenados por fecha de creación descendente"
                );
            }

            assertEquals("Latest Tutorial", posts.get(0).getTitle(),
                        "El primer post debe ser el más reciente");

            System.out.println("✅ findAllByCreatedAt funciona correctamente");

        } catch (Exception e) {
            fail("❌ Error al probar findAllByCreatedAt: " + e.getMessage());
        }
    }

    @Test
    @Order(5)
    @DisplayName("PostRepository: Existe un método para buscar posts por palabra clave en el título")
    void testFindByTitleContainingMethodExists() {
        try {
            Method method = PostRepository.class.getMethod("findByTitleContaining", String.class);

            assertNotNull(method, "El método para buscar posts por título debe existir en PostRepository");
            assertEquals(List.class, method.getReturnType(), "El método debe retornar List<Post>");

            System.out.println("✅ Método para buscar posts por título encontrado");

        } catch (NoSuchMethodException e) {
            fail("❌ Método findByTitleContaining no encontrado en PostRepository. " +
                 "Implementa un método que tome un String como parámetro y retorne List<Post> usando JPQL.");
        }
    }

    @Test
    @Order(6)
    @DisplayName("PostRepository: Buscar posts por palabra clave en el título funciona correctamente")
    void testFindByTitleContainingFunctionality() {
        try {
            Method method = PostRepository.class.getMethod("findByTitleContaining", String.class);

            Object result = method.invoke(postRepository, "Java");
            assertInstanceOf(List.class, result, "Debe retornar una List");
            List<Post> postsJava = (List<Post>) result;

            assertEquals(1, postsJava.size(), "Debe encontrar exactamente 1 post que contenga 'Java'");
            assertEquals("Java Fundamentals", postsJava.get(0).getTitle(),
                        "Debe encontrar 'Java Fundamentals'");

            result = method.invoke(postRepository, "Spring");
            List<Post> postsSpring = (List<Post>) result;

            assertEquals(1, postsSpring.size(), "Debe encontrar exactamente 1 post que contenga 'Spring'");
            assertEquals("Advanced Spring Boot", postsSpring.get(0).getTitle(),
                        "Debe encontrar 'Advanced Spring Boot'");

            result = method.invoke(postRepository, "Architecture");
            List<Post> postsArchitecture = (List<Post>) result;

            assertEquals(1, postsArchitecture.size(), "Debe encontrar exactamente 1 post que contenga 'Architecture'");
            assertEquals("Microservices Architecture", postsArchitecture.getFirst().getTitle(),
                        "Debe encontrar 'Microservices Architecture'");

            result = method.invoke(postRepository, "Python");
            List<Post> postsPython = (List<Post>) result;

            assertEquals(0, postsPython.size(), "No debe encontrar posts que contengan 'Python'");

            System.out.println("✅ findByTitleContaining funciona correctamente");

        } catch (Exception e) {
            fail("❌ Error al probar findByTitleContaining: " + e.getMessage());
        }
    }
}
