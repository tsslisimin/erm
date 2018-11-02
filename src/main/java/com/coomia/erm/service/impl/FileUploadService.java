
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
package com.coomia.erm.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * spancer.ray on 2017/2/20.
 */
@Service
public class FileUploadService {
	// 文件上传完整目录
	@Value("${upload.path}")
	private String path;
	// 文件上传目录，不包括根目录
	@Value("${upload.dir}")
	private String dir;

	/**
	 * 将上传的图片保存到服务器指定目录
	 * 
	 * @param file
	 *            客户端传输的文件
	 * @return 保存目录和文件名共同组成的字符串
	 */
	public String imageUpload(MultipartFile file) throws IOException {
		// 获取文件名
		String filename = file.getOriginalFilename();
		// 获取文件的后缀名
		String suffixName = filename.substring(filename.lastIndexOf("."));
		// 解决中文问题，linux下中文路径，图片显示问题
		filename = UUID.randomUUID() + suffixName;
		try {
			File dest = new File(filename);
			file.transferTo(dest);
			// 上传成功则返回服务器地址和文件名共同组成的字符串
			return filename;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
