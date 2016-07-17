package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import models.Course;
import play.data.Form;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.CourseTypeEnum;

import java.util.List;

import static utils.CourseTypeEnum.BOOK_NAME;

public class CourseController extends Controller {

    public Result getCourse() {
        JsonNode json = request().body().asJson();
        System.out.println("json=" + json);
        Integer id = json.findPath("id").asInt();
        return ok(Json.toJson(Course.find.byId(id)));
    }

    public Result booklist() {
        return ok(Json.toJson(Ebean.find(Course.class).select("id, title").where().eq("type", CourseTypeEnum.BOOK_NAME.getType()).findList()));
    }

    public Result list() {
        return ok(Json.toJson(Course.find.findList()));
    }

    public Result updateCourse() {
        Form<CourseUpdateForm> courseUpdateForm = Form.form(CourseUpdateForm.class).bindFromRequest();
        if (courseUpdateForm.hasErrors()) {
            return badRequest(courseUpdateForm.errorsAsJson());
        } else {
            Course course = Course.find.byId(courseUpdateForm.get().id);
            course.setContent(courseUpdateForm.get().content);
            course.update();
        }
        return ok(ApplicationController.buildJsonResponse("success", "course updated successfully"));
    }

    public Result listByParentId() {
        JsonNode json = request().body().asJson();
        System.out.println("json=" + json);
        Integer parentId = json.findPath("parentId").asInt();
        System.out.println("parentId=" + parentId);
        List<Course> list = Ebean.find(Course.class).select("id, type, title, parentId").where().eq("parentId", parentId).findList();
        return ok(Json.toJson(list));
    }

    public Result addCourse() {
//        Form<CourseForm> courseForm = Form.form(CourseForm.class).bindFromRequest();

//        if (courseForm.hasErrors()) {
//            return badRequest(courseForm.errorsAsJson());
//        } else {
//        request().
        JsonNode json = request().body().asJson();
        System.out.println("json=" + json);
        Integer parentId = json.findPath("parentId").asInt();
        Integer type = json.findPath("type").asInt();
        String title = json.findPath("title").asText();

        System.out.println("************");
        System.out.println("title===" + title);

        Course course = new Course();
        course.setParentId(parentId);
        course.setTitle(title);
        course.setType(type);

        course.save();
        return ok(ApplicationController.buildJsonResponse("success", "course added successfully"));
    }

    public static class CourseForm {

        @Constraints.Required
        public Integer type;

        @Constraints.Required
        public String title;

        public String content;

        public Integer parentId;

    }

    public static class CourseUpdateForm {
        @Constraints.Required
        public Integer id;

        @Constraints.Required
        public String content;
    }

}
