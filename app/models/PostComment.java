package models;

import com.avaje.ebean.Finder;
import com.avaje.ebean.Model;
import com.avaje.ebean.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * Created by beangou on 16/7/4.
 */
@Entity
public class PostComment extends Model {

    @Id
    public Long id;

    @ManyToOne
    @JsonIgnore
    @JsonBackReference
    public BlogPost blogPost;

    @ManyToOne
    @JsonBackReference
    public User user;

    @Column(columnDefinition = "TEXT")
    public String content;

    public static final Finder<Long, PostComment> find = new Finder<Long, PostComment>(
            Long.class, PostComment.class);

    public static List<PostComment> findAllCommentsByPost(final BlogPost blogPost) {
        return find.where().eq("post", blogPost).findList();
    }

    public static List<PostComment> findAllCommentsByUser(final User user) {
        return find
                .where()
                .eq("user", user)
                .findList();
    }

}

