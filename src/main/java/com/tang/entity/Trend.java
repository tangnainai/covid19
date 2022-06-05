package com.tang.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trend {
    @TableId  // 主键
    private Integer dateId; // 时间
    private Integer confirmedCount; // 累计确诊
    private Integer curedCount; // 累计治愈
    @TableField("current_confirmed_count")
    private Integer currentConfirmedCount; // 现存确诊
    private Integer deadCount; // 死亡
    private Integer confirmedIncr; // 新增确诊
    private Integer curedIncr; // 新增治愈
    @TableField("current_confirmed_incr")
    private Integer currentConfirmedIncr; // 确诊变化
    private Integer deadIncr; // 新增死亡
    private String time;
}
