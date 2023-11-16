package com.jun.domain.entity.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/4 16:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVo {


    private Long id;
    //名称
    private String name;
    //logo
    private String logo;
    //描述
    private String description;
    //网站地址
    private String address;

}
