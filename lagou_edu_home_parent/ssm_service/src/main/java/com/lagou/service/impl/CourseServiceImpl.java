package com.lagou.service.impl;

import com.lagou.dao.CourseMapper;
import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;
import com.lagou.domain.Teacher;
import com.lagou.service.CourseService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Override
    public List<Course> findCourseByCondition(CourseVo courseVo) {
        return courseMapper.findCourseByCondition(courseVo);
    }

    @Override
    public void saveCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {

        //封装课程信息
        Course course = new Course();
        BeanUtils.copyProperties(course,courseVo);

        //补全课程信息
        Date data = new Date();
        course.setCreateTime(data);
        course.setUpdateTime(data);

        System.out.println(courseVo);
        System.out.println(course);

        //保存课程
        courseMapper.saveCourse(course);
        //获取新插入数据的id
        int id = course.getId();

        //封装讲师信息
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher,courseVo);
        teacher.setCourseId(id);
        teacher.setCreateTime(data);
        teacher.setUpdateTime(data);
        //保存讲师信息
        courseMapper.saveTeacher(teacher);

    }

    @Override
    public CourseVo findCourseById(int id) {
        return courseMapper.findCourseById(id);
    }

    @Override
    public void updateCourseOrTeacher(CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {
        //封装课程信息
        Course course = new Course();
        BeanUtils.copyProperties(course,courseVo);

        //补全信息
        Date date = new Date();
        course.setUpdateTime(date);
        courseMapper.updateCourse(course);

        //封装讲师信息
        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacher,courseVo);

        //补全信息
        teacher.setUpdateTime(date);
        teacher.setCourseId(course.getId());
        courseMapper.updateTeacher(teacher);
    }

    @Override
    public void updateCourseStatus(int id, int status) {
        Course course = new Course();
        course.setUpdateTime(new Date());
        course.setId(id);
        course.setStatus(status);
        courseMapper.updateCourseStatus(course);
    }


}
