package com.svartvalp.GameMate.Services;

import com.svartvalp.GameMate.Models.User;
import reactor.core.publisher.Mono;

public interface IUserService {
    public Mono<Boolean> verifyUser(User user);
    public Mono<User> findUserByNickName(String nickname);
    public Mono<User> findUserByEmail(String email);
    public Mono<User> createUser(User user);
    public void updatePassword(String email, String oldPassword, String newPassword);
    public void deleteUser(User user);
}
