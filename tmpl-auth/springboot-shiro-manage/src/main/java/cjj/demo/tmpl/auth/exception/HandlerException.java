package cjj.demo.tmpl.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import road.cjj.commons.entity.R;
import road.cjj.commons.entity.RC;


/**
 * 全局异常
 */
@Slf4j
@RestControllerAdvice
public class HandlerException {

    /**
     * 捕获 MethodArgumentNotValidException
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> handleBusinessException(MethodArgumentNotValidException e){
        log.error("【参数异常】，{}",e);
        BindingResult br = e.getBindingResult();
        FieldError fe = br.getFieldError();
        return R.err(RC.ERR_P.getCode(), fe.getDefaultMessage());
    }

    /**
     * 捕获 BusinessException（shiro授权）
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public R<String> handleBusinessException(UnauthorizedException e){
        log.error("【权限异常】，{}",e);
        return R.err(RC.ERR_NOT_PERMISSION.getCode(),RC.ERR_NOT_PERMISSION.getMsg());
    }

    /**
     * 捕获 BizException
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    public R<String> handleBusinessException(BizException e){
        log.error("【业务异常】，{}",e);
        return R.err(e.getRcode(),e.getRmsg());
    }

    /**
     * 捕获 Exception
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R<String> handleException(Exception e){
        if (e instanceof MaxUploadSizeExceededException){
            log.error("【上传文件异常】，{}",e);
            return R.err(RC.ERR_FILE_TO_LARGE.getCode(),RC.ERR_FILE_TO_LARGE.getMsg());
        }
        log.error("【系统异常】，{}",e);
        return R.err(RC.ERR_SYS.getCode(), RC.ERR_SYS.getMsg());
    }
}
