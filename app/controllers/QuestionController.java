package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.PagedList;
import com.fasterxml.jackson.databind.JsonNode;
import models.Course;
import models.Option;
import models.Question;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class QuestionController extends Controller {

    public Result list() {
        JsonNode json = request().body().asJson();
        Integer pageIndex = json.path("pageIndex").asInt();
        Integer pageSize  = json.path("pageSize").asInt();
        if(pageIndex == null) {
            pageIndex = 1;
        }
        if(pageSize == null) {
            pageSize = 5;
        }

        PagedList<Question> list = Ebean.find(Question.class).findPagedList(pageIndex, pageSize);
        int rowCount = Question.find.findRowCount();
        Map<String, Object> map = new HashMap<>();
        map.put("responseCode", "00");
        map.put("responseMsg", "SUCCESS");
        map.put("data", list.getList());
        map.put("totalItems", rowCount);
        return ok(Json.toJson(map));
    }

    public Result getQuestion() {
        JsonNode json = request().body().asJson();
        Integer id = json.path("id").asInt();

        Map<String, Object> map = new HashMap<>();
        map.put("responseCode", "00");
        map.put("responseMsg", "SUCCESS");
        map.put("data", Question.find.byId(id));
        return ok(Json.toJson(map));
    }

    public Result addQuestion() {

        JsonNode json = request().body().asJson();

        System.out.println("json=" + json);
        Integer type = json.path("type").asInt();
        Date now = new Date();

        Question question = new Question();
        question.setTitle(json.path("title").asText());
        question.setAnalysis(json.path("analysis").asText());
        question.setAnswers(json.path("answers").toString());
        question.setType(type);
        question.setCreatedAt(now);
        question.setUpdatedAt(now);

        Integer courseId = json.path("courseId").asInt();
        Course course = Course.find.byId(courseId);
        question.setCourse(course);
        if(type != 3) {
            // 选择题才有选项
            List<Option> optionList = new ArrayList<>();
            Iterator<JsonNode> options = json.path("options").iterator();

            while(options.hasNext()) {
                JsonNode node = options.next();
                Option option = new Option();
                option.setContent(node.textValue());
                option.setCreatedAt(now);
                option.setUpdatedAt(now);
                optionList.add(option);
            }
            question.setOptions(optionList);
        }

        question.save();
        return ok(ApplicationController.buildJsonResponse("success", "question added successfully"));
    }


}
