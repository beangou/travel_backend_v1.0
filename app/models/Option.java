package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by beangou on 16/7/2.
 */

@Entity
@Table(name = "my_option")
public class Option extends Model {

    @Id
    public Integer id;

    // 题目id
//    public Integer question_id;

    // 选项内容
    public String content;

    // 题目
    @ManyToOne
    @JsonBackReference
    public Question question;

    // 创建时间
    public Date createdAt;

    // 更新时间
    public Date updatedAt;

    // 删除时间
    public Date deletedAt;

    public static Finder<Integer, Option> find = new Finder<>(Option.class);

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Integer getId() {
        return id;

    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

}
