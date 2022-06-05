package com.tang.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author tang
 * @since 2022-06-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("most_serious")
@ApiModel(value = "MostSerious对象", description = "")
public class MostSerious implements Serializable {
    private static final long serialVersionUID = 1L;
      @ApiModelProperty("日期")
      @TableId
      private Integer dateId;
      @ApiModelProperty("确诊")
      private Integer confirmedIncr;
      @ApiModelProperty("死亡")
      private Integer deadIncr;
      @ApiModelProperty("省份")
      private String provinces;
}
