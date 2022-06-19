package cjj.demo.tmpl.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色授权表
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
@Data
@Accessors(chain = true)
@TableName("t_role_permission")
@ApiModel(value = "RolePermission对象", description = "角色授权表")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色id")
    private Long roleid;

    @ApiModelProperty("权限id")
    private Long permissionid;


}
