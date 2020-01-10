package com.jdry.pms.system.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.bdf2.core.model.Role;
import com.bstek.bdf2.core.model.RoleMember;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.service.BusinessService;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.msgandnotice.util.CharacterUtil;
import com.jdry.pms.system.pojo.VRoleBtn;
import com.jdry.pms.system.service.RoleMantainService;
import com.jdry.pms.system.service.URLBtnService;

@Repository
@Component
public class RoleMaintainController  implements IController{


	@Resource
	private RoleMantainService service;
	
	@Resource
	private URLBtnService urlBtnService;
	
	@Resource
	private BusinessService businessService;
	
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
		String method=arg0.getParameter("method");
		String roleId=null == arg0.getParameter("roleId")?"":arg0.getParameter("roleId").toString();//员工工号

		try {
			
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(method.equalsIgnoreCase("list")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));// 每页行数			  
			    int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
			    String search = arg0.getParameter("search") == null ? "" : arg0.getParameter("search");
			    search = CharacterUtil.getUTF_8String(search);
			    if (currentPage != 0) {// 获取页数
			    	currentPage = currentPage / showCount;
			    }
			    currentPage += 1;
			    Map<String, Object> parameter = new HashMap<String, Object>();
			    parameter.put("search", search);
			    Page<Role> page =new Page<Role>(showCount, currentPage);
			    service.queryRoles(page, parameter,"normal");
			    List<Role> result=(List<Role>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("editRole") || method.equals("viewRole")){
				
				arg1.setContentType("application/json;charset=utf-8");
				Role role = new Role();
				role = service.getRoleById(roleId);
				String jsonString = JSON.toJSONString(role);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("save")){
				String name= null == arg0.getParameter("name")?"":arg0.getParameter("name").toString();
				String desc= null == arg0.getParameter("desc")?"":arg0.getParameter("desc").toString();
				
				Role role = new Role();
				if(roleId !=null && !roleId.isEmpty()){
					role = service.getRoleById(roleId);
				}else{
					role.setId(UUID.randomUUID().toString());
				}
				role.setName(name);
				role.setDesc(desc);
				role.setType("normal");
				
				service.addRole(role);
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
				
			}
			
			if(method.equalsIgnoreCase("deleteRole")){

				PrintWriter out = arg1.getWriter();
				try{
					if(!StringUtil.isEmpty(roleId)){
						service.deleteRole(roleId);
					}
					String jsonString = JSON.toJSONString("删除成功！");
					out.println(jsonString);
					out.flush();

				}catch(Exception e){
					e.printStackTrace();
					String jsonString = JSON.toJSONString("删除失败！");
					out.println(jsonString);
					out.flush();
				}
			
			}
			
			if(method.equalsIgnoreCase("initPermissions")){
				String pathName= null == arg0.getParameter("pathName")?"":arg0.getParameter("pathName").toString();
				RoleMember role = businessService.getRoleMemberByUsername(CommUser.getUserName());
				if(role!=null){
					String projectName = arg0.getContextPath();
					pathName = pathName.substring(projectName.length()+1);
					System.out.println(pathName);
					List<VRoleBtn> roleBtn = urlBtnService.getAuthBtnByRole(role.getRoleId(), pathName);
					String jsonString = JSON.toJSONString(roleBtn);
					arg1.setCharacterEncoding("utf-8");
					arg1.setContentType("application/json;charset=utf-8");
					arg1.setHeader("pragma","no-cache");
					arg1.setHeader("cache-control","no-cache");
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(jsonString);
					out.flush();
				}
				
			}
			//用户维护界面使用
			if(method.equalsIgnoreCase("getlist")){
			    List<Role> lists = service.getRoles(0);			    
				String jsonString = JSON.toJSONString(lists);				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public String getUrl() {
		return "/role/roleMaintain";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}
	
public String inputFile(HttpServletRequest arg0, HttpServletResponse arg1,String folder) throws IOException, ServletException{
		
		//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = "D:/upload/emp/"+folder;
		String paths = "";
		File file = new File(savePath);
		//判断上传文件的保存目录是否存在
		if (!file.exists() && !file.isDirectory()) {
			System.out.println(savePath+"目录不存在，需要创建");
			//创建目录
			file.mkdirs();
		}
		
		try{
			//使用Apache文件上传组件处理文件上传步骤：
			//1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8"); 
			//3、判断提交上来的数据是否是上传表单的数据
			if(!ServletFileUpload.isMultipartContent(arg0)){
				//按照传统方式获取数据
				return null;
			}
			//4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(arg0);
			
			for(FileItem item : list){
				//如果fileitem中封装的是普通输入项的数据
				if(item.isFormField()){
					String name = item.getFieldName();
					//解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					//value = new String(value.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name + "=" + value);
				}else{//如果fileitem中封装的是上传文件
					//得到上传的文件名称，
					String filename = item.getName();
					String ext = filename.substring(filename.lastIndexOf(".") + 1);
			 	    Date d = new Date();
			 	    String fileType = Long.toString(d.getTime()) + "." + ext;
					System.out.println(filename);
					if(filename==null || filename.trim().equals("")){
						continue;
					}
					
					if(paths.isEmpty()){
						paths = savePath+"/"+fileType;
					}else{
						paths += " ;"+savePath+"/"+fileType;
					}
					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\")+1);
					//获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					//创建一个文件输出流
					//创建一个文件输出流
					FileOutputStream out = new FileOutputStream(savePath + "\\" + fileType);
					//创建一个缓冲区
					byte buffer[] = new byte[1024];
					//判断输入流中的数据是否已经读完的标识
					int len = 0;
					//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while((len=in.read(buffer))>0){
						//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						out.write(buffer, 0, len);
					}
					//关闭输入流
					in.close();
					//关闭输出流
					out.close();
					//删除处理文件上传时生成的临时文件
					item.delete();
					//return "true";
				}
			}
			return paths;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void downloadFile(HttpServletRequest request, HttpServletResponse response,String filepath){
		// path是指欲下载的文件的路径。
		try {
			File file = new File(filepath);
			String filename = file.getName();
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(filepath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"), "iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
