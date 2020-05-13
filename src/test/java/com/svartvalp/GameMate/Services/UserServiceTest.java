package com.svartvalp.GameMate.Services;


import com.svartvalp.GameMate.Models.Chat;
import com.svartvalp.GameMate.Models.User;
import com.svartvalp.GameMate.Repositories.ChatRepository;
import com.svartvalp.GameMate.Repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;



@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @MockBean
    UserRepository userRepository;
    @MockBean
    ChatRepository chatRepository;

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void verifyUseTest() {
        Mockito.when(userRepository.findByEmail("email"))
                .thenReturn(Mono.just(new User("email", "nickname", encoder.encode("password"))));
        IUserService userService = new UserService(userRepository, encoder, chatRepository);
        StepVerifier.create(userService.verifyUser(new User("email", "nickname", "password")))
                .expectNext(true);
    }

    @Test
    public void addChatToUserTest() {
        User user = new User("email", "nickname", encoder.encode("password"));
        Mockito.when(userRepository.findByNickname("nickname")).thenReturn(Mono.just(user));
        IUserService userService = new UserService(userRepository, encoder, chatRepository);
        userService.addChatToUser(user.getNickname(), new Chat()).then().subscribe((f) -> {
            Assertions.assertEquals(1, user.getChatIds().size());
        });
    }

    @Test
    public void updatePasswordTest() {
        User user = new User("email", "nickname", encoder.encode("password"));
        Mockito.when(userRepository.findByEmail("email")).thenReturn(Mono.just(user));
        IUserService userService = new UserService(userRepository, encoder, chatRepository);
        userService.updatePassword("email", "password", "newPassword")
                .subscribe(user1 -> Assertions.assertEquals(true, encoder.matches("newPassword", user.getPassword())))
        ;
    }

}
