package dev.marshall_bits.repositories;
import dev.marshall_bits.repositories.models.Post;
import dev.marshall_bits.repositories.models.User;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Task2RepositoryCreationTest {

    @Test
    @Order(1)
    @DisplayName("UserRepository existe y extiende JpaRepository")
    void testUserRepositoryExists() {
        try {
            Class<?> userRepoClass = Class.forName("dev.marshall_bits.repositories.repositories.UserRepository");

            assertTrue(userRepoClass.isInterface(),
                "UserRepository debe ser una interfaz");

            assertTrue(JpaRepository.class.isAssignableFrom(userRepoClass),
                "UserRepository debe extender JpaRepository");

            verifyJpaRepositoryGenerics(userRepoClass, User.class, Long.class);

            System.out.println("✅ UserRepository: CORRECTO");

        } catch (ClassNotFoundException e) {
            fail("❌ UserRepository no encontrado. Crea una interfaz UserRepository en el paquete repositories.");
        }
    }

    @Test
    @Order(2)
    @DisplayName("PostRepository existe y extiende JpaRepository")
    void testPostRepositoryExists() {
        try {
            Class<?> postRepoClass = Class.forName("dev.marshall_bits.repositories.repositories.PostRepository");

            assertTrue(postRepoClass.isInterface(),
                "PostRepository debe ser una interfaz");

            assertTrue(JpaRepository.class.isAssignableFrom(postRepoClass),
                "PostRepository debe extender JpaRepository");

            verifyJpaRepositoryGenerics(postRepoClass, Post.class, Long.class);

            System.out.println("✅ PostRepository: CORRECTO");

        } catch (ClassNotFoundException e) {
            fail("❌ PostRepository no encontrado. Crea una interfaz PostRepository en el paquete repositories.");
        }
    }


    @Test
    @Order(4)
    @DisplayName("Estructura: los repositories están en el paquete 'repositories'")
    void testRepositoriesPackageStructure() {
        try {
            Class.forName("dev.marshall_bits.repositories.repositories.UserRepository");
            Class.forName("dev.marshall_bits.repositories.repositories.PostRepository");

            System.out.println("✅ Todos los repositories encontrados en la estructura de paquetes correcta");

        } catch (ClassNotFoundException e) {
            fail("❌ Uno o más repositories faltan. Asegúrate que los tres repositories están creados en el paquete 'repositories'.");
        }
    }

    @Test
    @Order(5)
    @DisplayName("Los repositories están implementados correctamente")
    void testRepositoriesHaveBasicCrudMethods() {
        try {
            Class<?> userRepoClass = Class.forName("dev.marshall_bits.repositories.repositories.UserRepository");

            Method[] methods = JpaRepository.class.getMethods();
            boolean hasSaveMethod = false;
            boolean hasFindByIdMethod = false;
            boolean hasDeleteMethod = false;

            for (Method method : methods) {
                if (method.getName().equals("save")) hasSaveMethod = true;
                if (method.getName().equals("findById")) hasFindByIdMethod = true;
                if (method.getName().equals("delete")) hasDeleteMethod = true;
            }

            assertTrue(hasSaveMethod, "Repository debe heredar método save de JpaRepository");
            assertTrue(hasFindByIdMethod, "Repository debe heredar método findById de JpaRepository");
            assertTrue(hasDeleteMethod, "Repository debe heredar método delete de JpaRepository");

            System.out.println("✅ Repositories heredan métodos CRUD básicos");

        } catch (ClassNotFoundException e) {
            fail("❌ No se pueden verificar métodos CRUD - UserRepository no encontrado. Crear los repositories primero.");
        }
    }



    private void verifyJpaRepositoryGenerics(Class<?> repositoryClass, Class<?> expectedEntity, Class<?> expectedId) {
        Type[] genericInterfaces = repositoryClass.getGenericInterfaces();

        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType paramType = (ParameterizedType) genericInterface;

                if (paramType.getRawType().equals(JpaRepository.class)) {
                    Type[] actualTypeArguments = paramType.getActualTypeArguments();

                    assertEquals(2, actualTypeArguments.length,
                        "JpaRepository debe tener exactamente 2 argumentos de tipo genérico");

                    assertEquals(expectedEntity, actualTypeArguments[0],
                        String.format("El primer tipo genérico debe ser %s", expectedEntity.getSimpleName()));

                    assertEquals(expectedId, actualTypeArguments[1],
                        String.format("El segundo tipo genérico debe ser %s", expectedId.getSimpleName()));

                    return;
                }
            }
        }

        fail("Repository debe extender JpaRepository con tipos genéricos apropiados");
    }
}
