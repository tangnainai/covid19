package com.tang.mapper;

import com.tang.entity.MostSerious;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tang
 * @since 2022-06-04
 */
@Mapper
public interface MostSeriousMapper extends BaseMapper<MostSerious> {
    @Update("truncate table most_serious")
    void deleteUser();
}
