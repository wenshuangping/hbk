package com.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.admin.mapper.SysZuulRouteMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.github.common.constant.CommonConstant;
import com.github.common.entity.SysZuulRoute;
import com.admin.service.SysZuulRouteService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

/**
 * <p>
 * 动态路由配置表 服务实现类
 * </p>
 *
 * @author wsp
 * @since 2018-05-15
 */
@Service
public class SysZuulRouteServiceImpl extends ServiceImpl<SysZuulRouteMapper, SysZuulRoute>
        implements SysZuulRouteService {

    @Resource
    private RedisTemplate<String, List<SysZuulRoute>> redisTemplate;

    /**
     * 立即生效配置
     *
     * @return
     */
    @Override
    public Boolean applyZuulRoute() {
        EntityWrapper<SysZuulRoute> wrapper = new EntityWrapper<>();
        wrapper.eq(CommonConstant.DEL_FLAG, CommonConstant.STATUS_NORMAL);
        List<SysZuulRoute> routeList = selectList(wrapper);
        redisTemplate.opsForValue().set(CommonConstant.ROUTE_KEY, routeList);
        return Boolean.TRUE;
    }
}
