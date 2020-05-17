package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.Chat;
import com.svartvalp.GameMate.Models.User;
import reactor.core.publisher.Mono;

public interface IUserService {
    Mono<User> verifyUser(User user);
    Mono<User> findUserByNickName(String nickname);
    Mono<User> findUserByEmail(String email);
    Mono<User> createUser(User user);
    Mono<User> updatePassword(String email, String oldPassword, String newPassword);
    Mono<Void> deleteUser(User user);
    Mono<User> addChatToUser(String nickname, Chat chat);
    Mono<User> addChatToUser(String nickname, String chatId);
}
