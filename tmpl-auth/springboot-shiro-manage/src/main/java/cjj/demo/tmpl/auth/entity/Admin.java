package cjj.demo.tmpl.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
@Data
@Accessors(chain = true)
@TableName("t_admin")
@ApiModel(value = "Admin对象", description = "管理员表")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("登录名")
    private String loginName;

    @ApiModelProperty("密码（sp_password加密）")
    private String pwd;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("登录邮箱")
    private String email;

    @ApiModelProperty("登录手机号")
    private String phone;

    @ApiModelProperty("性别（0=保密，1=男，2=女）")
    private Integer sex;

    @ApiModelProperty("账户状态(1=正常，-1=锁定 )")
    private Integer stat;

    @ApiModelProperty("是否删除（0=否，1=是）")
    private Boolean isDel;

    @ApiModelProperty("上次登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("上次登录IP")
    private Integer lastLoginIp;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
