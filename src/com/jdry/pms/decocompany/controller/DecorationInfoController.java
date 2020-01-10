package com.jdry.pms.decocompany.controller;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

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
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.CommUser;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.comm.util.ImgCompress;
import com.jdry.pms.decocompany.pojo.ShopsGoods;
import com.jdry.pms.decocompany.pojo.ShopsGoodsImg;
import com.jdry.pms.decocompany.pojo.ShopsInfo;
import com.jdry.pms.decocompany.pojo.VShopsInfo;
import com.jdry.pms.decocompany.service.DecorationInfoService;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class DecorationInfoController implements IController{
	
	@Resource
	private DecorationInfoService service;
	
	@Override
	public boolean anonymousAccess() {
		return true;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {

		arg0.setCharacterEncoding("utf-8");
		arg1.setCharacterEncoding("utf-8");
		arg1.setContentType("text/html; charset=utf-8");
		String method=arg0.getParameter("method");
		String id=null == arg0.getParameter("id")?"":arg0.getParameter("id").toString();//员工工号

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
			    Page<VShopsInfo> page =new Page<VShopsInfo>(showCount, currentPage);
			    service.queryVDecorationInfoByParam(page, parameter);
			    List<VShopsInfo> result=(List<VShopsInfo>) page.getEntities();			
				String b = JSON.toJSONString (result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("editDecInfo") || method.equals("viewDecInfo")){
				
				arg1.setContentType("application/json;charset=utf-8");
				ShopsInfo decInfo = new ShopsInfo();
				decInfo = service.getDecorationInfoById(id);
				String jsonString = JSON.toJSONString(decInfo);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			
			if(method.equalsIgnoreCase("getDecorationInfoById")){
				
				arg1.setContentType("application/json;charset=utf-8");
				ShopsInfo decInfo = service.getDecorationInfoById(id);
				String name = "全部";
				if(decInfo!=null){
					name = decInfo.getName();
				}
				String jsonString = JSON.toJSONString(name);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}

			if(method.equals("save")){
				String name= null == arg0.getParameter("name")?"":arg0.getParameter("name").toString();
				String summary= null == arg0.getParameter("summary")?"":arg0.getParameter("summary").toString();
				String telephone= null == arg0.getParameter("telephone")?"":arg0.getParameter("telephone").toString();
				String logoPath= null == arg0.getParameter("logoPath")?"":arg0.getParameter("logoPath").toString();
				String qualifyId= null == arg0.getParameter("qualifyId")?"":arg0.getParameter("qualifyId").toString();
				String[] urls = qualifyId.split(";");
				ShopsInfo decInfo = new ShopsInfo();
				String uuid = CommUtil.getGuuid();
				String downloadUrl = "";
				if(id!=null && !id.isEmpty()){
					decInfo.setId(id);
					downloadUrl = "/decorate/decoCompany.app?method=loadDecCompanyLogo&id="+id;
				}else{
					decInfo.setId(uuid);
					downloadUrl = "/decorate/decoCompany.app?method=loadDecCompanyLogo&id="+uuid;
				}
				decInfo.setName(name);
				decInfo.setSummary(summary);
				decInfo.setTelephone(telephone);
				decInfo.setEditorId(CommUser.getUserName());
				decInfo.setEditorName(ContextHolder.getLoginUser().getCname());
				decInfo.setLogoPath(logoPath);
			    
				decInfo.setLogoUrl(downloadUrl);
				
				String savedId = service.addDecorationInfo(decInfo);
				
				for(int i =0;i<urls.length;i++){
					
					ShopsGoodsImg qualify = new ShopsGoodsImg();
					String qid = CommUtil.getGuuid();
					qualify.setId(qid);
					qualify.setImgPath(urls[i]);
					String imgPath = "/decorate/decoCompany.app?method=loadQualifyImg&imgId="+qid;
					qualify.setImgUrl(imgPath);
					
					service.saveDecorationQualify(qualify);
				}
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.print(JSON.toJSONString("succese"));
				out.flush();
				
			}
			
			if(method.equalsIgnoreCase("deleteDecInfo")){

				PrintWriter out = arg1.getWriter();
				try{
					
					if(!StringUtil.isEmpty(id)){
						service.deleteDecorationInfo(id);
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
			
			if(method.equalsIgnoreCase("checkerInfo")){
				List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
				List<Object> owner =  service.getCheckerInfo(id);
				for(int i=0;i<owner.size();i++){
					Map<String,Object> map = new HashMap<String,Object>();
					Object[] arr = (Object[])owner.get(i);
					map.put("ownerName", arr[0]);
					map.put("phone", arr[1]);
					map.put("num", arr[2]);
					result.add(map);
				}
				JSONArray jsonArray = JSONArray.fromObject(result);
				String jsonString = jsonArray.toString();
				PrintWriter out = arg1.getWriter();
				out.print(jsonString);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("consultorInfo")){
				List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
				List<Object> owner = service.getConsultorInfo(id);
				for(int i=0;i<owner.size();i++){
					Map<String,Object> map = new HashMap<String,Object>();
					Object[] arr = (Object[])owner.get(i);
					map.put("ownerName", arr[0]);
					map.put("phone", arr[1]);
					map.put("num", arr[2]);
					result.add(map);
				}
				JSONArray jsonArray = JSONArray.fromObject(result);
				String jsonString = jsonArray.toString();
				PrintWriter out = arg1.getWriter();
				out.print(jsonString);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("inputFile")){
				arg1.setContentType("application/json"); 
				String folder =  null == arg0.getParameter("folder")?"":arg0.getParameter("folder").toString();

				String result = "{\"status\":\"0\",\"data\":\"上传失败\"}";
				try{
					String paths = inputFile(arg0,arg1,folder);
					if(paths!=null){
						result = "{\"status\":\"1\",\"data\":\""+paths+"\"}";
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				String b = JSON.toJSONString(result);				
				PrintWriter out = arg1.getWriter();
				out.print(b);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("saveImg")){
				String imgType =  null == arg0.getParameter("imgType")?"":arg0.getParameter("imgType").toString();
				String imgSummary =  null == arg0.getParameter("imgSummary")?"":arg0.getParameter("imgSummary").toString();
				String imgUrl =  null == arg0.getParameter("imgUrl")?"":arg0.getParameter("imgUrl").toString();
				String result = "";
				
				ShopsGoods img = new ShopsGoods();
				String imgId = CommUtil.getGuuid();
				img.setId(imgId);
				img.setCompanyId(id);
				String imgPath = "/decorate/decoCompany.app?method=loadImg&imgId="+imgId;
				
				service.saveDecorationImg(img);
				String b = JSON.toJSONString(result);				
				PrintWriter out = arg1.getWriter();
				out.print(b);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("loadImg")){
				String imgId =  null == arg0.getParameter("imgId")?"":arg0.getParameter("imgId").toString();
				
				ShopsGoods img = service.getDecorationImgById(imgId);
				if(img!=null){
					this.downloadFile(arg0, arg1,img.getCompanyId());
				}else{
					System.out.println("图片不存在");
				}
			}
			
			if(method.equalsIgnoreCase("getImgs")){
				
				List<ShopsGoodsImg> imgs = service.getImgByCompanyId(id);
				
				String b = JSON.toJSONString(imgs);				
				PrintWriter out = arg1.getWriter();
				out.print(b);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("delImg")){
				String imgId =  null == arg0.getParameter("imgId")?"":arg0.getParameter("imgId").toString();
				ShopsGoods img = service.getDecorationImgById(imgId);
				if(img!=null){
					service.deleteImg(img);
					deleteFile("");
				}
				String b = JSON.toJSONString("");				
				PrintWriter out = arg1.getWriter();
				out.print(b);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("getQualify")){
				List<ShopsGoodsImg> qualify = service.getQualifyByCompanyId(id);
				
				String b = JSON.toJSONString(qualify);				
				PrintWriter out = arg1.getWriter();
				out.print(b);
				out.flush();
			}
			
			if(method.equalsIgnoreCase("loadQualifyImg")){
				String imgId =  null == arg0.getParameter("imgId")?"":arg0.getParameter("imgId").toString();
				
				ShopsGoodsImg img = service.getQualifyById(imgId);
				if(img!=null){
					this.downloadFile(arg0, arg1,img.getImgPath());
				}
			}
			
			if(method.equalsIgnoreCase("loadDecCompanyLogo")){
				
				ShopsInfo img = service.getDecorationInfoById(id);
				if(img!=null){
					this.downloadFile(arg0, arg1,img.getLogoPath());
				}
			}
			
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}

	@Override
	public String getUrl() {
		return "/decorate/decoCompany";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}
	
	public String inputFile(HttpServletRequest arg0, HttpServletResponse arg1,String folder) throws IOException, ServletException{
		
		//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String savePath = "D:/upload/decocompany/"+folder+"/"+sdf.format(date);
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
					System.out.println(filename);
					if(filename==null || filename.trim().equals("")){
						continue;
					}
					
					if(paths.isEmpty()){
						paths = savePath+"/"+filename;
					}else{
						paths += " ;"+savePath+"/"+filename;
					}
					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\")+1);
					//获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					//创建一个文件输出流
					//创建一个文件输出流
					FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
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
					if(!folder.equals("logo")){
						
						ImgCompress imgCom = new ImgCompress(savePath + "/" + filename);
						imgCom.resizeFix(600, 600);
					}
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
	
	//删除文件
	public void deleteFile(String filepath) throws IOException{
		
		try {
			File file = new File(filepath);
			if (file.isFile() && file.exists())
			{
				file.delete();
			}
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
