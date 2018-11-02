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
package com.coomia.erm.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * ClassName:EncodUtil
 * Function: BCryptPasswordEncoder
 * Reason: TODO ADD REASON.
 * Date: 2017年6月7日 上午9:53:26
 * 
 * @author spancer
 * @version
 * @since JDK 1.6
 * @see
 */
/**
 * @author spancer
 */
public class EncodUtil {
    private static PasswordEncoder pe = new BCryptPasswordEncoder();

    /**
     * BCryptPasswordEncoder
     * 
     * @param original
     * @return
     */
    public static String encoderBCrypt(String original) {
        return pe.encode(original);
    }
}
