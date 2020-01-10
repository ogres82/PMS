package com.jdry.pms.dir.controller;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.controller.IController;
import com.jdry.pms.dir.pojo.DirDirectory;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.dir.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Repository
public class DirDirectoryController implements IController {
    @Override
    public String getUrl() {
        return "/DirDirectory";
    }

    @Autowired
    private DirectoryService  directoryService;

    @Override
    public void execute(HttpServletRequest arg0, HttpServletResponse arg1) throws IOException, ServletException {
        arg0.setCharacterEncoding("utf-8");
        arg1.setCharacterEncoding("utf-8");
        arg1.setContentType("text/html; charset=utf-8");
        String method = arg0.getParameter("method");
        String jsonString ="";
        PrintWriter out = arg1.getWriter();
        try {
            if (method.equalsIgnoreCase("queryAll")) {
                List<DirDirectoryDetail> list= directoryService.queryAll();
                jsonString = JSON.toJSONString(list);
            }
        } catch  (Exception e) {
            // TODO: handle exception
            jsonString = JSON.toJSONString("操作失败！");
            e.printStackTrace();
        } finally {
            out.println(jsonString);
            out.flush();
        }
    }

    @Override
    public boolean anonymousAccess() {
        return false;
    }

    @Override
    public boolean isDisabled() {
        return false;
    }
}
