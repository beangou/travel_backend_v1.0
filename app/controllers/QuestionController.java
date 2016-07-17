package controllers;

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
import java.util.List;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class QuestionController extends Controller {

    public Result addQuestion() {
        Form<QuestionForm> questionForm = Form.form(QuestionForm.class).bindFromRequest();

        if (questionForm.hasErrors()) {
            return badRequest(questionForm.errorsAsJson());
        } else {

            List<Option> optionList = new ArrayList<>();
            for (String optionStr :questionForm.get().options) {
                Option option = new Option();
                option.setContent(optionStr);
                optionList.add(option);
                option = null;
            }

            Question question = new Question();
            question.setTitle(questionForm.get().title);
            question.setAnalysis(questionForm.get().analysis);
            question.setAnswers(questionForm.get().answers);
            question.setOptions(optionList);

            question.save();
        }
        return ok(ApplicationController.buildJsonResponse("success", "question added successfully"));
    }

    public static class QuestionForm {

        @Constraints.Required
        public String title;

        @Constraints.Required
        public String options[];

        @Constraints.Required
        public String answers;

        public String analysis;
    }


}
