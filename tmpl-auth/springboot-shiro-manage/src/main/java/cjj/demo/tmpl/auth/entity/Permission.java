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
 * 权限表
 * </p>
 *
 * @author junjie.cheng
 * @since 2022-06-18
 */
@Data
@Accessors(chain = true)
@TableName("t_permission")
@ApiModel(value = "Permission对象", description = "权限表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("菜单权限编码(前端按钮权限标识)")
    private String code;

    @ApiModelProperty("菜单权限名称")
    private String title;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("授权(如：sys:user:add)")
    private String perms;

    @ApiModelProperty("访问地址URL（前端文件路由）")
    private String path;

    @ApiModelProperty("与前端vue路由name保持一致")
    private String permissionName;

    @ApiModelProperty("父菜单id")
    private Long pid;

    @ApiModelProperty("优先级")
    private Double priority;

    @ApiModelProperty("菜单权限类型(1=目录,2=菜单,3=按钮)")
    private Integer kind;

    @ApiModelProperty("删除状态（0=未删除，1=已删除）")
    private Boolean isDel;

    @ApiModelProperty("启用状态（0=禁用，1=启用）")
    private Boolean isPub;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
