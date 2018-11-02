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
package com.coomia.erm.entity;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author spancer date: 2017年11月6日 下午5:53:57 <br/>
 */
public class FundFlowGraph {

  private Queue<FlowNode> nodes = new LinkedList<FlowNode>();

  /**
   * @return the nodes
   */
  public Queue<FlowNode> getNodes() {
    return nodes;
  }

  /**
   * @param nodes the nodes to set
   */
  public void setNodes(LinkedList<FlowNode> nodes) {
    this.nodes = nodes;
  }


  /**
   * @param nodes
   * @param type
   */
  public FundFlowGraph(LinkedList<FlowNode> nodes) {
    super();
    this.nodes = nodes;
  }

  /**
   * 
   * fullGraph TODO
   * 
   * @param ids 流程节点ID，值为fundstatus的code
   * @param indexId 目前正的运行的环节ID的索引，从1开始
   * @return
   */
  public FundFlowGraph(String idstr, int runningId) {
    String[] idss = idstr.replaceAll(" ", "").split(",");
    LinkedList<FlowNode> nodes = new LinkedList<FlowNode>();
    int index = 0;
    if (idstr.contains(runningId + "")) {
      index = indexOf(idss, runningId + "");
    } else {
      if (runningId == FundStatus.OPERAUDITFAIL.getCode()
          || runningId == FundStatus.OPERAUDITSUCCESS.getCode())
        index = indexOf(idss, FundStatus.OPERAUDIT.getCode() + "");
      else if (runningId == FundStatus.SCHAUDITFAIL.getCode()
          || runningId == FundStatus.SCHAUDITSUCCESS.getCode())
        index = indexOf(idss, FundStatus.SCHAUDIT.getCode() + "");
      else if (runningId == FundStatus.EBAUDITFAIL.getCode()
          || runningId == FundStatus.EBAUDITSUCCESS.getCode())
        index = indexOf(idss, FundStatus.EBAUDIT.getCode() + "");
    }
    for (int i = 0; i < idss.length; i++) {
      int nodeId = Integer.parseInt(idss[i].trim());
      if (i < index)
        nodes.add(new FlowNode(nodeId, 1));
      else if (i == index)
      {
        if(i==idss.length-1)
          nodes.add(new FlowNode(nodeId, 1));
        else
          nodes.add(new FlowNode(nodeId, 2));
      }
      else
        nodes.add(new FlowNode(nodeId, 0));
    }
    this.nodes = nodes;
  }
  
  public static int indexOf(String[] line, String s)
  {
    for (int i = 0; i<line.length; i++) {
      if(line[i].equals(s))
        return i;
    }
    return -1;
  }

  public static FundFlowGraph ebFlow(int runningId) {
    return new FundFlowGraph("3,4,5,6,7,8,9,10,13,15", runningId);
  }

  public static FundFlowGraph schFlow(int runningId) {
    return new FundFlowGraph("3,4,5,6,7,8,9,10,13,15", runningId);
  }

  // 1,4,5,6,7(16,19),8(17,20),9(18,21),10,12,14,15
  public static FundFlowGraph studentFlow(int runningId) {
    return new FundFlowGraph("3,4,5,6,7,8,9,10,13,15", runningId);
  }


}
