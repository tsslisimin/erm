/**
 * Copyright (c) 2015-2020 Coomia Network Technology Co., Ltd. All Rights Reserved.
 *
 * <p>
 * This software is licensed not sold. Use or reproduction of this software by any unauthorized
 * individual or entity is strictly prohibited. This software is the confidential and proprietary
 * information of Coomia Network Technology Co., Ltd. Disclosure of such confidential information
 * and shall use it only in accordance with the terms of the license agreement you entered into with
 * Coomia Network Technology Co., Ltd.
 *
 * <p>
 * Coomia Network Technology Co., Ltd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY
 * OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. Coomia Network
 * Technology Co., Ltd. SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ANY DERIVATIVES THEREOF.
 */
package com.coomia.erm.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.coomia.erm.model.CoomiaHttpResponse;
import com.coomia.erm.model.CoomiaHttpStatus;
import com.coomia.erm.service.impl.FileUploadService;
import com.coomia.erm.util.ExcelTemplate;

/**
 * @author spancer 用于图片上传和头像修改 date: 2017年10月17日 下午4:12:03 <br/>
 */
@RestController
@RequestMapping("/erm/api/ermFile")
public class FileUploadController {
	@Autowired
	private FileUploadService fileUploadService;
	
	private final ResourceLoader resourceLoader;  
	  
    @Autowired  
    public FileUploadController(ResourceLoader resourceLoader) {  
        this.resourceLoader = resourceLoader;  
    }   

	@PostMapping("/uploadFile")
	public ResponseEntity<?> imageUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
		if (file.isEmpty()) {
			return new ResponseEntity<>(CoomiaHttpResponse.error(CoomiaHttpStatus.FILE_IS_EMPTY),
					HttpStatus.BAD_REQUEST);
		}
		String imgUrl;
		try {
			imgUrl = fileUploadService.imageUpload(file);
		} catch (IOException e) {
			return new ResponseEntity<>(CoomiaHttpResponse.error(CoomiaHttpStatus.FILE_UPLOAD_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(CoomiaHttpResponse.ok(imgUrl), HttpStatus.OK);
	}
	
	@PostMapping("/uploadFileWithoutLogin")
	public ResponseEntity<?> uploadFileWithoutLogin(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
		if (file.isEmpty()) {
			return new ResponseEntity<>(CoomiaHttpResponse.error(CoomiaHttpStatus.FILE_IS_EMPTY),
					HttpStatus.BAD_REQUEST);
		}
		String imgUrl;
		try {
			imgUrl = fileUploadService.imageUpload(file);
		} catch (IOException e) {
			return new ResponseEntity<>(CoomiaHttpResponse.error(CoomiaHttpStatus.FILE_UPLOAD_ERROR),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(CoomiaHttpResponse.ok(imgUrl), HttpStatus.OK);
	}

	@RequestMapping(value = "exportFile", method = { RequestMethod.POST, RequestMethod.GET })
	public void downloadFile(HttpServletRequest request, HttpServletResponse response,@RequestBody Map<String,Object> params) {
		try {
			String fileName = (String) params.get("fileName");
			String filePath = "/public/img/work/Tomcat/localhost/ROOT/" + fileName;
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(filePath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes()));
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/img/{filename:.+}")  
    public ResponseEntity<?> getFile(@PathVariable String filename) {  
        try {  
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get("/public/img/work/Tomcat/localhost/ROOT/", filename).toString()));  
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.notFound().build();  
        }  
    }  

	/**
     * @Title: getExcelTemplate 
     * @Description: 生成Excel模板并导出 
     * @param @param uuid
     * @param @param request
     * @param @param response
     * @param @return
     * @return Data
     * @throws
     */
    @RequestMapping("/exportFileTemplate")
    public void getExcelTemplate(HttpServletRequest request, HttpServletResponse response){
        
//        String fileName = "员工信息表"; //模板名称
        String[] handers = {"姓名","性别","证件类型","证件号码","服务结束时间","参保地","民族"}; //列标题
        
        //下拉框数据
        List<String[]> downData = new ArrayList();
        String[] str1 = {"男","女","未知"};
        String[] str2 = {"北京","上海","广州","深圳","武汉","长沙","湘潭"};
        String[] str3 = {"01-汉族","02-蒙古族","03-回族","04-藏族","05-维吾尔族","06-苗族","07-彝族","08-壮族","09-布依族","10-朝鲜族","11-满族","12-侗族","13-瑶族","14-白族","15-土家族","16-哈尼族","17-哈萨克族","18-傣族","19-黎族","20-傈僳族","21-佤族","22-畲族","23-高山族","24-拉祜族","25-水族","26-东乡族","27-纳西族","28-景颇族","29-柯尔克孜族","30-土族","31-达斡尔族","32-仫佬族","33-羌族","34-布朗族","35-撒拉族","36-毛难族","37-仡佬族","38-锡伯族","39-阿昌族","40-普米族", "41-塔吉克族","42-怒族","43-乌孜别克族","44-俄罗斯族","45-鄂温克族","46-德昂族","47-保安族","48-裕固族","49-京族","50-塔塔尔族", "51-独龙族","52-鄂伦春族","53-赫哲族","54-门巴族","55-珞巴族","56-基诺族","98-外国血统","99-其他"};
        downData.add(str1);
        downData.add(str2);
        downData.add(str3);
        String [] downRows = {"1","5","6"}; //下拉的列序号数组(序号从0开始)
        
        try {
        	String filePath = "\\data\\template\\test.xls";
            ExcelTemplate.createExcelTemplate(filePath, handers, downData, downRows,"test");
            ExcelTemplate.getExcel(filePath, "test", response, request);
            ExcelTemplate.delFile(filePath);
//            ExcelTemplate.getExcelTemplate(fileName, handers, downData, downRows, request, response);
            
        } catch (Exception e) {
        }
    }

}
