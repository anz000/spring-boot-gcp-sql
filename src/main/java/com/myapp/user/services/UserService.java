package com.myapp.user.services;

import com.myapp.user.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    /**
     * Fetch User By Id
     * @param id the id of the user to be fetched
     * @return
     */
    public ResponseEntity<User> getUserById(String id);

    /**
     * Fetch All Users
     * @return the list of all users
     */
    public List getAllUsers();

    /**
     * Create a new user
     * @param newUser the new user to be created
     * @return
     */
    public User createNewUser(User newUser);

    /**
     * Update the given user
     * @param id the id of the user to be edited
     * @param editUser the user with the updated information
     * @return
     */
    public ResponseEntity<User> updateUserById(String id, User editUser);

    /**
     * Delete the given user
     * @param id the id of the user to be deleted
     * @return
     */
    public ResponseEntity<?> deleteUserById(String id);

}
