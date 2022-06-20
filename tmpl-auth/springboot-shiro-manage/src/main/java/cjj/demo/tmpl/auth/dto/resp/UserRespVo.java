package cjj.demo.tmpl.auth.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRespVo {
    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("性别(1.男 2.女)")
    private Integer sex;

    @ApiModelProperty("账户状态(1.正常 2.锁定 )")
    private Integer status;

    @ApiModelProperty("邮箱(唯一)")
    private String email;
}
