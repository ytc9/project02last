package com.example.project02last.entity;

import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author 杨添辰
 * @since 2022-04-27
 */
@Getter
@Setter
  @ApiModel(value = "Menu对象", description = "")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        private Integer id;

      @ApiModelProperty("名称")
      private String name;

      @ApiModelProperty("路径")
      private String path;

      @ApiModelProperty("图标")
      private String icon;

      @ApiModelProperty("描述")
      private String description;


}
