package com.europehang.europe.user.domain;

import com.europehang.europe.common.BaseEntity;
import com.europehang.europe.common.converts.GenderConvert;
import com.europehang.europe.common.enums.Gender;
import com.europehang.europe.post.domain.Post;
import com.europehang.europe.role.domain.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;

    private String password;

    @Convert(converter = GenderConvert.class)
    private Gender gender;

    private String nickname;

    @ColumnDefault("Y")
    @Column(name = "is_active")
    private String isActive;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name="user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "role_name")}
    )
    private Set<Role> roles = new HashSet<>();

    @Builder
    public User(String email, String username, String password,Gender gender,String nickname, Set<Role> roles) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.nickname = nickname;
        this.roles = roles;
    }

}
