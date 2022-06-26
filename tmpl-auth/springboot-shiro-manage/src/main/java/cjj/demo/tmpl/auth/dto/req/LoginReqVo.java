package cjj.demo.tmpl.auth.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "登录对象",description = "用户登录的参数对象")
public class LoginReqVo {

    @ApiModelProperty("登录名")
    @NotBlank(message = "登录名不能为空")
    private String loginName;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String pwd;

//    @ApiModelProperty(value = "登录类型 1：pc；2：App")
//    @NotBlank(message = "用户登录类型不能为空")
//    private String type;

//    @ApiModelProperty("验证码")
//    @NotBlank(message = "验证码不能为空")
//    private String captcha;

}
