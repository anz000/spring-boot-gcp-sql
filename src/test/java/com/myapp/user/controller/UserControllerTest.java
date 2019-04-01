package com.myapp.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.Application;
import com.myapp.user.entity.User;
import com.myapp.user.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    User userMock;

    @Before
    public void init() {
        userMock = new User("1","userName",
                "user@tc.com","passTC",
                new Date(), new Date());
    }

    @Test
    public void controller_should_get_user_by_id() throws Exception {
        when(userService.getUserById("1")).thenReturn(new ResponseEntity(userMock, HttpStatus.OK));
        this.mockMvc.perform(get("/user/{id}",1L)).andExpect(status().isOk());
    }

    @Test
    public void controller_should_create_a_new_user() throws Exception {
        when(userService.createNewUser(userMock)).thenReturn(userMock);
        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content( asJsonString(userMock) )
            )
            .andExpect(status().isOk());
        verify(userService, times(1)).createNewUser(userMock);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void controller_should_get_all_users() throws Exception {
        List<User> userList = new ArrayList<>();
        userList.add(userMock);
        userList.add(userMock);
        userList.add(userMock);

        when(userService.getAllUsers()).thenReturn(userList);
        this.mockMvc.perform(get("/user"))
                .andExpect(status().isOk());

        // TODO: 3/7/19 Should probably validate the user as well ; eg user count in the list
    }

    @Test
    public void controller_should_delete_user_by_id() throws Exception {
        when(userService.deleteUserById("1")).thenReturn(new ResponseEntity(null, HttpStatus.OK));
        this.mockMvc.perform(delete("/user/{id}",1L)).andExpect(status().isOk());
    }

    @Test
    public void controller_should_update_user_by_id() throws Exception {
        when(userService.updateUserById("1", userMock)).thenReturn(new ResponseEntity(userMock, HttpStatus.OK));
        this.mockMvc.perform(put("/user/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content( asJsonString(userMock) )
            )
            .andExpect(status().isOk());

        // TODO: 3/7/19 Add to validate
    }

    /*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}