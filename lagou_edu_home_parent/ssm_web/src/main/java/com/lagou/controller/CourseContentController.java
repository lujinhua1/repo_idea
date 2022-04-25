package com.lagou.controller;


import com.lagou.domain.Course;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    //查询课程内容
    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLessonByCourseId(Integer courseId){
        List<CourseSection> sectionAndLessonByCourseId = courseContentService.findSectionAndLessonByCourseId(courseId);
        return new ResponseResult(true,200,"章节及课时查询成功",sectionAndLessonByCourseId);
    }

    //回显章节对应的课程信息
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(Integer courseId){
        Course course = courseContentService.findCourseByCourseId(courseId);
        return new ResponseResult(true,200,"回显课程信息成功",course);
    }

    //新建课程
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection section){
        if(section.getId()==null){
            courseContentService.saveSection(section);
            return new ResponseResult(true,200,"新建章节成功",null);
        }else {
            courseContentService.updateSection(section);
            return new ResponseResult(true,200,"修改章节成功",null);
        }
    }
}
