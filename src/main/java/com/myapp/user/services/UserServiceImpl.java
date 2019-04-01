package com.myapp.user.services;

import com.myapp.user.entity.User;
import com.myapp.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    /**
     * Fetch User By Id
     * @param id the id of the user to be fetched
     * @return
     */
    public ResponseEntity<User> getUserById(String id) {
        return userRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Fetch All Users
     * @return the list of all users
     */
    public List getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Create a new user
     * @param newUser the new user to be created
     * @return
     */
    public User createNewUser(User newUser){
        return userRepository.save(newUser);
    }

    /**
     * Update the given user
     * @param id the id of the user to be edited
     * @param editUser the user with the updated information
     * @return
     */
    public ResponseEntity<User> updateUserById(String id, User editUser) {
        return userRepository.findById(id)
                .map(record -> {
                    record.setUserName(editUser.getUserName());
                    record.setEmail(editUser.getEmail());
                    record.setPassword(editUser.getPassword());
                    User updated = userRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    /**
     * Delete the given user
     * @param id the id of the user to be deleted
     * @return
     */
    public ResponseEntity<?> deleteUserById(String id) {
        return userRepository.findById(id)
                .map(record -> {
                    userRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
