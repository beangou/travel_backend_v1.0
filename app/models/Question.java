package models;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

/**
 * Created by beangou on 16/7/2.
 */

@Entity
@Table(name = "my_question")
public class Question extends Model {

    @Id
    public Integer id;

    // 标题
    public String title;

    // 选项
    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    public List<Option> options;

    // 多个,则用"、"隔开
    public String answers;

    // 解析
    public String analysis;

    public static Finder<Integer, Question> find = new Finder<>(Question.class);

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<Option> getOptions() {
        return options;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public String getAnswers() {
        return answers;
    }

    public String getAnalysis() {
        return analysis;
    }


}
