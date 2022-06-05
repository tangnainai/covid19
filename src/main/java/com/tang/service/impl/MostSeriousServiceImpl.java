package com.tang.service.impl;

import com.tang.entity.MostSerious;
import com.tang.mapper.MostSeriousMapper;
import com.tang.service.IMostSeriousService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tang
 * @since 2022-06-04
 */
@Service
public class MostSeriousServiceImpl extends ServiceImpl<MostSeriousMapper, MostSerious> implements IMostSeriousService {
    @Autowired
    private MostSeriousMapper mostSeriousMapper;
    @Override
    public void deleteUser() {
        mostSeriousMapper.deleteUser();
    }
}
