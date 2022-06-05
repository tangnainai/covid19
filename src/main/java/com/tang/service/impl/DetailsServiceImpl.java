package com.tang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tang.entity.Details;
import com.tang.mapper.DetailsMapper;
import com.tang.service.DetailsService;
import org.springframework.stereotype.Service;


@Service
public class DetailsServiceImpl extends ServiceImpl<DetailsMapper, Details> implements DetailsService {
}
