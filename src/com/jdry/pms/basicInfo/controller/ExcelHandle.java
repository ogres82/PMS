package com.jdry.pms.basicInfo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.jdry.pms.basicInfo.dao.ExcelUtil;
import com.jdry.pms.basicInfo.pojo.VEmp;
 
/**
 * 对excel进行操作工具类
 *@author xiliang.xiao
 *@date 2015年1月8日 下午1:46:36
 *
 **/
@SuppressWarnings("rawtypes")
@Component
public class ExcelHandle {
 
	
    private Map<String,HashMap[]> tempFileMap  = new HashMap<String,HashMap[]>();
    private Map<String,Map<String,Cell>> cellMap = new HashMap<String,Map<String,Cell>>();
    private Map<String,FileInputStream> tempStream = new HashMap<String, FileInputStream>();
    private Map<String,Workbook> tempWorkbook = new HashMap<String, Workbook>();
    private Map<String,Workbook> dataWorkbook = new HashMap<String, Workbook>();
     
    /**
     * 单无格类
     * @author xiliang.xiao
     *
     */
    class Cell{
        private int column;//列
        private int line;//行
        private CellStyle cellStyle;
 
        public int getColumn() {
            return column;
        }
        public void setColumn(int column) {
            this.column = column;
        }
        public int getLine() {
            return line;
        }
        public void setLine(int line) {
            this.line = line;
        }
        public CellStyle getCellStyle() {
            return cellStyle;
        }
        public void setCellStyle(CellStyle cellStyle) {
            this.cellStyle = cellStyle;
        }
    }
     
    /**
     * 向Excel中输入相同title的多条数据
     * @param tempFilePath excel模板文件路径
     * @param cellList 需要填充的数据（模板<!%后的字符串）
     * @param dataList 填充的数据
     * @param sheet 填充的excel sheet,从0开始
     * @throws IOException 
     */
    public void writeListData(String tempFilePath,List<String> cellList,List<Map<String,Object>> dataList,int sheet) throws IOException{
        //获取模板填充格式位置等数据
        HashMap temp = getTemp(tempFilePath,sheet);
        //按模板为写入板
        Workbook temWorkbook = getTempWorkbook(tempFilePath);
        //获取数据填充开始行
        int startCell = Integer.parseInt((String)temp.get("STARTCELL"));
        //数据填充的sheet
        Sheet wsheet = temWorkbook.getSheetAt(sheet);
        //移除模板开始行数据即<!%
        wsheet.removeRow(wsheet.getRow(startCell));
        if(dataList!=null&&dataList.size()>0){
            for(Map<String,Object> map:dataList){
                for(String cell:cellList){
                    //获取对应单元格数据
                    Cell c = getCell(cell,temp,temWorkbook,tempFilePath);
                    //写入数据
                    ExcelUtil.setValue(wsheet, startCell, c.getColumn(), map.get(cell), c.getCellStyle());
                }
                startCell++;
            }
        }
    }
 
    /**
     * 按模板向Excel中相应地方填充数据
     * @param tempFilePath excel模板文件路径
     * @param cellList 需要填充的数据（模板<%后的字符串）
     * @param dataMap 填充的数据
     * @param sheet 填充的excel sheet,从0开始
     * @throws IOException 
     */
    public void writeData(String tempFilePath,List<String> cellList,Map<String,Object> dataMap,int sheet) throws IOException{
        //获取模板填充格式位置等数据
        HashMap tem = getTemp(tempFilePath,sheet);
        //按模板为写入板
        Workbook wbModule = getTempWorkbook(tempFilePath);
        //数据填充的sheet
        Sheet wsheet = wbModule.getSheetAt(sheet);
        if(dataMap!=null&&dataMap.size()>0){
            for(String cell:cellList){
                //获取对应单元格数据
                Cell c = getCell(cell,tem,wbModule,tempFilePath);
                ExcelUtil.setValue(wsheet, c.getLine(), c.getColumn(), dataMap.get(cell), c.getCellStyle());
            }
        }
    }
     
    /**
     * Excel文件读值
     * @param tempFilePath
     * @param cell
     * @param sheet
     * @return
     * @throws IOException 
     */
    public Object getValue(String tempFilePath,String cell,int sheet,File excelFile) throws IOException{
        //获取模板填充格式位置等数据
        HashMap tem = getTemp(tempFilePath,sheet);
        //模板工作区
        Workbook temWorkbook = getTempWorkbook(tempFilePath);
        //数据工作区
        Workbook dataWorkbook = getDataWorkbook(tempFilePath, excelFile);
        //获取对应单元格数据
        Cell c = getCell(cell,tem,temWorkbook,tempFilePath);
        //数据sheet
        Sheet dataSheet = dataWorkbook.getSheetAt(sheet);
        return ExcelUtil.getCellValue(dataSheet, c.getLine(), c.getColumn());
    }
     
    /**
     * 读值列表值
     * @param tempFilePath
     * @param cell
     * @param sheet
     * @return
     * @throws IOException 
     */
    public List<Map<String,Object>> getListValue(String tempFilePath,List<String> cellList,int sheet,File excelFile) throws IOException{
        List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
        //获取模板填充格式位置等数据
        HashMap tem = getTemp(tempFilePath,sheet);
        //获取数据填充开始行
        int startCell = Integer.parseInt((String)tem.get("STARTCELL"));
        //将Excel文件转换为工作区间
        Workbook dataWorkbook = getDataWorkbook(tempFilePath,excelFile) ;
        //数据sheet
        Sheet dataSheet = dataWorkbook.getSheetAt(sheet);
        //文件最后一行
        int lastLine = dataSheet.getLastRowNum();
         
        for(int i=startCell;i<=lastLine;i++){
            dataList.add(getListLineValue(i, tempFilePath, cellList, sheet, excelFile));
        }
        return dataList;
    }
     
    /**
     * 读值一行列表值
     * @param tempFilePath
     * @param cell
     * @param sheet
     * @return
     * @throws IOException 
     */
    public Map<String,Object> getListLineValue(int line,String tempFilePath,List<String> cellList,int sheet,File excelFile) throws IOException{
        Map<String,Object> lineMap = new HashMap<String, Object>();
        //获取模板填充格式位置等数据
        HashMap tem = getTemp(tempFilePath,sheet);
        //按模板为写入板
        Workbook temWorkbook = getTempWorkbook(tempFilePath);
        //将Excel文件转换为工作区间
        Workbook dataWorkbook = getDataWorkbook(tempFilePath,excelFile) ;
        //数据sheet
        Sheet dataSheet = dataWorkbook.getSheetAt(sheet);
        for(String cell:cellList){
            //获取对应单元格数据
            Cell c = getCell(cell,tem,temWorkbook,tempFilePath);
            lineMap.put(cell, ExcelUtil.getCellValue(dataSheet, line, c.getColumn()));
        }
        return lineMap;
    }
     
     
 
    /**
     * 获得模板输入流
     * @param tempFilePath 
     * @return
     * @throws FileNotFoundException 
     */
    private FileInputStream getFileInputStream(String tempFilePath) throws FileNotFoundException {
        if(!tempStream.containsKey(tempFilePath)){
            tempStream.put(tempFilePath, new FileInputStream(tempFilePath));
        }
         
        return tempStream.get(tempFilePath);
    }
 
    /**
     * 获得输入工作区
     * @param tempFilePath
     * @return
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    private Workbook getTempWorkbook(String tempFilePath) throws FileNotFoundException, IOException {
        if(!tempWorkbook.containsKey(tempFilePath)){
            if(tempFilePath.endsWith(".xlsx")){
                tempWorkbook.put(tempFilePath, new XSSFWorkbook(getFileInputStream(tempFilePath)));
            }else if(tempFilePath.endsWith(".xls")){
                tempWorkbook.put(tempFilePath, new HSSFWorkbook(getFileInputStream(tempFilePath)));
            }
        }
        return tempWorkbook.get(tempFilePath);
    }
     
    /**
     * 获取对应单元格样式等数据数据
     * @param cell
     * @param tem
     * @param wbModule 
     * @param tempFilePath
     * @return
     */
    private Cell getCell(String cell, HashMap tem, Workbook wbModule, String tempFilePath) {
        if(!cellMap.get(tempFilePath).containsKey(cell)){
            Cell c = new Cell();
             
            int[] pos = ExcelUtil.getPos(tem, cell);
            if(pos.length>1){
                c.setLine(pos[1]);
            }
            c.setColumn(pos[0]);
            c.setCellStyle((ExcelUtil.getStyle(tem, cell, wbModule)));
            cellMap.get(tempFilePath).put(cell, c);
        }
        return cellMap.get(tempFilePath).get(cell);
    }
 
    /**
     * 获取模板数据
     * @param tempFilePath 模板文件路径
     * @param sheet 
     * @return
     * @throws IOException
     */
    private HashMap getTemp(String tempFilePath, int sheet) throws IOException {
        if(!tempFileMap.containsKey(tempFilePath)){
            tempFileMap.put(tempFilePath, ExcelUtil.getTemplateFile(tempFilePath));
            cellMap.put(tempFilePath, new HashMap<String,Cell>());
        }
        return tempFileMap.get(tempFilePath)[sheet];
    }
     
    /**
     * 资源关闭
     * @param tempFilePath 模板文件路径
     * @param os 输出流
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    public void writeAndClose(String tempFilePath,OutputStream os) throws FileNotFoundException, IOException{
        if(getTempWorkbook(tempFilePath)!=null){
            getTempWorkbook(tempFilePath).write(os);
            tempWorkbook.remove(tempFilePath);
        }
        if(getFileInputStream(tempFilePath)!=null){
            getFileInputStream(tempFilePath).close();
            tempStream.remove(tempFilePath);
        }
    }
     
    /**
     * 获得读取数据工作间
     * @param tempFilePath
     * @param excelFile
     * @return
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    private Workbook getDataWorkbook(String tempFilePath, File excelFile) throws FileNotFoundException, IOException {
        if(!dataWorkbook.containsKey(tempFilePath)){
            if(tempFilePath.endsWith(".xlsx")){
                dataWorkbook.put(tempFilePath, new XSSFWorkbook(new FileInputStream(excelFile)));
            }else if(tempFilePath.endsWith(".xls")){
                dataWorkbook.put(tempFilePath, new HSSFWorkbook(new FileInputStream(excelFile)));
            }
        }
        return dataWorkbook.get(tempFilePath);
    }
     
    /**
     * 读取数据后关闭
     * @param tempFilePath
     */
    public void readClose(String tempFilePath){
        dataWorkbook.remove(tempFilePath);
    }
    
    public List<Map<String,Object>> getVEmp(List<VEmp> list){
    	List<Map<String,Object>> datalist = new  ArrayList<Map<String,Object>>();
              
        for(int i=0;i<list.size();i++){
            Map<String,Object> map = new HashMap<String, Object>();
        	map.put("cur_date",list.get(i).getCurDate()!=null?list.get(i).getCurDate().toString().substring(0,10):"");
        	map.put("emp_work_unit",list.get(i).getEmpWorkUnit());
        	map.put("emp_no",list.get(i).getEmpNo());
        	map.put("emp_name",list.get(i).getEmpName());
        	map.put("emp_phone",list.get(i).getEmpPhone());
        	map.put("emp_sex_name",list.get(i).getEmpSexName());
        	map.put("emp_nation",list.get(i).getEmpNation());
        	map.put("emp_birth",list.get(i).getEmpBirth()!=null?list.get(i).getEmpBirth().toString().substring(0,10):"");
        	map.put("emp_birth_mon",list.get(i).getEmpBirthMon());
        	map.put("emp_age",list.get(i).getEmpAge());
        	map.put("emp_age_group",list.get(i).getEmpAgeGroup());
        	map.put("emp_id_no",list.get(i).getEmpIdNo());
        	map.put("emp_politics",list.get(i).getEmpPolitics());
        	map.put("emp_plate",list.get(i).getEmpPlate());
        	map.put("emp_dept_name",list.get(i).getEmpDeptName());
        	map.put("emp_work_place",list.get(i).getEmpWorkPlace());
        	map.put("emp_post_name",list.get(i).getEmpPostName());
        	map.put("emp_entry_time",list.get(i).getEmpEntryTime()!=null?list.get(i).getEmpEntryTime().toString().substring(0,10):"");
        	map.put("emp_quit_time",list.get(i).getEmpQuitTime()!=null?list.get(i).getEmpQuitTime().toString().substring(0,10):"");
        	map.put("emp_probation",list.get(i).getEmpProbation());
        	map.put("emp_regularize",list.get(i).getEmpRegularizeName());
        	map.put("emp_regularize_time",list.get(i).getEmpRegularizeTime()!=null?list.get(i).getEmpRegularizeTime().toString().substring(0,10):"");
        	map.put("emp_work_age1",list.get(i).getEmpWorkAge());
        	map.put("emp_work_age2",list.get(i).getEmpWorkAge1());        	
        	map.put("emp_position_levl",list.get(i).getEmpPositionLevlName());
        	map.put("emp_tech_levl",list.get(i).getEmpTechLevlName());
        	map.put("emp_tenure_date",list.get(i).getEmpTenureDate()!=null?list.get(i).getEmpTenureDate().toString().substring(0,10):"");
        	map.put("emp_pos_change1",list.get(i).getEmpPosChange1());
        	map.put("emp_pos_change2",list.get(i).getEmpPosChange2());
        	map.put("emp_training_rec",list.get(i).getEmpTrainingRec());
        	map.put("emp_reword_punish",list.get(i).getEmpRewordPunish());
        	map.put("emp_file_store_pos",list.get(i).getEmpFileStorePos());
        	map.put("emp_file_no",list.get(i).getEmpFileNo());
        	map.put("emp_education",list.get(i).getEmpEducationName());
        	map.put("emp_edu_full_time",list.get(i).getEmpEduFullTime1());
        	map.put("emp_graduate_uni",list.get(i).getEmpGraduateUni());
        	map.put("emp_major",list.get(i).getEmpMajor());
        	map.put("emp_certificate",list.get(i).getEmpCertificate());
        	map.put("emp_certifi_no",list.get(i).getEmpCertifiNo());
        	map.put("emp_certifi_date",list.get(i).getEmpCertifiDate()!=null?list.get(i).getEmpCertifiDate().toString().substring(0,10):"");
        	map.put("emp_certifiexpire_date",list.get(i).getEmpCertifiExpireDate()!=null?list.get(i).getEmpCertifiExpireDate().toString().substring(0,10):"");
        	map.put("emp_certificheck_date",list.get(i).getEmpCertifiCheckDate()!=null?list.get(i).getEmpCertifiCheckDate().toString().substring(0,10):"");
        	map.put("emp_certifi_lev",list.get(i).getEmpCertifiLev());
        	map.put("emp_certifi_scan",list.get(i).getEmpCertifiScan1());
        	map.put("emp_employ_type",list.get(i).getEmpEmployType());
        	map.put("emp_contract_subject",list.get(i).getEmpContractSubject());
        	map.put("emp_contract_begin_date",list.get(i).getEmpContractBeginDate()!=null?list.get(i).getEmpContractBeginDate().toString().substring(0,10):"");
        	map.put("emp_contract_end_date",list.get(i).getEmpContractEndDate()!=null?list.get(i).getEmpContractEndDate().toString().substring(0,10):"");
        	map.put("emp_contract_remain",list.get(i).getEmpContractRemain());
        	map.put("emp_contract_mark",list.get(i).getEmpContractMark());
        	map.put("emp_insured_unit_no",list.get(i).getEmpInsuredUnitNo());
        	map.put("emp_insured_no",list.get(i).getEmpInsuredNo());
        	map.put("emp_insured_fee",list.get(i).getEmpInsuredFee());
        	map.put("emp_insured_date",list.get(i).getEmpInsuredDate()!=null?list.get(i).getEmpInsuredDate().toString().substring(0,10):"");
        	map.put("emp_funds_unit_no",list.get(i).getEmpFundsUnitNo());
        	map.put("emp_funds_no",list.get(i).getEmpFundsNo());
        	map.put("emp_funds_fee",list.get(i).getEmpFundsFee());
        	map.put("emp_funds_date",list.get(i).getEmpFundsDate()!=null?list.get(i).getEmpFundsDate().toString().substring(0,10):"");
            map.put("emp_labor_status1",list.get(i).getEmpLaborStatus1());
            map.put("emp_labor_status2",list.get(i).getEmpLaborStatus2());
            map.put("emp_labor_status3",list.get(i).getEmpLaborStatus3());
            map.put("emp_labor_status4",list.get(i).getEmpLaborStatus4());
            map.put("emp_labor_status5",list.get(i).getEmpLaborStatus5());
            map.put("emp_labor_status6",list.get(i).getEmpLaborStatus6());
        	map.put("emp_pre_work_unit",list.get(i).getEmpPreWorkUnit());
        	map.put("emp_first_work_date",list.get(i).getEmpFirstWorkDate()!=null?list.get(i).getEmpFirstWorkDate().toString().substring(0,10):"");
        	map.put("emp_marriage",list.get(i).getEmpMarriageName());
        	map.put("emp_bear",list.get(i).getEmpBearName());
        	map.put("emp_account",list.get(i).getEmpAccount());
        	map.put("emp_account_properties",list.get(i).getEmpAccountProperties());
        	map.put("emp_account_address",list.get(i).getEmpAccountAddress());
        	map.put("emp_address",list.get(i).getEmpAddress());
        	map.put("emp_mobilePhone",list.get(i).getEmpMobilePhone());
        	map.put("emp_contact_info",list.get(i).getEmpContactInfo());
        	map.put("emp_residence",list.get(i).getEmpResidenceName());
        	map.put("emp_apply",list.get(i).getEmpApply());
        	map.put("emp_special",list.get(i).getEmpSpecial());
        	map.put("emp_remark",list.get(i).getEmpRemark());
        	map.put("emp_flow_chart",list.get(i).getEmpFlowChart());
        	map.put("emp_resume",list.get(i).getEmpResume());
        	map.put("emp_reg_form",list.get(i).getEmpRegForm());
        	map.put("emp_agreement",list.get(i).getEmpAgreement());
        	map.put("emp_photo",list.get(i).getEmpPhoto());
        	map.put("emp_idcard_copy",list.get(i).getEmpIDCardCopy());
        	map.put("emp_booklet_copy",list.get(i).getEmpBookletCopy());
        	map.put("emp_diploma_copy",list.get(i).getEmpDiplomaCopy());
        	map.put("emp_checkup_report",list.get(i).getEmpCheckupReport());
        	map.put("emp_leave_certificate",list.get(i).getEmpLeaveCertificate());
        	map.put("emp_bondsman_info",list.get(i).getEmpBondsmanInfo());
        	map.put("emp_bond",list.get(i).getEmpBond());
        	map.put("emp_driver_license",list.get(i).getEmpDriverLicense());
        	map.put("emp_title_certificate",list.get(i).getEmpTitleCertificate());
        	map.put("emp_probation_wages",list.get(i).getEmpProbationWages());
        	map.put("emp_full_wages",list.get(i).getEmpFullWages());
        	map.put("emp_full_wages_date",list.get(i).getEmpFullWagesDate()!=null?list.get(i).getEmpFullWagesDate().toString().substring(0,10):"");
        	map.put("emp_fullwages_flag",list.get(i).getEmpFullwagesFlag1());
        	map.put("emp_real_wages",list.get(i).getEmpRealWages());
        	map.put("emp_raise_wages",list.get(i).getEmpRaiseWages());
        	map.put("emp_bank_name",list.get(i).getEmpBankName());
        	map.put("emp_bank_card_no",list.get(i).getEmpBankCardNo());
        	datalist.add(map);
        }
        return datalist;
    }
    
    
}