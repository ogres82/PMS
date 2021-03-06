package com.jdry.pms.msgpublic.controller;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.bstek.bdf2.core.context.ContextHolder;
import com.bstek.bdf2.core.controller.IController;
import com.bstek.bdf2.core.model.DefaultPosition;
import com.bstek.bdf2.core.model.DefaultUser;
import com.bstek.dorado.data.provider.Page;
import com.jdry.pms.comm.util.CommUtil;
import com.jdry.pms.comm.util.DateUtil;
import com.jdry.pms.contract.pojo.ContractAttachRela;
import com.jdry.pms.contract.pojo.ContractAttachment;
import com.jdry.pms.contract.service.ContractInfoService;
import com.jdry.pms.environment.controller.FileUpload;
import com.jdry.pms.msgandnotice.service.PositionService;
import com.jdry.pms.msgpublic.pojo.MsgPubAuditinfo;
import com.jdry.pms.msgpublic.pojo.MsgPubMain;
import com.jdry.pms.msgpublic.service.MsgPubService;
@Repository
@Component
public class MsgPubController implements IController{
	@Resource
	ContractInfoService service;
	@Resource
    public MsgPubService msgPubService;
	@Resource
    public FileUpload fileUploader;
	@Resource
	PositionService positionService;
	@Override
	public boolean anonymousAccess() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void execute(HttpServletRequest arg0, HttpServletResponse arg1)
			throws IOException, ServletException {
		String method=arg0.getParameter("method");
		String ntcId=null == arg0.getParameter("ntcId")?"":arg0.getParameter("ntcId").toString();
		try {
			DefaultUser user=(DefaultUser) ContextHolder.getLoginUser();
			if(method.equalsIgnoreCase("listAjax")){
				int currentPage = arg0.getParameter("offset") == null ? 1 : Integer.parseInt(arg0.getParameter("offset"));
				  // 每页行数
				  int showCount = arg0.getParameter("limit") == null ? 10 : Integer.parseInt(arg0.getParameter("limit"));
				  if (currentPage != 0) {// 获取页数
				   currentPage = currentPage / showCount;
				  }
				  currentPage += 1;
				Page<MsgPubMain> page =new Page<MsgPubMain>(showCount, currentPage);
				msgPubService.getAllNotice(page, null, null);
				List<MsgPubMain> result=(List<MsgPubMain>) page.getEntities();
				String jsonString = JSON.toJSONString(result);
				JSONArray jsonArr = JSON.parseArray(jsonString); 
			    //int TotalCount=SqlADO.getTradeRowsCount(sql);
			    JSONObject jsonObject=new JSONObject();
			    jsonObject.put("rows", jsonArr);//JSONArray
			    jsonObject.put("total",page.getEntityCount());//总记录数
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(JSON.toJSONString(jsonObject));
				//out.flush();
				//arg0.setAttribute("noticeList", result);
			}
			//获取审核人
			if(method.equalsIgnoreCase("getAuditor")){
				List<DefaultPosition> positions=(List<DefaultPosition>) positionService.findPosition();
				String jsonString = JSON.toJSONString(positions);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			if(method.equalsIgnoreCase("save")){
				String ntcContent=arg0.getParameter("ntcContent");
				String ntcCreatorId=arg0.getParameter("ntcCreatorId");
				String ntcSubject=arg0.getParameter("ntcSubject");
				String ntcCreatorName=arg0.getParameter("ntcCreatorName");
				String ntcCreateTimeStr=arg0.getParameter("ntcCreateTime");
				String ntcAudit=arg0.getParameter("ntcAudit");
				String ntcPicPath=arg0.getParameter("ntcPicPath");
				String fileIds= null == arg0.getParameter("fileIds")?"":arg0.getParameter("fileIds").toString();
				Date ntcCreateTime =DateUtil.convertStringToDate(ntcCreateTimeStr, "yyyy-MM-dd HH:mm:ss");
				MsgPubMain notice=new MsgPubMain();
				notice.setNtcContent(ntcContent);
				notice.setNtcCreateTime(ntcCreateTime);
				notice.setNtcCreator(ntcCreatorName);
				notice.setNtcCreatorId(ntcCreatorId);
				notice.setNtcStatus("20");
				notice.setNtcSubject(ntcSubject);
				notice.setNtcAuditor(ntcAudit);
				List<MsgPubMain> saveList=new ArrayList<MsgPubMain>();
				saveList.add(notice);
				ntcId=msgPubService.saveNotice(saveList,ntcPicPath);
				if(!fileIds.isEmpty()){
					
					String[] ids = fileIds.split(";");
					for(int i=0;i<ids.length;i++){
						
						ContractAttachRela rela = new ContractAttachRela();
						rela.setContractId(ntcId);
						rela.setFileId(ids[i]);
						
						service.saveContractAttachRela(rela);
					}
				}
			}//保存summernote上传的图片
			if(method.equalsIgnoreCase("uploadImg")){
				String savePath = arg0.getSession().getServletContext()
	                    .getRealPath("/");
				String fileName=savePath+processRequest(arg0);
				fileName=fileName.replace("\\", "/");
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				String jsonString="{\"fileName\":\""+fileName+"\"}";
				out.println(jsonString);
				out.flush();
			}
			if(method.equalsIgnoreCase("viewForAjax")){
				String id =arg0.getParameter("noticeId");
				MsgPubMain notice=msgPubService.getNoticeById(id);
				String jsonString = JSON.toJSONString(notice);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
				arg0.setAttribute("notice", notice);
			}
			//获取审核信息
			if(method.equalsIgnoreCase("viewForAuditInfo")){
				String id =arg0.getParameter("noticeId");
				MsgPubMain notice=msgPubService.getNoticeById(id);
				List<MsgPubAuditinfo> auditInfos=(List<MsgPubAuditinfo>) notice.getMsgandnoticeNoticeAuditinfos();
				List<MsgPubAuditinfo> auditInfoRtn=new ArrayList<MsgPubAuditinfo>();
				for (MsgPubAuditinfo auc:auditInfos){
					MsgPubAuditinfo au=new MsgPubAuditinfo();
					au.setNtcAuditContnt(auc.getNtcAuditContnt());
					au.setNtcAuditor(auc.getNtcAuditor());
					au.setNtcCreateTime(auc.getNtcCreateTime());
					auditInfoRtn.add(au);
				}
				String jsonString = JSON.toJSONString(auditInfoRtn,SerializerFeature.DisableCircularReferenceDetect);
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
				arg0.setAttribute("notice", notice);
			}
			if(method.equalsIgnoreCase("update")){
				ntcId=arg0.getParameter("ntcId");
				String fileIds= null == arg0.getParameter("fileIds")?"":arg0.getParameter("fileIds").toString();
				String ntcContent=arg0.getParameter("ntcContent");
				String ntcSubject=arg0.getParameter("ntcSubject");
				MsgPubMain notice=msgPubService.getNoticeById(ntcId);
				notice.setNtcContent(ntcContent);
				notice.setNtcSubject(ntcSubject);
				List<MsgPubMain> updateList=new ArrayList<MsgPubMain>();
				updateList.add(notice);
				msgPubService.updateNotice(updateList);
                if(!fileIds.isEmpty()){
					String[] ids = fileIds.split(";");
					for(int i=0;i<ids.length;i++){
						ContractAttachRela rela = new ContractAttachRela();
						rela.setContractId(ntcId);
						rela.setFileId(ids[i]);
						service.saveContractAttachRela(rela);
					}
				}
			}if(method.equalsIgnoreCase("deleteByAjax")){
				arg1.setCharacterEncoding("utf-8");
				arg1.setContentType("application/json;charset=utf-8");
				arg1.setHeader("pragma","no-cache");
				arg1.setHeader("cache-control","no-cache");
				System.out.println("来delete方法了");
				String deleteIds=arg0.getParameter("deleteId");
				PrintWriter out = arg1.getWriter();
				try{
					
					if(!StringUtil.isEmpty(deleteIds)){
						JSONArray jsonArr = JSON.parseArray(deleteIds);
						for (int i=0;i<jsonArr.size();i++){
							msgPubService.deleteNoticeById((String) jsonArr.get(i));
						}
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
			}else if(method.equalsIgnoreCase("upload")){
				    arg1.setCharacterEncoding("utf-8");
				    arg1.setContentType("application/json;charset=utf-8");
				    arg1.setHeader("pragma","no-cache");
				    arg1.setHeader("cache-control","no-cache");
					String savePath = "D:/upload/msgpub";
					File file = new File(savePath);
					//判断上传文件的保存目录是否存在
					if (!file.exists() && !file.isDirectory()) {
						System.out.println(savePath+"目录不存在，需要创建");
						//创建目录
						file.mkdirs();
					}
					//消息提示
					String message = "";
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
							return;
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
								//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
								//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
								filename = filename.substring(filename.lastIndexOf("\\")+1);
								//获取item中的上传文件的输入流
								InputStream in = item.getInputStream();
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
								String path = savePath + "/" + filename;
								
								String fileId = saveUploadFile(filename,path,ntcId);
								
								message = fileId+":"+filename;
								
							}
						}
					}catch (Exception e) {
						message= "文件上传失败！";
						e.printStackTrace();
					}finally{
						PrintWriter pr = arg1.getWriter();
						pr.println(message);
						pr.flush();
					}
			}
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(method.equals("list")){
			arg0.getRequestDispatcher("/msgandnotice/NoticePub.jsp").forward(arg0,arg1);
		}
		if(method.equals("save")){
			arg0.getRequestDispatcher("/msgandnotice/NoticePub.jsp").forward(arg0,arg1);
		}
		if(method.equals("edit")){
			arg0.getRequestDispatcher("/msgandnotice/NoticePubEdit.jsp").forward(arg0,arg1);
		}
		if(method.equals("delete")){
			arg0.getRequestDispatcher("/msgandnotice/NoticePub.jsp").forward(arg0,arg1);
		}
		if(method.equals("view")){
			arg0.getRequestDispatcher("/msgandnotice/NoticePubView.jsp").forward(arg0,arg1);
		}
		if(method.equals("update")){
			arg0.getRequestDispatcher("/msgandnotice/NoticePub.jsp").forward(arg0,arg1);
		}
	}
	protected String processRequest(HttpServletRequest request)
	        throws ServletException, IOException {
	    String savePath = request.getSession().getServletContext()
                .getRealPath("/")
                + "/upload/";
        String id=CommUtil.getGuuid();
	    File folder = new File(savePath);
        if(!(folder.exists()&& folder.isDirectory())){
              folder.mkdirs();
        }
        String rtnFileName="";
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8"); 
		if(!ServletFileUpload.isMultipartContent(request)){
			return null;
		}
	    OutputStream out = null;
	    InputStream filecontent = null;
	    try {
	    	List<FileItem> list = upload.parseRequest(request);
	    	if(list.size()>0){
	    		FileItem item = list.get(0);
				//如果fileitem中封装的是普通输入项的数据
				if(item.isFormField()){
					String name = item.getFieldName();
					//解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					//value = new String(value.getBytes("iso8859-1"),"UTF-8");
					System.out.println(name + "=" + value);
				}else{//如果fileitem中封装的是上传文件
					//得到上传的文件名称，
					String fileName = item.getName();
					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
					//获取item中的上传文件的输入流
					filecontent = item.getInputStream();
					//创建一个文件输出流
					 out = new FileOutputStream(savePath+id+"."+ext);
					//创建一个缓冲区
					byte buffer[] = new byte[1024];
					//判断输入流中的数据是否已经读完的标识
					int len = 0;
					//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while((len=filecontent.read(buffer))>0){
						//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						out.write(buffer, 0, len);
					}
					//关闭输入流
					filecontent.close();
					//关闭输出流
					out.close();
					//删除处理文件上传时生成的临时文件
					item.delete();
			        rtnFileName=folder.getName()+"/"+id+"."+ext;
				}
	    	}
	    	
	        //writer.println("New file " + fileName + " created at " + savePath);
	    } catch (FileNotFoundException fne) {
	       fne.printStackTrace();
	    } catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	    }
	    return rtnFileName;
	}
	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	//上传文件
		public String saveUploadFile(String filename,String path,String contractId){
			ContractAttachment atta = new ContractAttachment();
			atta.setFileName(filename);
			atta.setFilePath(path);
			atta.setFileUploadTime(new Date());
			
			String fileId = service.saveContractAttachment(atta);//保存文件信息
			return fileId;
		}
		
		//下载文件
		public void downloadFile(String path, HttpServletResponse response){
			try {
	            // path是指欲下载的文件的路径。
	            File file = new File(path);
	            // 取得文件名。
	            String filename = file.getName();
	            // 取得文件的后缀名。
	           // String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

	            // 以流的形式下载文件。
	            InputStream fis = new BufferedInputStream(new FileInputStream(path));
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
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
			
	    }
	@Override
	public String getUrl() {
		return "/msgpublic/list";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}
}