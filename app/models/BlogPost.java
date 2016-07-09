package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.util.List;

/**
 * Created by beangou on 16/7/4.
 */
@Entity
public class BlogPost extends Model {

    @Id
    public Long id;

    @Column(length = 255, nullable = false)
    @Constraints.MaxLength(255)
    @Constraints.Required
    public String subject;

    @Column(columnDefinition = "TEXT")
    @Constraints.Required
    public String content;

    @ManyToOne
    @JsonBackReference
    public User user;

    public Long commentCount;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    public List<PostComment> comments;

    public static final Finder<Long, BlogPost> find = new Finder<Long, BlogPost>(Long.class, BlogPost.class);

    public static List<BlogPost> findBlogPostsByUser(final User user) {
        return find.where().eq("user", user).findList();
    }

    public static BlogPost findBlogPostById(final Long id) {
        return find.where().eq("id", id).findUnique();
    }

}
