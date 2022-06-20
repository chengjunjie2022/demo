package cjj.demo.tmpl.auth.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginRespVo {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("登录名")
    private String loginName;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("登录邮箱")
    private String email;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("性别（0=保密，1=男，2=女）")
    private Integer sex;

    @ApiModelProperty("权限菜单集合")
    private List<PermissionRespNodeVo> menus;

    @ApiModelProperty("按钮权限集合")
    private List<String> permissions;

    @ApiModelProperty("正常的业务token")
    private String authorization;

}
