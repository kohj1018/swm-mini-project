package com.todaymeal.todaymeal.domain.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.todaymeal.todaymeal.domain.BaseEntity;
import com.todaymeal.todaymeal.domain.comment.Comment;
import com.todaymeal.todaymeal.domain.comment.CommentLike;
import com.todaymeal.todaymeal.domain.post.Post;
import com.todaymeal.todaymeal.domain.post.PostLike;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class User extends BaseEntity {
    protected User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private String nickName;
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    @Builder(builderMethodName = "userBuilder")
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.password = null;
        this.email = email;
        this.picture = null;
        this.nickName = name;
        this.picture = picture;
        this.role = role;
    }

    public void addComments(Comment comment) {
        comment.setUser(this);
    }

    public void addPostLikes(PostLike postLike) {
        postLike.setUser(this);
    }

    public void addCommentLikes(CommentLike commentLike) {
        commentLike.setUser(this);
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public void addUser(User user) {
        user.addUser(this);
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
