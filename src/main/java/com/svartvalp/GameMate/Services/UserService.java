package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Exceptions.AuthenticationException;
import com.svartvalp.GameMate.Models.Chat;
import com.svartvalp.GameMate.Models.User;
import com.svartvalp.GameMate.Repositories.ChatRepository;
import com.svartvalp.GameMate.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;



@Service
public class UserService implements IUserService {
    private UserRepository userRepository;
    private PasswordEncoder encoder;
    private ChatRepository chatRepository;

    public UserService() {
    }

    public UserService(UserRepository userRepository, PasswordEncoder encoder, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.chatRepository = chatRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setChatRepository(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Mono<User> verifyUser(User user) {
       return userRepository.findByEmail(user.getEmail())
                .flatMap(foundUser ->
                        encoder.matches(user.getPassword(), foundUser.getPassword()) ?
                                Mono.just(user) : Mono.error(new AuthenticationException()));
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
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.insert(user).onErrorMap((e) -> new AuthenticationException());
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
        return userRepository.findByNickname(user.getNickname()).flatMap(foundUser -> {
            if(foundUser.getEmail().equals(user.getEmail()) &&
                    encoder.matches(user.getPassword(), foundUser.getPassword())) {
                return userRepository.delete(foundUser);
            }
            return Mono.error(new AuthenticationException());
        });
    }

    @Override
    public Mono<User> addChatToUser(String nickname, Chat chat, String ownerNickname) {
        return userRepository.findByNickname(nickname)
                .flatMap(user -> {
                    if(chat.getOwnerNickname().equals(ownerNickname)) {
                        chat.getUserRequests().remove(user.getNickname());
                        user.getChatIds().add(chat.getId());
                        userRepository.save(user);
                        return Mono.just(user);
                    }
                    return Mono.error(new AuthenticationException("you have permissions to do that"));
                });
    }

    @Override
    public Mono<User> addChatToUser(String nickname, String chatId, String ownerNickname) {
        return chatRepository.findById(chatId).flatMap(chat -> addChatToUser(nickname,chat, ownerNickname));
    }

    @Override
    public Mono<Chat> subscribeToChat(String username, String chatId) {
        return chatRepository.findById(chatId).map(chat -> {chat.getUserRequests().add(username); return chat;});
    }

}
