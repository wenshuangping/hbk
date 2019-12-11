

package com.github.common.util;



import com.baomidou.mybatisplus.plugins.Page;
import org.apache.commons.lang.StringUtils;

import java.util.Map;

/**
 * @author wsp
 * @date 2017/12/10
 */
public class Query<T> extends Page<T> {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;
    private static final String PAGE = "page";
    private static final String LIMIT = "limit";
    private static final String ORDER_BY_FIELD = "orderByField";
    private static final String IS_ASC = "isAsc";

    public Query(Map<String, Object> params) {
        super(Integer.parseInt(params.getOrDefault(PAGE, 1).toString())
                , Integer.parseInt(params.getOrDefault(LIMIT, 10).toString()));

        String orderByField = params.getOrDefault(ORDER_BY_FIELD, "").toString();
        if (StringUtils.isNotEmpty(orderByField)) {
            this.setOrderByField(orderByField);
        }

        boolean isAsc = Boolean.parseBoolean(params.getOrDefault(IS_ASC, Boolean.TRUE).toString());
        this.setAsc(isAsc);

        params.remove(PAGE);
        params.remove(LIMIT);
        params.remove(ORDER_BY_FIELD);
        params.remove(IS_ASC);
        this.setCondition(params);
    }

    public Query(Map<String, Object> params, boolean onlyPage) {
        super(Integer.parseInt(params.getOrDefault(PAGE, 1).toString())
                , Integer.parseInt(params.getOrDefault(LIMIT, 10).toString()));
        if (!onlyPage) {
            String orderByField = params.getOrDefault(ORDER_BY_FIELD, "").toString();
            if (StringUtils.isNotEmpty(orderByField)) {
                this.setOrderByField(orderByField);
            }
            boolean isAsc = Boolean.parseBoolean(params.getOrDefault(IS_ASC, Boolean.TRUE).toString());
            this.setAsc(isAsc);
            params.remove(PAGE);
            params.remove(LIMIT);
            params.remove(ORDER_BY_FIELD);
            params.remove(IS_ASC);
            this.setCondition(params);
        }
    }

}
