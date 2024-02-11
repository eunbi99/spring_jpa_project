package com.europehang.europe.user.domain;

import com.europehang.europe.common.BaseEntity;
import com.europehang.europe.common.enums.Gender;
import com.europehang.europe.common.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@Getter
@ToString
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @JoinColumn(name = "user_name")
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String nickname;


    @Builder
    public User(String email, String username, String password,Gender gender,String nickname) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.nickname = nickname;
    }

}
