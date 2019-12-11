package com.github.common.bean.handler;

/**
 * @author wsp
 * @date 2018/5/24
 */

import com.github.common.util.Res;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局的的异常拦截器
 *
 * @author wsp
 * @date 2018/05/22
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局异常.
     *
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Res exception(Exception e) {
        log.info("保存全局异常信息 ex={}", e.getMessage(), e);
        String message=e.getMessage();
        Res res  = new Res();
        res.setCode(500);
        res.setMsg(message);
        return res;
    }
}
