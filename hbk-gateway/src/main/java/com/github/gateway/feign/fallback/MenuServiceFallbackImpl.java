
package com.github.gateway.feign.fallback;

import com.github.common.vo.MenuVO;
import com.github.gateway.feign.MenuService;
import com.xiaoleilu.hutool.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author wsp
 * @date 2017/10/31
 * why add @Service when i up version ?
 * https://github.com/spring-cloud/spring-cloud-netflix/issues/762
 */
@Slf4j
@Service
public class MenuServiceFallbackImpl implements MenuService {
    @Override
    public Set<MenuVO> findMenuByRole(String role) {
        System.out.println("findMenuByRole=======================================");
        log.error("调用{}异常{}", "findMenuByRole", role);
        return CollUtil.newHashSet();
    }
}
