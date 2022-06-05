package com.tang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.entity.History;
import com.tang.mapper.HistoryMapper;
import com.tang.service.HistoryService;
import org.springframework.stereotype.Service;


@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

}
