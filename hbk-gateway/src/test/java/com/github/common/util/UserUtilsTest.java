

package com.github.common.util;

import com.github.common.constant.CommonConstant;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Optional;

/**
 * @author wsp
 * @date 2017/12/22
 */
public class UserUtilsTest {
    @Test
    public void getToken() throws Exception {
        String authorization = null;
        System.out.println(StringUtils.substringAfter(authorization, CommonConstant.TOKEN_SPLIT));
    }

    @Test
    public void optionalTest() {
        Optional<String> optional = Optional.ofNullable("");

    }

}