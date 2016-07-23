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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseController extends Controller {

    public Result getCourse() {
        JsonNode json = request().body().asJson();
        System.out.println("json=" + json);
        Integer id = json.findPath("id").asInt();

        Map<String, Object> map = new HashMap<>();
        map.put("responseCode", "00");
        map.put("responseMsg", "SUCCESS");
        map.put("data", Course.find.byId(id));

        return ok(Json.toJson(map));
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
        Integer type = json.findPath("type").asInt();
        System.out.println("type="+type);
        if(type == 3 && list != null) {
            // 如果查询节,则也查出小节
           for (Course course : list) {
               List<Course> sonlist = Ebean.find(Course.class).select("id, type, title, parentId").where().eq("parentId", course.getId()).findList();
               course.setSonCourses(sonlist);
           }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("responseCode", "00");
        map.put("responseMsg", "SUCCESS");
        map.put("data", list);
        return ok(Json.toJson(map));
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

        System.out.println("courseTitle=" + course.getTitle());
        System.out.println("course=" + course);
        course.save();
        System.out.println("courseTitle=" + course.getTitle());
        System.out.println("course=" + course);
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
