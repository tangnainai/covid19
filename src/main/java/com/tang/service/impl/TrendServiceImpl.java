package com.tang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.entity.Trend;
import com.tang.mapper.TrendMapper;
import com.tang.service.TrendService;
import org.springframework.stereotype.Service;

@Service
public class TrendServiceImpl extends ServiceImpl<TrendMapper, Trend>implements TrendService {
}
