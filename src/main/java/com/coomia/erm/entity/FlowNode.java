/**
 * Copyright (c) 2015-2020 Coomia Network Technology Co., Ltd.  All Rights Reserved.
 *
 * <p>
 * This software is licensed not sold.  Use or reproduction of this software by
 * any unauthorized individual or entity is strictly prohibited. This software
 * is the confidential and proprietary information of Coomia Network Technology Co., Ltd.
 * Disclosure of such confidential information and shall use it only in
 * accordance with the terms of the license agreement you entered into with
 * Coomia Network Technology Co., Ltd.
 *
 * <p>
 * Coomia Network Technology Co., Ltd. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT.  Coomia Network Technology Co., Ltd. SHALL NOT
 * BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING,
 * MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ANY DERIVATIVES THEREOF.
 */
package com.coomia.erm.entity;

/**
 * @author spancer
 * date: 2017年11月6日 下午5:55:00 <br/> 
 */
public class FlowNode {

    private int id;
    private String name;
    private int status = 0; //0 undone 1 done 2 running  
    
    /**
     * @return the id
     */
    public int getId() {
      return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
      this.id = id;
    }
    /**
     * @return the name
     */
    public String getName() {
      return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
      this.name = name;
    }
    /**
     * @return the status
     */
    public int getStatus() {
      return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
      this.status = status;
    }
    /**
     * @param id
     * @param name
     * @param status
     */
    public FlowNode(int id, String name, int status) {
      super();
      this.id = id;
      this.name = name;
      this.status = status;
    }
    
    public FlowNode(int id, int status) {
      super();
      this.id = id;
      this.status = status;
      this.name = FundStatus.getName(id).getName();
    }
    
    
}
