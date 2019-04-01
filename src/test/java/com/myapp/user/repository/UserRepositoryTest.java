package com.myapp.user.repository;

import com.myapp.user.entity.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.validation.*;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    // This validates the Hibernate Constraints
    private Validator validator;

    // SetUp the validator object
    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    /**
     * Check if the repository is empty at the beginning
     */
    @Test
    public void repo_should_find_no_customers_if_repository_is_empty() {
        Iterable<User> user = userRepository.findAll();
        assertThat(user).isEmpty();
    }

    /**
     * Check if a new user can be created
     */
    @Test
    public void repo_should_create_a_user() {
        User u = new User();
        u.setUserName("myUser");
        entityManager.persist(u);

        User user = userRepository.findAll().get(0);
        System.out.println(userRepository.findAll());
        Assert.assertThat( user, instanceOf(User.class) );
        assertEquals("myUser", user.getUserName());

    }

    /**
     * Check if a user can fetched using their name
     */
    @Test
    public void repo_should_find_a_user_by_name() {
        User u = new User();
        u.setUserName("myAppUser");
        entityManager.persist(u);

        User user = userRepository.findByUserName("myAppUser");
        assertEquals("myAppUser", user.getUserName());
    }

    /**
     * Check if a user can fetched using their email
     */
    @Test
    public void repo_should_find_a_user_by_email() {
        User u = new User();
        u.setEmail("user@myapp.com");
        entityManager.persist(u);

        User user = userRepository.findByEmail("user@myapp.com");
        assertEquals("user@myapp.com", user.getEmail());
    }

    /**
     * Validate that an exception would occur if email validation rule is not followed
     */
    @Test(expected = ValidationException.class)
    public void repo_should_cause_error_if_email_is_invalid(){
        User u = new User();
        u.setEmail("badEmail.com");
        validateBean(u);
    }

    /**
     * Simulates the behaviour of bean-validation e.g. @NotNull
     */
    private void validateBean(Object bean) throws AssertionError {
        Optional<ConstraintViolation<Object>> violation = validator.validate(bean).stream().findFirst();
        if (violation.isPresent()) {
            throw new ValidationException(violation.get().getMessage());
        }
    }

    /**
     * Validate that an exception would occur if email validation rule is not followed
     */
    @Test(expected = PersistenceException.class)
    public void repo_should_cause_error_if_user_name_is_duplicated(){
        User u = new User();
        u.setUserName("userSupreme");
        u.setEmail("user1@myapp.com");
        entityManager.persist(u);

        User duplicate = new User();
        duplicate.setUserName("userSupreme");
        duplicate.setEmail("user2@myapp.com");
        entityManager.persist(duplicate);
    }

    /**
     * Validate that an exception would occur if email validation rule is not followed
     */
    @Test(expected = PersistenceException.class)
    public void repo_should_cause_error_if_user_email_is_duplicated(){
        User u = new User();
        u.setUserName("userType1");
        u.setEmail("user@myapp.com");
        entityManager.persist(u);

        User duplicate = new User();
        duplicate.setUserName("userType2");
        duplicate.setEmail("user@myapp.com");
        entityManager.persist(duplicate);
    }
}