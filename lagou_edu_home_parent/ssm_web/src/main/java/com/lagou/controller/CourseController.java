package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    //多条件课程列表查询

    @RequestMapping("/findCourseByCondition")
    public ResponseResult findCourseByCondition(@RequestBody CourseVo courseVo){
        List<Course> courseList = courseService.findCourseByCondition(courseVo);
        ResponseResult result = new ResponseResult(true, 200, "响应成功", courseList);
        return result;
    }

    //图片上传接口
    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {

        //1.判断文件是否为空
        if(file.isEmpty()){
            throw new RuntimeException();
        }

        //2.获取项目部署路径
        // D:\apache-tomcat-8.5.56\webapps\ssm_web\
        String realPath = request.getServletContext().getRealPath("/");
        // D:\apache-tomcat-8.5.56\webapps\
        String webappPath = realPath.substring(0,realPath.indexOf("ssm_web"));
        //3.获取原文件名
        String originalFilename = file.getOriginalFilename();

        //4.新文件名
        String newFileName = System.currentTimeMillis()+originalFilename.substring(originalFilename.lastIndexOf("."));

        //5.上传文件
        String uploadPth = webappPath+"upload/";
        File filePath = new File(uploadPth, newFileName);

        //如果目录不存在就创建目录
        if(!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录"+filePath);
        }

        file.transferTo(filePath);

        //6.将文件名和文件路径返回
        Map<String,String> map = new HashMap<>();
        map.put("fileName",newFileName);
        //"filePath": "http://localhost:8080/upload/1597112871741.JPG"
        map.put("filePath","http://localhost:8080/upload/"+newFileName);
        return new ResponseResult(true,200,"图片上传成功",map);
    }


    //新建&修改课程接口
    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVo courseVo) throws InvocationTargetException, IllegalAccessException {
        if(courseVo.getId()==0){

            //调用service
            courseService.saveCourseOrTeacher(courseVo);
            return new ResponseResult(true, 200, "新增成功", null);

        }else {
            courseService.updateCourseOrTeacher(courseVo);
            return new ResponseResult(true, 200, "修改成功", null);
        }
    }
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(@RequestParam int id){
        CourseVo courseVo = courseService.findCourseById(id);
        return new ResponseResult(true,200,"响应成功",courseVo);
    }

    //修改课程状态
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id,Integer status){
        courseService.updateCourseStatus(id,status);
        Map<String,Integer> map = new HashMap<>();
        map.put("status",status);
        return new ResponseResult(true,200,"修改状态成功",map);
    }


}
