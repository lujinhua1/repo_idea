package com.lagou.service;

import com.lagou.domain.Course;
import com.lagou.domain.CourseSection;

import java.util.List;

public interface CourseContentService {
    //查询课程下的章节与课时信息
    public List<CourseSection> findSectionAndLessonByCourseId(int courseId);


    //回显课程信息
    public Course findCourseByCourseId(int courseId);

    //保存章节
    public void saveSection(CourseSection section);

    //更新课程
    public void updateSection(CourseSection section);

}
