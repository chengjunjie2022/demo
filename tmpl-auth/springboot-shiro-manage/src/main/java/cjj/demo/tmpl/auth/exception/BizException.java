package cjj.demo.tmpl.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BizException extends RuntimeException{
    private int rcode;
    private String rmsg;
}
