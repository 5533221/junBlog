package com.jun.domain.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/20 17:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelCategoryVo {

    @ExcelProperty("分类名")
    private String name;

    @ExcelProperty("描述")
    private String description;

    @ExcelProperty("状态0:正常,1禁用")
    private String status;

}
