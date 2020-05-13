package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.Chat;
import com.svartvalp.GameMate.Models.User;
import reactor.core.publisher.Mono;

public interface IUserService {
    public Mono<Boolean> verifyUser(User user);
    public Mono<User> findUserByNickName(String nickname);
    public Mono<User> findUserByEmail(String email);
    public Mono<User> createUser(User user);
    public Mono<User> updatePassword(String email, String oldPassword, String newPassword);
    public Mono<Void> deleteUser(User user);
    public Mono<User> addChatToUser(String nickname, Chat chat);
    public Mono<User> addChatToUser(String nickname, String chatId);
}
