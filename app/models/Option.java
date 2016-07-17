package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;

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
    public Question question;

    // 创建时间
    public Timestamp created_at;

    // 更新时间
    public Timestamp updated_at;

    // 删除时间
    public Timestamp deleted_at;

    public static Finder<Integer, Option> find = new Finder<>(Option.class);

    public void setId(Integer id) {
        this.id = id;
    }


    public void setContent(String content) {
        this.content = content;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public void setDeleted_at(Timestamp deleted_at) {
        this.deleted_at = deleted_at;
    }

    public Integer getId() {
        return id;

    }

    public String getContent() {
        return content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public Timestamp getDeleted_at() {
        return deleted_at;
    }

}
