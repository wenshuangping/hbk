package com.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.common.entity.SysZuulRoute;


/**
 * <p>
 * 动态路由配置表 服务类
 * </p>
 *
 * @author wsp
 * @since 2018-05-15
 */
public interface SysZuulRouteService extends IService<SysZuulRoute> {

    /**
     * 立即生效配置
     *
     * @return
     */
    Boolean applyZuulRoute();
}
