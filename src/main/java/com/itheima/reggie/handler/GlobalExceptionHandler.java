package com.itheima.reggie.handler;


import com.itheima.reggie.common.R;
import com.itheima.reggie.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 如果出现了异常：本类和全局都不能处理，
 * SpringBoot底层对SpringMVC有兜底处理机制；自适应处理（浏览器响应页面、移动端会响应json）
 * 最佳实践：我们编写全局异常处理器，处理所有异常
 * <p>
 * 前端关心异常状态，后端正确业务流程。
 * 推荐：后端只编写正确的业务逻辑，如果出现业务问题，后端通过抛异常的方式提前中断业务逻辑。前端感知异常；
 *
 */

@Slf4j
@ResponseBody// 结果还是以JSON的形式写出去
@ControllerAdvice(annotations = {RestController.class, Controller.class}) //告诉Spring，这个组件是专门负责进行全局异常处理的
public class GlobalExceptionHandler {

    /**
     * 自定义异常处理, 感觉没什么意义
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public R handleBizException(BizException e) {
        Integer code = e.getCode();
        String msg = e.getMsg();
        return R.error(code, msg);
    }
}
