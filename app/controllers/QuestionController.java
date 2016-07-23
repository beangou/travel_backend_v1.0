package controllers;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.PagedList;
import com.fasterxml.jackson.databind.JsonNode;
import models.BlogPost;
import models.Option;
import models.Question;
import models.User;
import play.data.Form;
import play.data.validation.Constraints;
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

        List<Option> optionList = new ArrayList<>();
        Iterator<JsonNode> options = json.path("options").iterator();
        while(options.hasNext()) {
            JsonNode node = options.next();
            Option option = new Option();
            option.setContent(node.textValue());
            optionList.add(option);
            System.out.println("option=" + node.textValue());
        }

        System.out.println("options=" + options);

        Question question = new Question();
        question.setTitle(json.path("title").asText());
        question.setAnalysis(json.path("analysis").asText());
        question.setAnswers(json.path("answers").toString());
        question.setOptions(optionList);

        question.save();
//        }
        return ok(ApplicationController.buildJsonResponse("success", "question added successfully"));
    }

    public static class QuestionForm {

        @Constraints.Required
        public String title;

        @Constraints.Required
        public String options[];

        @Constraints.Required
        public String answers[];

        public String analysis;
    }


}
