package com.admin.model.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_sale_relation")
public class SaleRelation{

	@TableId(value = "id", type = IdType.INPUT)
	private String id;

	private String buserId; // 新注册Buser的ID

	private String buserMobile; // Buser手机号(冗余字段)

	private String parentId; // 推荐人ID

	private Integer givenAmount; // 支付次数

	private Integer relationStatus; // 关系是否可用: 1-可用 2-停用 (支付次数达5次时停用)
	public static final Integer RELATION_STATE_INUSE = 1;
	public static final Integer RELATION_STATE_UNUSE = 2;

	private Integer delFlag; // 删除标记: 0-未删除 1-删除
	public static final Integer DEL_FLAG_UNDELETE = 0;
	public static final Integer DEL_FLAG_DELETED = 1;

	private Date createDate;

	private Date updateDate;

	private String createBy;

	private String updateBy;

}
