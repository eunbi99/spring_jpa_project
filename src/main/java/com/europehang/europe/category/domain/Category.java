package com.europehang.europe.category.domain;

import com.europehang.europe.post.domain.Post;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jdk.jshell.Snippet;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="parent_id")
    private Category parent;

    // 부모 엔티티가 여러개의 자식 엔티티를 가질수 있다. -> 하나의 상위 메뉴에 여러개의 하위메뉴를 가진다.
    @JsonIgnore
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Category> children = new ArrayList<>();

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    private List<Post> parentcCatrgoryPosts = new ArrayList<>();

    @OneToMany(mappedBy = "childCategory", fetch = FetchType.LAZY)
    private List<Post> childCategoryPosts = new ArrayList<>();

    @Builder
    public Category(String categoryName, Category parent) {
        this.categoryName = categoryName;
        this.parent = parent;
    }
}
