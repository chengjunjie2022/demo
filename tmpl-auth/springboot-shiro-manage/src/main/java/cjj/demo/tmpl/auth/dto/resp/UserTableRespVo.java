package cjj.demo.tmpl.auth.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserTableRespVo {

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

    @ApiModelProperty("创建人名称")
    private String createUserName;

    @ApiModelProperty("更新人名称")
    private String updateUserName;

    @ApiModelProperty("创建来源")
    private Integer createWhere;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
