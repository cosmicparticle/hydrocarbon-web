package cho.carbon.hc.web.common.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 * <p>Title: ControllerExceptionHandler</p>
 * <p>Description: </p><p>
 * 通用的Controller错误处理机制，将错误显示到控制台后将错误信息返回到页面
 * </p>
 */
//@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleIOException(Exception ex) {
        ex.printStackTrace();
        return ClassUtils.getShortName(ex.getClass()) + ex.getMessage();
    }
}
