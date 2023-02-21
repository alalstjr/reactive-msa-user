package com.jjunpro.reactive.domain.user.dto;

import com.jjunpro.reactive.domain.user.User;
import javax.management.relation.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    String username;
    String nickname;
    String email;
    String phone;
    String password;
    Role   role;

    public static UserDto entityToDto(User user) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);

        return userDto;
    }
}
