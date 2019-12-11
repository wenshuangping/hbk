package com.admin.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @Author: wwp
 * @Date: 2018/12/15 13:55
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelationLogDTO {
  //成功数目
  int successAmount;

  String batchId;

  //失败数目
  int failAmount;

  //总数目
  int amount;

}
