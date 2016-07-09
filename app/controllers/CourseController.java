package controllers;

import com.avaje.ebean.Ebean;
import models.Course;
import play.data.Form;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class CourseController extends Controller {

    public Result getCourse(Integer id) {
        return ok(Json.toJson(Course.find.byId(id)));
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

    public Result listByParentId(Integer parentId) {
        List<Course> list = Ebean.find(Course.class).select("id, type, title, parentId").where().eq("parentId", parentId).findList();
        return ok(Json.toJson(list));
    }

    public Result addCourse() {
        Form<CourseForm> courseForm = Form.form(CourseForm.class).bindFromRequest();

        if (courseForm.hasErrors()) {
            return badRequest(courseForm.errorsAsJson());
        } else {
            Course course = new Course();

            course.setContent(courseForm.get().content);
            course.setParentId(courseForm.get().parentId);
            course.setTitle(courseForm.get().title);
            course.setType(courseForm.get().type);

            course.save();
        }
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
