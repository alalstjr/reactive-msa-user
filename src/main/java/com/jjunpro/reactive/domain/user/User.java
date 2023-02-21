package com.jjunpro.reactive.domain.user;

import com.jjunpro.reactive.domain.configs.Base;
import com.jjunpro.reactive.domain.user.dto.UserDto;
import javax.management.relation.Role;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@EqualsAndHashCode
@Document(collection = "users")
public class User extends Base {

    String username;
    String nickname;
    String email;
    String phone;
    String password;
    Role   role;
}
