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
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Transactional
public class Task3FindByMethodsTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private User testUser;
    private Post testPost;

    @BeforeEach
    void setUp() {
        postRepository.deleteAll();
        userRepository.deleteAll();

        testUser = new User("testuser", "test@example.com", "password123");
        testUser = userRepository.save(testUser);

        testPost = new Post("Spring Boot Tutorial", "Learn Spring Boot basics", testUser);
        testPost = postRepository.save(testPost);
    }

    @Test
    @Order(1)
    @DisplayName("UserRepository: existe un método para buscar por userName")
    void testFindByUsernameMethodExists() {
        try {
            Method findByUsernameMethod = UserRepository.class.getMethod("findByUsername", String.class);

            assertNotNull(findByUsernameMethod, "El método para buscar por nombre de usuario debe existir en UserRepository");

            assertEquals("findByUsername", findByUsernameMethod.getName());
            assertTrue(findByUsernameMethod.getReturnType().equals(Optional.class) ||
                      findByUsernameMethod.getReturnType().equals(User.class),
                      "El método debe retornar Optional<User> o User");

            System.out.println("✅ Método para buscar por nombre de usuario encontrado");

        } catch (NoSuchMethodException e) {
            fail("❌ Método para buscar por nombre de usuario no encontrado en UserRepository. " +
                 "Implementa un método que tome un String como parámetro y retorne Optional<User> o User.");
        }
    }

    @Test
    @Order(2)
    @DisplayName("UserRepository: Existe un método para buscar por email")
    void testFindByEmailMethodExists() {
        try {
            Method findByEmailMethod = UserRepository.class.getMethod("findByEmail", String.class);

            assertNotNull(findByEmailMethod, "El método para buscar por email debe existir en UserRepository");

            assertEquals("findByEmail", findByEmailMethod.getName());
            assertTrue(findByEmailMethod.getReturnType().equals(Optional.class) ||
                      findByEmailMethod.getReturnType().equals(User.class),
                      "El método debe retornar Optional<User> o User");

            System.out.println("✅ Método para buscar por email encontrado");

        } catch (NoSuchMethodException e) {
            fail("❌ Método para buscar por email no encontrado en UserRepository. " +
                 "Implementa un método que tome un String como parámetro y retorne Optional<User> o User.");
        }
    }

    @Test
    @Order(3)
    @DisplayName("PostRepository: Existe un método para buscar por título")
    void testFindByTitleMethodExists() {
        try {
            Method findByTitleMethod = PostRepository.class.getMethod("findByTitle", String.class);

            assertNotNull(findByTitleMethod, "El método para buscar por título debe existir en PostRepository");

            assertEquals("findByTitle", findByTitleMethod.getName());
            assertTrue(findByTitleMethod.getReturnType().equals(Optional.class) ||
                      findByTitleMethod.getReturnType().equals(Post.class),
                      "El método debe retornar Optional<Post> o Post");

            System.out.println("✅ Método para buscar por título encontrado");

        } catch (NoSuchMethodException e) {
            fail("❌ Método para buscar por título no encontrado en PostRepository. " +
                 "Implementa un método que tome un String como parámetro y retorne Optional<Post> o Post.");
        }
    }

    @Test
    @Order(4)
    @DisplayName("PostRepository: Existe un método para buscar por categoría")
    void testFindByCategoryMethodExists() {
        try {
            Method findByCategoryMethod = PostRepository.class.getMethod("findByCategory", PostCategory.class);

            assertNotNull(findByCategoryMethod, "El método para buscar por categoría debe existir en PostRepository");

            assertEquals("findByCategory", findByCategoryMethod.getName());
            assertTrue(findByCategoryMethod.getReturnType().equals(List.class),
                      "El método debe retornar List<Post>");

            System.out.println("✅ Método para buscar por categoría encontrado");

        } catch (NoSuchMethodException e) {
            fail("❌ Método para buscar por categoría no encontrado en PostRepository. " +
                 "Implementa un método que tome un enum PostCategory como parámetro y retorne List<Post>.");
        }
    }

    @Test
    @Order(5)
    @DisplayName("UserRepository: Buscar por userName funciona correctamente")
    void testFindByUsernameFunctionality() {
        try {
            Method findByUsernameMethod = UserRepository.class.getMethod("findByUsername", String.class);
            Object result = findByUsernameMethod.invoke(userRepository, "testuser");

            User foundUser = null;
            if (result instanceof Optional) {
                Optional<User> optionalUser = (Optional<User>) result;
                assertTrue(optionalUser.isPresent(), "findByUsername debe encontrar el usuario existente");
                foundUser = optionalUser.get();
            } else if (result instanceof User) {
                foundUser = (User) result;
            } else {
                fail("findByUsername debe retornar Optional<User> o User");
            }

            assertNotNull(foundUser, "El usuario encontrado no debe ser null");
            assertEquals("testuser", foundUser.getUsername(), "El username debe coincidir");
            assertEquals("test@example.com", foundUser.getEmail(), "El email debe coincidir");

            Object resultNotFound = findByUsernameMethod.invoke(userRepository, "nonexistent");
            if (resultNotFound instanceof Optional) {
                Optional<User> optionalUser = (Optional<User>) resultNotFound;
                assertFalse(optionalUser.isPresent(), "findByUsername debe retornar Optional vacío para usuario inexistente");
            } else {
                assertNull(resultNotFound, "findByUsername debe retornar null para usuario inexistente");
            }

            System.out.println("✅ findByUsername funciona correctamente");

        } catch (Exception e) {
            fail("❌ Error al probar findByUsername: " + e.getMessage());
        }
    }

    @Test
    @Order(6)
    @DisplayName("UserRepository: Buscar por email funciona correctamente")
    void testFindByEmailFunctionality() {
        try {
            Method findByEmailMethod = UserRepository.class.getMethod("findByEmail", String.class);
            Object result = findByEmailMethod.invoke(userRepository, "test@example.com");

            User foundUser = null;
            if (result instanceof Optional) {
                Optional<User> optionalUser = (Optional<User>) result;
                assertTrue(optionalUser.isPresent(), "findByEmail debe encontrar el usuario existente");
                foundUser = optionalUser.get();
            } else if (result instanceof User) {
                foundUser = (User) result;
            } else {
                fail("findByEmail debe retornar Optional<User> o User");
            }

            assertNotNull(foundUser, "El usuario encontrado no debe ser null");
            assertEquals("test@example.com", foundUser.getEmail(), "El email debe coincidir");
            assertEquals("testuser", foundUser.getUsername(), "El username debe coincidir");

            Object resultNotFound = findByEmailMethod.invoke(userRepository, "nonexistent@example.com");
            if (resultNotFound instanceof Optional) {
                Optional<User> optionalUser = (Optional<User>) resultNotFound;
                assertFalse(optionalUser.isPresent(), "findByEmail debe retornar Optional vacío para email inexistente");
            } else {
                assertNull(resultNotFound, "findByEmail debe retornar null para email inexistente");
            }

            System.out.println("✅ findByEmail funciona correctamente");

        } catch (Exception e) {
            fail("❌ Error al probar findByEmail: " + e.getMessage());
        }
    }

    @Test
    @Order(7)
    @DisplayName("PostRepository: Buscar por título funciona correctamente")
    void testFindByTitleFunctionality() {
        try {
            Method findByTitleMethod = PostRepository.class.getMethod("findByTitle", String.class);
            Object result = findByTitleMethod.invoke(postRepository, "Spring Boot Tutorial");

            Post foundPost = null;
            if (result instanceof Optional) {
                Optional<Post> optionalPost = (Optional<Post>) result;
                assertTrue(optionalPost.isPresent(), "findByTitle debe encontrar el post existente");
                foundPost = optionalPost.get();
            } else if (result instanceof Post) {
                foundPost = (Post) result;
            } else {
                fail("findByTitle debe retornar Optional<Post> o Post");
            }

            assertNotNull(foundPost, "El post encontrado no debe ser null");
            assertEquals("Spring Boot Tutorial", foundPost.getTitle(), "El título debe coincidir");
            assertEquals("Learn Spring Boot basics", foundPost.getContent(), "El contenido debe coincidir");

            Object resultNotFound = findByTitleMethod.invoke(postRepository, "Nonexistent Post");
            if (resultNotFound instanceof Optional) {
                Optional<Post> optionalPost = (Optional<Post>) resultNotFound;
                assertFalse(optionalPost.isPresent(), "findByTitle debe retornar Optional vacío para título inexistente");
            } else {
                assertNull(resultNotFound, "findByTitle debe retornar null para título inexistente");
            }

            System.out.println("✅ findByTitle funciona correctamente");

        } catch (Exception e) {
            fail("❌ Error al probar findByTitle: " + e.getMessage());
        }
    }

    @Test
    @Order(8)
    @DisplayName("PostRepository: Buscar por categoría funciona correctamente")
    void testFindByCategoryFunctionality() {
        try {
            Post techPost = new Post("Spring Boot Guide", "Advanced Spring Boot", testUser, PostCategory.TECHNOLOGY);
            postRepository.save(techPost);

            Post tutorialPost = new Post("Java Basics", "Learn Java fundamentals", testUser, PostCategory.TUTORIAL);
            postRepository.save(tutorialPost);

            Post anotherTechPost = new Post("Microservices", "Microservices architecture", testUser, PostCategory.TECHNOLOGY);
            postRepository.save(anotherTechPost);

            Post postWithoutCategory = new Post("Random Post", "Random content", testUser);
            postRepository.save(postWithoutCategory);

            Method findByCategoryMethod = PostRepository.class.getMethod("findByCategory", PostCategory.class);

            Object result = findByCategoryMethod.invoke(postRepository, PostCategory.TECHNOLOGY);

            assertTrue(result instanceof List, "findByCategory debe retornar una List");
            List<Post> techPosts = (List<Post>) result;

            assertEquals(2, techPosts.size(), "Debe encontrar exactamente 2 posts con categoría TECHNOLOGY");

            boolean foundSpringPost = techPosts.stream().anyMatch(p -> "Spring Boot Guide".equals(p.getTitle()));
            boolean foundMicroservicesPost = techPosts.stream().anyMatch(p -> "Microservices".equals(p.getTitle()));

            assertTrue(foundSpringPost, "Debe encontrar el post 'Spring Boot Guide'");
            assertTrue(foundMicroservicesPost, "Debe encontrar el post 'Microservices'");

            Object resultTutorial = findByCategoryMethod.invoke(postRepository, PostCategory.TUTORIAL);
            List<Post> tutorialPosts = (List<Post>) resultTutorial;

            assertEquals(1, tutorialPosts.size(), "Debe encontrar exactamente 1 post con categoría TUTORIAL");
            assertEquals("Java Basics", tutorialPosts.get(0).getTitle(), "Debe encontrar el post 'Java Basics'");

            Object resultEmpty = findByCategoryMethod.invoke(postRepository, PostCategory.NEWS);
            List<Post> emptyPosts = (List<Post>) resultEmpty;
            assertTrue(emptyPosts.isEmpty(), "Debe retornar lista vacía para categoría sin posts");

            System.out.println("✅ findByCategory funciona correctamente");

        } catch (Exception e) {
            fail("❌ Error al probar findByCategory: " + e.getMessage());
        }
    }

}
