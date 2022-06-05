package com.tang.service;

import com.tang.entity.MostSerious;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tang
 * @since 2022-06-04
 */
public interface IMostSeriousService extends IService<MostSerious> {
    void deleteUser();
}
