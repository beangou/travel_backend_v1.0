package models;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by beangou on 16/7/2.
 */

@Entity
@Table(name = "my_course")
public class Course extends Model {

    @Id
    public Integer id;

    // course类型 1: 书名 2: 章名 3: 节名 4: 小节名
    public Integer type;

    public String title;

    // 只有type=4时,才有值
    public String content;

    // 书的parentId为0
    public Integer parentId;

    public static Finder<Integer, Course> find = new Finder<>(Course.class);

    public void setId(Integer id) {

        this.id = id;

    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getParentId() {
        return parentId;
    }

}
