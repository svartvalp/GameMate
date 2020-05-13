package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.Chat;
import com.svartvalp.GameMate.Models.User;
import com.svartvalp.GameMate.Repositories.ChatRepository;
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
    ChatRepository chatRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.chatRepository = chatRepository;
    }

    @Override
    public Mono<Boolean> verifyUser(User user) {
       return userRepository.findByEmail(user.getEmail())
                .map(foundUser -> encoder.matches(user.getPassword(), foundUser.getPassword()));
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
        return userRepository.insert(user);
    }

    @Override
    public Mono<User> updatePassword(String email, String oldPassword, String newPassword) {
        return userRepository.findByEmail(email)
                .handle((user, sink) -> {
            if(!encoder.matches(oldPassword, user.getPassword()))
                sink.error(new AuthenticationException("password is not correct"));
            user.setPassword(encoder.encode(newPassword));
            userRepository.save(user);
        });
    }

    @Override
    public Mono<Void> deleteUser(User user) {
       return userRepository.delete(user).then();
    }

    @Override
    public Mono<User> addChatToUser(String nickname, Chat chat) {
        return userRepository.findByNickname(nickname)
                .map(user -> {
                    user.getChatIds().add(chat.getId());
                    userRepository.save(user);
                    return user;
                });
    }

    @Override
    public Mono<User> addChatToUser(String nickname, String chatId) {
        return chatRepository.findById(chatId).map(chat -> addChatToUser(nickname,chat).block());
    }

}
