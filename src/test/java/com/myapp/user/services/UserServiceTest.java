package com.myapp.user.services;

import com.myapp.user.entity.User;
import com.myapp.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserRepository userRepository;

    User userMock;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        userMock = new User("1","userName",
                "user@tc.com","passTC",
                new Date(), new Date());
    }

    @Test
    public void service_should_find_a_user_by_id(){
        Optional<User> returnCacheValue = Optional.of(userMock);
        when(userRepository.findById("1")).thenReturn(returnCacheValue);

        ResponseEntity<User> user = userService.getUserById("1");
        assertEquals("userName", user.getBody().getUserName());
    }

    @Test
    public void service_should_find_all_the_users(){
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
        userList.add(new User());
        when(userRepository.findAll()).thenReturn(userList);

        List<User> uList = userService.getAllUsers();
        assertEquals(3, uList.size());
    }

    @Test
    public void service_should_create_a_new_user(){
        when(userRepository.save(userMock)).thenReturn(userMock);

        User user = userService.createNewUser(userMock);
        assertEquals("user@tc.com", user.getEmail());
    }

    @Test
    public void service_should_update_a_user(){
        User user = new User();
        user.setId("1");
        user.setPassword("newPass");
        Optional<User> returnCacheValue = Optional.of(user);
        when(userRepository.findById("1")).thenReturn(returnCacheValue);
        when(userRepository.save(user)).thenReturn(user);

        ResponseEntity<User> updatedUser = userService.updateUserById("1", user);
        assertEquals("newPass", updatedUser.getBody().getPassword());
    }

    @Test
    public void service_should_delete_a_user(){
        User user = new User();
        user.setId("1");
        user.setPassword("newPass");
        Optional<User> returnCacheValue = Optional.of(user);
        when(userRepository.findById("1")).thenReturn(returnCacheValue);

        userRepository.deleteById(returnCacheValue.get().getId());

        //verify the mocks
        verify(userRepository, times(1)).deleteById( eq( returnCacheValue.get().getId()));

        ResponseEntity<?> response = userService.deleteUserById("1");
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
