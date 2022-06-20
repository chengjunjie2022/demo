package cjj.demo.tmpl.auth.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionRespNodeVo {

    @ApiModelProperty("权限id")
    private String id;

    @ApiModelProperty("菜单权限名称")
    private String title;

    @ApiModelProperty("菜单权限图标")
    private String icon;

    @ApiModelProperty("菜单地址")
    private String path;

    @ApiModelProperty("菜单name")
    private String name;

    @ApiModelProperty("是否是菜单")
    private boolean menu;

    @ApiModelProperty("子集集合")
    private List<PermissionRespNodeVo> children;

    /*@ApiModelProperty("默认展开")
    private boolean spread=true;*/

   /* @ApiModelProperty("节点是否选中")
    private boolean checked;*/
}
