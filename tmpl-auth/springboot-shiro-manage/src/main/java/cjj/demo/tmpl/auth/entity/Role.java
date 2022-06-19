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
 * 角色表
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
@Data
@Accessors(chain = true)
@TableName("t_role")
@ApiModel(value = "Role对象", description = "角色表")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色编码")
    private String code;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("父角色ID")
    private Long pid;

    @ApiModelProperty("角色描述")
    private String description;

    @ApiModelProperty("删除状态（0=未删除，1=已删除）")
    private Boolean isDel;

    @ApiModelProperty("启用状态（0=禁用，1=启用）")
    private Boolean isPub;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
