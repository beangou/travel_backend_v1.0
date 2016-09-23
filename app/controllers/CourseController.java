package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import io.netty.util.internal.StringUtil;
import models.Course;
import models.Question;
import play.data.Form;
import play.data.validation.Constraints;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.CourseTypeEnum;

import java.util.*;
import java.util.stream.Collectors;

public class CourseController extends Controller {

    public Result getChapters() {
        JsonNode json = request().body().asJson();
        System.out.println("json=" + json);
        Integer parentId = json.findPath("parentId").asInt();
        System.out.println("parentId=" + parentId);
        List<Course> list = Ebean.find(Course.class).where().eq("parentId", parentId).eq("type", 2).select("id, type, title, parentId").findList();
        if(list != null) {
            for (Course chapter: list) {
                List<Integer> typeList = chapter.getQuestions().stream().map((question)->question.getType()).distinct().collect(Collectors.toList());
                chapter.setTypeList(typeList);
                chapter.setQuestions(null);
                chapter.getQuestions();
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("responseCode", "00");
        map.put("responseMsg", "SUCCESS");
        map.put("data", list);
        return ok(Json.toJson(map));
    }

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

    public Result deleteCourse() {
        JsonNode json = request().body().asJson();
        System.out.println("json=" + json);
        Integer id = json.findPath("id").asInt();

        Map<String, Object> map = new HashMap<>();
        if(Course.find.byId(id).delete()) {
            map.put("responseCode", "00");
            map.put("responseMsg", "SUCCESS");
        }else {
            map.put("responseCode", "01");
            map.put("responseMsg", "FAILURE");
        }
        return ok(Json.toJson(map));
    }

    public Result updateCourse() {
        Form<CourseUpdateForm> courseUpdateForm = Form.form(CourseUpdateForm.class).bindFromRequest();
        if (courseUpdateForm.hasErrors()) {
            return badRequest(courseUpdateForm.errorsAsJson());
        } else {
            Date now = new Date();
            Course course = Course.find.byId(courseUpdateForm.get().id);
            if(!StringUtil.isNullOrEmpty(courseUpdateForm.get().content)) {
                course.setContent(courseUpdateForm.get().content);
            }
            if(!StringUtil.isNullOrEmpty(courseUpdateForm.get().title)) {
                course.setTitle(courseUpdateForm.get().title);
            }
            course.setUpdatedAt(now);
            course.update();
        }
        return ok(ApplicationController.buildJsonResponse("success", "course updated successfully"));
    }

    public Result listByParentId() {
        JsonNode json = request().body().asJson();
        System.out.println("json=" + json);
        Integer parentId = json.findPath("parentId").asInt();
        System.out.println("parentId=" + parentId);
        List<Course> list = Ebean.find(Course.class).where().eq("parentId", parentId).select("id, type, title, parentId").findList();
//        List<Course> list = Ebean.find(Course.class).fetch("id").where().eq("parentId", parentId).findList();
        Integer type = json.findPath("type").asInt();
        System.out.println("type="+type);
        if(type != 0 && list != null) {
            // 如果查询节,则也查出小节
           for (Course course : list) {
               List<Course> sonlist = Course.find.select("id, type, title, parentId").where().eq("parentId", course.getId()).findList();
               course.setSonCourses(sonlist);
               course.setContent(null);
           }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("responseCode", "00");
        map.put("responseMsg", "SUCCESS");
        map.put("data", list);
        return ok(Json.toJson(map));
    }

    public Result addCourse() {
        JsonNode json = request().body().asJson();
        System.out.println("json=" + json);
        Integer parentId = json.findPath("parentId").asInt();
        Integer type = json.findPath("type").asInt();
        String title = json.findPath("title").asText();

        Date now = new Date();

        Course course = new Course();
        course.setParentId(parentId);
        course.setTitle(title);
        course.setType(type);
        course.setCreatedAt(now);
        course.setUpdatedAt(now);

        course.save();
        return ok(ApplicationController.buildJsonResponse("success", "course added successfully"));
    }

    public static class CourseUpdateForm {
        @Constraints.Required
        public Integer id;

        public String title;

        public String content;
    }

}
