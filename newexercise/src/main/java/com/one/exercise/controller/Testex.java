package com.one.exercise.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class Testex {

    @PostMapping("down")
    public void a(HttpServletRequest request, HttpServletResponse response){


        // 设置相应头，告诉浏览器该文件可以下载
        response.setContentType(request.getServletContext().getMimeType("test.pdf"));
        response.setHeader("Content-Disposition", "attachment;filename=" + "test.pdf");
    }

}
