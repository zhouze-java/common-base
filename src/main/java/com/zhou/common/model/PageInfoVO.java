package com.zhou.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(Include.NON_NULL)
@ApiModel(description = "分页对象")
public class PageInfoVO<T> {
	
	@ApiModelProperty(value="当前页")
    private Integer pageNum = 1;
	
	@ApiModelProperty(value="每页的数量",notes="默认值10", example="10")
    private Integer pageSize = 10;
	
    @ApiModelProperty(value="总记录数")
    private Integer total;
    
    @ApiModelProperty(value="总页数")
    private Integer pages;
    
    @ApiModelProperty(value="结果集")
    private List<T> list;

}
