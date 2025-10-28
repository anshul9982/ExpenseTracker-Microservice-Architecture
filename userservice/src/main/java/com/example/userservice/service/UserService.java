package com.example.userservice.service;

import com.example.userservice.entities.UserInfo;
import com.example.userservice.model.UserSignUpEventDto;
import com.example.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserSignUpEventDto createOrUpdateUser(UserSignUpEventDto userSignUpEventDto){

        UnaryOperator<UserInfo> updateUser = userInfo1 -> {
            userInfo1.setFirstName(userSignUpEventDto.getFirstName());
            userInfo1.setLastName(userSignUpEventDto.getLastName());
            userInfo1.setEmail(userSignUpEventDto.getEmail());
            return userRepository.save(userInfo1);
        };

        Supplier<UserInfo> createUser = () -> {
            return userRepository.save(userSignUpEventDto.toUserInfo());
        };

        UserInfo userInfo = userRepository.findByUserId(userSignUpEventDto.getUserId()).map(updateUser).orElseGet(createUser);

        return new UserSignUpEventDto(userInfo.getUserId(), userInfo.getFirstName(), userInfo.getLastName(), userInfo.getEmail());
    }

    public UserSignUpEventDto getUser(String userId) {
        Optional<UserInfo> userInfo = userRepository.findByUserId(userId);
        return userInfo.map(UserSignUpEventDto::fromUserInfo).orElse(null);
    }
}
