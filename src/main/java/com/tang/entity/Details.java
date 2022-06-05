package com.tang.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Details {
    private Integer id;
    @TableField(value = "province_short_name")
    private String provinceShortName; // 省份短名称
    @TableField(value = "current_confirmed_count")
    private Integer currentConfirmedCount; // 现有确诊
    private Integer confirmedCount; // 累计确诊
    private Integer deadCount; // 累计死亡
    private Integer curedCount; // 累计治愈
    private String statisticsData; // 以往数据
    private String time; // 数据获取时间
}
