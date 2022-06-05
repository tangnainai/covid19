package com.tang.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("history_bean")
public class History {
    private Integer id;
    private String time; // 数据获取时间
    @TableField("current_confirmed_count")
    private Integer currentConfirmedCount;// 确诊人数
    private Integer confirmedCount; // 现存确诊
    private Integer curedCount; // 已治愈
    private Integer deadCount; // 死亡人数
    private String provinceName; // 国家
    private String incrVo; // 昨天数据
    private String statisticsData;// 链接
}
