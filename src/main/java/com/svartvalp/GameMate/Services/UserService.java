package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.User;
import com.svartvalp.GameMate.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.naming.AuthenticationException;

@Service
public class UserService implements IUserService {
    UserRepository userRepository;
    PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    @Override
    public Mono<Boolean> verifyUser(User user) {
       return userRepository.findByEmail(user.getEmail())
                .map(foundUser -> {
                    return encoder.matches(user.getPassword(), foundUser.getPassword());
                });
    }

    @Override
    public Mono<User> findUserByNickName(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Mono<User> createUser(User user) {
        System.out.println(user.getEmail() + user.getPassword() + user.getNickname());
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void updatePassword(String email, String oldPassword, String newPassword) {
        userRepository.findByEmail(email).handle((user, sink) -> {
            if(!user.getPassword().equals(encoder.encode(oldPassword)))
                sink.error(new AuthenticationException("password is not correct"));
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
        });
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

}
