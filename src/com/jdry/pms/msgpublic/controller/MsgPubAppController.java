package com.jdry.pms.msgpublic.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
import com.jdry.pms.basicInfo.service.PropertyManagerService;
import com.jdry.pms.contract.pojo.ContractAttachRela;
import com.jdry.pms.contract.pojo.ContractAttachment;
import com.jdry.pms.contract.pojo.ContractInfo;
import com.jdry.pms.contract.pojo.VContractAttachment;
import com.jdry.pms.contract.pojo.VContractInfo;
import com.jdry.pms.contract.service.ContractInfoService;
import com.jdry.pms.dir.pojo.DirDirectoryDetail;
import com.jdry.pms.msgandnotice.util.CharacterUtil;

@Repository
@Component
public class MsgPubAppController implements IController{
	
	@Resource
	ContractInfoService service;
	@Resource
	PropertyManagerService propertyManagerService;

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
		String contractId=null == arg0.getParameter("contractId")?"":arg0.getParameter("contractId").toString();

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
			    Page<VContractInfo> page =new Page<VContractInfo>(showCount, currentPage);
			    service.queryContractByParam(page, parameter, null);
			    List<VContractInfo> result=(List<VContractInfo>) page.getEntities();			
				String b = JSON.toJSONString(result);				
				long count = page.getEntityCount();				//获取总记录数
				PrintWriter out = arg1.getWriter();
				String r = "{\"total\":"+count+",\"rows\":"+b+"}"; //服务端分页必须返回total和rows,rows为每页记录
				out.print(r);
				out.flush();
			}
			if(method.equalsIgnoreCase("editContract") || method.equals("viewContract")){
				
				arg1.setContentType("application/json;charset=utf-8");
				ContractInfo contract = new ContractInfo();
				contract = service.getContractById(contractId);
				String jsonString = JSON.toJSONString(contract);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			
			}
			if(method.equals("save")){
				SimpleDateFormat sdf  =   new SimpleDateFormat("yyyy-MM-dd");
				String contractCode= null == arg0.getParameter("contractCode")?"":arg0.getParameter("contractCode").toString();
				String contractName= null == arg0.getParameter("contractName")?"":arg0.getParameter("contractName").toString();
				String contractType= null == arg0.getParameter("contractType")?"":arg0.getParameter("contractType").toString();
				String contractDetail= null == arg0.getParameter("contractDetail")?"":arg0.getParameter("contractDetail").toString();
				String contractPbName= null == arg0.getParameter("contractPbName")?"":arg0.getParameter("contractPbName").toString();
				String contractPbLinker= null == arg0.getParameter("contractPbLinker")?"":arg0.getParameter("contractPbLinker").toString();
				String contractPbPhone= null == arg0.getParameter("contractPbPhone")?"":arg0.getParameter("contractPbPhone").toString();
				
				String contractSignDate= null == arg0.getParameter("contractSignDate")?"":arg0.getParameter("contractSignDate").toString();
				String contractEndDate= null == arg0.getParameter("contractEndDate")?"":arg0.getParameter("contractEndDate").toString();
				String contractPeriod= null == arg0.getParameter("contractPeriod")?"":arg0.getParameter("contractPeriod").toString();
				String contractPrice= null == arg0.getParameter("contractPrice")?"":arg0.getParameter("contractPrice").toString();
				String contractStatus= null == arg0.getParameter("contractStatus")?"":arg0.getParameter("contractStatus").toString();
				String remark= null == arg0.getParameter("remark")?"":arg0.getParameter("remark").toString();
				String fileIds= null == arg0.getParameter("fileIds")?"":arg0.getParameter("fileIds").toString();
				
				String result = "保存失败";
				
				
				ContractInfo contract = new ContractInfo();
				if(contractId !=null && !contractId.isEmpty()){
					contract.setContractId(contractId);
				}
				contract.setContractCode(contractCode);
				contract.setContractName(contractName);
				contract.setContractType(contractType);
				contract.setContractDetail(contractDetail);
				contract.setContractPbName(contractPbName);
				contract.setContractPbLinker(contractPbLinker);
				contract.setContractPbPhone(contractPbPhone);
				if(!contractPeriod.isEmpty()){
					contract.setContractPeriod(Float.parseFloat(contractPeriod));
				}
				if(!contractPrice.isEmpty()){
					contract.setContractPrice(Float.parseFloat(contractPrice));
				}
				contract.setContractStatus(contractStatus);
				contract.setRemark(remark);
				
				if(!contractSignDate.isEmpty()){
					contract.setContractSignDate(sdf.parse(contractSignDate));
				}
				if(!contractEndDate.isEmpty()){
					contract.setContractEndDate(sdf.parse(contractEndDate));
				}
				
				try{
					contractId = service.addContractInfo(contract);
					if(!fileIds.isEmpty()){
						
						String[] ids = fileIds.split(";");
						for(int i=0;i<ids.length;i++){
							
							ContractAttachRela rela = new ContractAttachRela();
							rela.setContractId(contractId);
							rela.setFileId(ids[i]);
							
							service.saveContractAttachRela(rela);
						}
					}
					result = "保存成功";
				}catch(Exception e){
					result = "保存失败";
					e.printStackTrace();
				}finally{
					
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.print(JSON.toJSONString(result));
					out.flush();
				}
				
			}
			
			if(method.equalsIgnoreCase("deleteContract")){
				
				PrintWriter out = arg1.getWriter();
				String result = "删除失败";
				try{
					
					if(!StringUtil.isEmpty(contractId)){
						service.deleteContractInfo(contractId);
						result = "删除成功";
					}

				}catch(Exception e){
					result = "删除失败";
					e.printStackTrace();
				}finally{
					String jsonString = JSON.toJSONString(result);
					out.println(jsonString);
					out.flush();
				}
			
			}
			if(method.equalsIgnoreCase("initDropAll")){
				List<DirDirectoryDetail> positions=propertyManagerService.getDirectoryLikeCode("contract_");
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
			
			if(method.equalsIgnoreCase("upload")){//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String savePath = "D:/upload/contract/"+sdf.format(date);
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
							
							String fileId = saveUploadFile(filename,path,contractId);
							
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
			
			//查找合同文件
			if(method.equalsIgnoreCase("searchContractFile")){
				arg1.setContentType("application/json;charset=utf-8");
				List<VContractAttachment> attr = new ArrayList<VContractAttachment>();
				attr = service.getContractAttachmentById(contractId);
				String jsonString = JSON.toJSONString(attr);
				
				//传输JSON
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			
			//查找合同文件
			if(method.equalsIgnoreCase("downloadAttr")){
				arg1.setContentType("application/json;charset=utf-8");
				String fileId =null == arg0.getParameter("fileId")?"":arg0.getParameter("fileId").toString();
				ContractAttachment att = service.getAttachmentByFileId(fileId);
				if(att==null){
					String result = "failed";
					String jsonString = JSON.toJSONString(result);
					
					//传输JSON
					PrintWriter out = arg1.getWriter();
					out.println(jsonString);
					out.flush();
				}else{
					this.downloadFile(att.getFilePath(), arg0,arg1);
				}
				
			}
			
			if(method.equalsIgnoreCase("delContractFile")){
				
				String fileId =null == arg0.getParameter("fileId")?"":arg0.getParameter("fileId").toString();
				String result = "failed";
				try{
					if(fileId.isEmpty()){
						result = "failed";
					}else{
						service.deleteAttachment(fileId);
						result = "success";
					}
				}catch(Exception e){
					e.printStackTrace();
				}
				//传输JSON
				String jsonString = JSON.toJSONString(result);
				PrintWriter out = arg1.getWriter();
				out.println(jsonString);
				out.flush();
			}
			
			arg0.setAttribute("loginUser", user);

		} catch (Exception e) {
			
			e.printStackTrace();
		}
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
	public void downloadFile(String path, HttpServletRequest request,HttpServletResponse response){
		
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
            try {
				request.getRequestDispatcher("/fileNotFind.jsp").forward(request,response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		
    }


	@Override
	public String getUrl() {
		return "/msgpub/attrdown";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

}
