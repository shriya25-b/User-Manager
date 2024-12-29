package com.mycompany;

import com.mycompany.user.User;
import com.mycompany.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {
    @Autowired private UserRepository repo;

    @Test
    public void testAddNew(){
        User user = new User();
        user.setEmail("test2@test.com");
        user.setPassword("pass2");
        user.setFirstname("Pranav");
        user.setLastname("Singh");

       User saveduser= repo.save(user);

        Assertions.assertThat(saveduser).isNotNull();
        Assertions.assertThat(saveduser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAll(){
       Iterable<User> users= repo.findAll();
       Assertions.assertThat(users).hasSizeGreaterThan(0);

        for (User user : users) {
            System.out.println(user);
            
        }

    }

    @Test
    public void testUpdate(){
        Integer userId=1;
        Optional<User> optionalUser = repo.findById(userId);
        User user = optionalUser.get();
        user.setPassword("Welcome");
        repo.save(user);
        User updateduser= repo.findById(userId).get();
        Assertions.assertThat(updateduser.getPassword()).isEqualTo("Welcome");

    }

    @Test
    public void testGet(){
        Integer userId=1;
        Optional<User> optionalUser = repo.findById(userId);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testDelete(){
    Integer userId=2;
    repo.deleteById(userId);

    Optional<User> optionalUser = repo.findById(userId);
    Assertions.assertThat(optionalUser).isNotPresent();

    }
}
