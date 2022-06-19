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
 * 管理员角色对应表
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
@Data
@Accessors(chain = true)
@TableName("t_admin_role")
@ApiModel(value = "AdminRole对象", description = "管理员角色对应表")
public class AdminRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("管理员id")
    private Long adminid;

    @ApiModelProperty("角色 id")
    private Long roleid;


}
