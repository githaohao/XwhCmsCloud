package com.xwh.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xwh.system.entity.SysTenant;
import com.xwh.system.mapper.SysTenantMapper;
import com.xwh.system.service.SysTenantService;
import org.springframework.stereotype.Service;

/**
 * @author xwh
 **/
@Service
public class SysTenantServiceImpl  extends ServiceImpl<SysTenantMapper, SysTenant> implements SysTenantService {
}
