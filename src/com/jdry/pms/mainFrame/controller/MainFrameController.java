package com.jdry.pms.mainFrame.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.business.IDept;
import com.bstek.bdf2.core.business.IPosition;
import com.bstek.bdf2.core.business.IUser;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.model.Message;
import com.bstek.bdf2.core.model.Url;
import com.bstek.bdf2.core.view.frame.main.MainFrame;
import com.bstek.bdf2.core.view.frame.main.register.changepassword.ChangePassword;
import com.bstek.bdf2.core.view.frame.main.register.message.SeeMessage;
import com.bstek.bdf2.core.view.frame.main.register.message.SendMessage;
import com.bstek.dorado.core.Configure;
import com.jdry.pms.chargeManager.pojo.ChargeArrearViewEntity;
import com.jdry.pms.chargeManager.service.ChargeArrearViewService;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.comm.util.ImgCompress;
import com.jdry.pms.mainFrame.dao.MainFrameDao;
import com.jdry.pms.mainFrame.pojo.VMessage;
import com.jdry.pms.system.dao.SystemLogDao;
import com.jdry.pms.system.pojo.SystemLog;
import com.jdry.pms.system.service.UrlService;
import com.jdry.pms.system.service.UserMaintainService;
import com.jdry.pms.topTask.service.TopTaskService;

@Repository
@Component
public class MainFrameController implements IController {

    @Resource
    CommUser user;

    @Resource
    MainFrame mainFrame;

    @Resource
    ChangePassword change;

    @Resource
    SendMessage send;

    @Resource
    SeeMessage see;

    @Resource
    ChargeArrearViewService chargeArrearViewService;

    @Resource
    MainFrameDao dao;

    @Resource
    TopTaskService topTaskService;

    @Resource
    UserMaintainService userService;

    @Resource
    private UrlService urlService;

    @Resource
    SystemLogDao logDao;

    @Override
    public boolean anonymousAccess() {
        return false;
    }

    @Override
    public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
            throws IOException, ServletException {

        arg0.setCharacterEncoding("utf-8");
        arg1.setCharacterEncoding("utf-8");
        arg1.setContentType("text/html; charset=utf-8");
        String method = arg0.getParameter("method");
        String parentId = null == arg0.getParameter("parentId") ? "" : arg0.getParameter("parentId").toString();

        try {

            if (method.equalsIgnoreCase("loadUrls")) {
                List<Url> urls = urlService.loadMeunUrls("");
                String b = JSON.toJSONString(urls);
                PrintWriter out = arg1.getWriter();
                out.print(b);
                out.flush();
            }

            if (method.equalsIgnoreCase("getCurrentUserInfo")) {
                @SuppressWarnings("static-access")
                IUser iuser = CommUser.getUser();
                String b = JSON.toJSONString(iuser);
                PrintWriter out = arg1.getWriter();
                out.print(b);
                out.flush();
            }

            if (method.equalsIgnoreCase("checkPwd")) {

                String fieldId = null == arg0.getParameter("fieldId") ? "" : arg0.getParameter("fieldId").toString();
                String fieldValue = null == arg0.getParameter("fieldValue") ? "" : arg0.getParameter("fieldValue").toString();
                String info = "[\"" + fieldId + "\",false]";
                try {
                    String check = change.checkPassword(fieldValue);
                    if (check == null) {
                        info = "[\"" + fieldId + "\",true]";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    info = "[\"" + fieldId + "\",false]";
                }
                PrintWriter out = arg1.getWriter();
                out.print(info);
                out.flush();
            }

            if (method.equalsIgnoreCase("changePwd")) {
                String newPassword = null == arg0.getParameter("newPassword") ? "" : arg0.getParameter("newPassword").toString();
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("newPassword", newPassword);
                String result = "failed";
                try {
                    change.changePassword(data);
                    result = "success";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    PrintWriter out = arg1.getWriter();
                    out.print(result);
                    out.flush();
                }
            }

            if (method.equalsIgnoreCase("sendMsg")) {
                String title = null == arg0.getParameter("title") ? "" : arg0.getParameter("title").toString();
                String content = null == arg0.getParameter("content") ? "" : arg0.getParameter("content").toString();
                String receiver = null == arg0.getParameter("receiver") ? "" : arg0.getParameter("receiver").toString();
                String result = "failed";

                Message msg = new Message();

                msg.setTitle(title);
                msg.setContent(content);
                msg.setReceiver(receiver);
                try {
                    send.send(msg);
                    result = "success";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    PrintWriter out = arg1.getWriter();
                    out.print(JSON.toJSONString(result));
                    out.flush();
                }
            }

            if (method.equalsIgnoreCase("receiveMsgInfo")) {


                List<VMessage> result = dao.queryReceiveMsgInfo();
                String b = JSON.toJSONString(result);
                //long count = page.getEntityCount();				//获取总记录数
                PrintWriter out = arg1.getWriter();
                //String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
                out.print(b);
                out.flush();
            }

            if (method.equalsIgnoreCase("hasSentMsgInfo")) {

                List<VMessage> result = dao.queryHasSentMsgInfo();
                String b = JSON.toJSONString(result);
                //long count = page.getEntityCount();				//获取总记录数
                PrintWriter out = arg1.getWriter();
                //String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
                out.print(b);
                out.flush();
            }

            if (method.equalsIgnoreCase("updateMsg")) {
                Session session = see.getSessionFactory().openSession();
                String id = arg0.getParameter("id") == null ? "" : arg0.getParameter("id");
                if (!id.isEmpty()) {

                    List<Message> lists = see
                            .query("from " + Message.class.getName()
                                    + " where id='" + id + "'");
                    if (null != lists && lists.size() > 0) {
                        Message msg = lists.get(0);
                        msg.setRead(true);
                        session.update(msg);
                        session.flush();
                        session.close();
                    }
                }
            }

            if (method.equalsIgnoreCase("unreadMsg")) {
                String receiver = CommUser.getUserName();
                if (!receiver.isEmpty()) {

                    List<Message> lists = see
                            .query("from " + Message.class.getName()
                                    + " where receiver='" + receiver + "' and read = 0");
                    if (null != lists && lists.size() > 0) {
                        String num = lists.size() + "";
                        PrintWriter out = arg1.getWriter();
                        out.print(num);
                        out.flush();
                    }
                }
            }

            //首页收到的信息列表
            if (method.equalsIgnoreCase("myReceiveMsg")) {
                String receiver = CommUser.getUserName();
                if (!receiver.isEmpty()) {

                    List<VMessage> lists = see
                            .query("from " + VMessage.class.getName()
                                    + " where receiver='" + receiver + "' order by sendDate desc");
                    if (null != lists && lists.size() > 0) {
                        String b = JSON.toJSONString(lists);
                        PrintWriter out = arg1.getWriter();
                        out.print(b);
                        out.flush();
                    }
                }
            }

            //欠费列表
            if (method.equalsIgnoreCase("oweList")) {

                List<ChargeArrearViewEntity> charges = chargeArrearViewService.queryAll();
                String b = JSON.toJSONString(charges);
                PrintWriter out = arg1.getWriter();
                out.print(b);
                out.flush();
            }

            //首页任务列表
            if (method.equalsIgnoreCase("taskList")) {

                @SuppressWarnings("rawtypes")
                List tasks = topTaskService.queryTopTaskInfoByUserId(CommUser.getUserName());
                String b = JSON.toJSONString(tasks);
                PrintWriter out = arg1.getWriter();
                out.print(b);
                out.flush();
            }

            if (method.equalsIgnoreCase("inputFile")) {
                arg1.setContentType("application/json");
                String folder = null == arg0.getParameter("folder") ? "" : arg0.getParameter("folder").toString();

                String result = "{\"status\":\"0\",\"data\":\"上传失败\"}";
                try {
                    String paths = inputFile(arg0, arg1, folder);
                    if (paths != null) {
                        result = "{\"status\":\"1\",\"data\":\"" + paths + "\"}";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String b = JSON.toJSONString(result);
                PrintWriter out = arg1.getWriter();
                out.print(b);
                out.flush();
            }

            if (method.equalsIgnoreCase("updateUserHeadImg")) {
                String userName = null == arg0.getParameter("userName") ? "" : arg0.getParameter("userName").toString();
                String logoPath = null == arg0.getParameter("logoPath") ? "" : arg0.getParameter("logoPath").toString();
                logoPath = Configure.getString("serverPath") + "/fileUpload.app?method=download&filepath=" + logoPath;
                DefaultUser dUser = userService.getUserById(userName);
                String result = "failed";
                try {
                    if (dUser != null) {
                        String address = dUser.getAddress();
                        if (!address.isEmpty() && address != null) {
                            String filepath = address.substring(address.lastIndexOf("=") + 1, address.length());
                            File file = new File(filepath);
                            if (file.isFile() && file.exists()) {
                                file.delete();
                            }

                        }
                        dUser.setAddress(logoPath);
                        userService.saveUser(dUser);
                        result = "success";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String b = JSON.toJSONString(result);
                PrintWriter out = arg1.getWriter();
                out.print(b);
                out.flush();
            }

            if (method.equalsIgnoreCase("exit")) {
                IUser user = ContextHolder.getLoginUser();
                user.getCname();
                SystemLog log = new SystemLog();
                log.setUserCname(user.getCname());
                log.setUserName(user.getUsername());
                log.setUserPhone(user.getMobile());
                List<IDept> depts = user.getDepts();
                String deptName = "";
                for (int i = 0; i < depts.size(); i++) {
                    if (i == 0) {
                        deptName = depts.get(i).getName();
                    } else {
                        deptName += "," + depts.get(i).getName();
                    }
                }
                List<IPosition> positions = user.getPositions();
                String posName = "";
                for (int i = 0; i < positions.size(); i++) {
                    if (i == 0) {
                        posName = positions.get(i).getName();
                    } else {
                        posName += "," + positions.get(i).getName();
                    }
                }
                log.setUserDept(deptName);
                log.setUserPos(posName);
                log.setUserOperation("退出系统");
                log.setCreateTime(new Date());
                logDao.createSystemLog(log);
            }

            if (method.equalsIgnoreCase("fileUpload")) {
                arg1.setContentType("application/json");
                String folder = null == arg0.getParameter("folder") ? "" : arg0.getParameter("folder").toString();

                String result = "{\"status\":\"0\",\"data\":\"上传失败\"}";
                try {
                    String paths = JSON.toJSONString(fileUpload(arg0, arg1, folder));
                    if (paths != null) {
                        result = "{\"status\":\"1\",\"data\":" + paths + "}";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String b = JSON.toJSONString(result);
                PrintWriter out = arg1.getWriter();
                out.print(b);
                out.flush();
            }
            if (method.equalsIgnoreCase("fileDelete")) {

                arg1.setContentType("application/json");
                String filePath = null == arg0.getParameter("filePath") ? "" : arg0.getParameter("filePath").toString();

                String result = "{\"status\":\"0\",\"data\":\"删除失败\"}";
                try {
                    Boolean deleteFlag = this.deleteServerFile(filePath);
                    if (deleteFlag) {
                        result = "{\"status\":\"1\",\"data\":\"删除成功\"}";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String b = JSON.toJSONString(result);
                PrintWriter out = arg1.getWriter();
                out.print(b);
                out.flush();
            }

            arg0.setAttribute("loginUser", user);

        } catch (Exception e) {

            e.printStackTrace();
        }


    }

    @Override
    public String getUrl() {
        return "/mainFrame/mainFrameInfo";
    }

    @Override
    public boolean isDisabled() {
        return false;
    }

    public String inputFile(HttpServletRequest arg0, HttpServletResponse arg1, String folder) throws IOException, ServletException {

        //得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = "D:/upload/" + folder;
        String paths = "";
        File file = new File(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            //创建目录
            file.mkdirs();
        }

        try {
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //3、判断提交上来的数据是否是上传表单的数据
            if (!ServletFileUpload.isMultipartContent(arg0)) {
                //按照传统方式获取数据
                return null;
            }
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(arg0);

            for (FileItem item : list) {
                //如果fileitem中封装的是普通输入项的数据
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");
                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                } else {//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    String ext = filename.substring(filename.lastIndexOf(".") + 1);
                    String uuid = CommUtil.getGuuid();
                    if (paths.isEmpty()) {
                        paths = savePath + "/" + uuid + "." + ext;
                    } else {
                        paths += " ;" + savePath + "/" + uuid + "." + ext;
                    }
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //创建一个文件输出流
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + uuid + "." + ext);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while ((len = in.read(buffer)) > 0) {
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                    if (folder.equals("wg_user")) {

                        ImgCompress imgCom = new ImgCompress(savePath + "/" + uuid + "." + ext);
                        imgCom.resizeFix(600, 600);
                    }
                    //return "true";
                }
            }
            return paths;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<?> fileUpload(HttpServletRequest arg0, HttpServletResponse arg1, String folder) throws IOException, ServletException {

        //得到上传文件的保存目录，将上传的文件存放于D:/upload/"folder"目录下.
        String savePath = "D:/upload/" + folder;
        File file = new File(savePath);
        //判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            //创建目录
            file.mkdirs();
        }

        try {
            //使用Apache文件上传组件处理文件上传步骤：
            //1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            //3、判断提交上来的数据是否是上传表单的数据
            if (!ServletFileUpload.isMultipartContent(arg0)) {
                //按照传统方式获取数据
                return null;
            }
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(arg0);
            List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();

            for (FileItem item : list) {
                String paths = "";
                Map<String, Object> fileValue = new HashMap<String, Object>();
                //如果fileitem中封装的是普通输入项的数据
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    String value = item.getString("UTF-8");

                } else {//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    long fileSize = item.getSize();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    String ext = filename.substring(filename.lastIndexOf(".") + 1);
                    String uuid = CommUtil.getGuuid();
//					if(paths.isEmpty()){
//						paths = savePath+"/"+uuid+"."+ext;
//					}else{
//						paths += " ;"+savePath+"/"+uuid+"."+ext;
//					}
                    paths = savePath + "/" + uuid + "." + ext;

                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);

                    fileValue.put("caption", filename.trim());
                    fileValue.put("size", fileSize);
                    fileValue.put("type", ext.trim());
                    fileValue.put("key", paths.trim());


                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //创建一个文件输出流
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + uuid + "." + ext);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while ((len = in.read(buffer)) > 0) {
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件

                }
                fileList.add(fileValue);
            }
            return fileList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //删除服务器上的问题
    public static boolean deleteServerFile(String filePath) {
        boolean deleteFlag = false;
        File file = new File(filePath);
        if (file.exists() && file.isFile() && file.delete())
            deleteFlag = true;
        else
            deleteFlag = false;
        return deleteFlag;
    }
}
