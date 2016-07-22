package controllers;

import com.avaje.ebean.Ebean;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class QuestionController extends Controller {

    public Result list() {
        List<Question> list = Ebean.find(Question.class).findList();
        return ok(Json.toJson(list));
    }

    public Result addQuestion() {
//        Form<QuestionForm> questionForm = Form.form(QuestionForm.class).bindFromRequest();

//        if (questionForm.hasErrors()) {
//            return badRequest(questionForm.errorsAsJson());
//        } else {

        JsonNode json = request().body().asJson();

        System.out.println("json=" + json);

//        List<String> options = json.findValuesAsText("options");

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
