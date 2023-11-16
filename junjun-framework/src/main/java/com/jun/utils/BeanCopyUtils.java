package com.jun.utils;

import com.jun.domain.entity.Article;
import com.jun.domain.entity.vo.hotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 27164
 * @version 1.0
 * @description: TODO
 * @date 2023/10/1 10:35
 */
public class BeanCopyUtils {

    //私有空参
    private BeanCopyUtils(){

    }

    //拷贝对象
    public static  <V>V copyBean(Object source,Class<V> clazz){

        V result=null;
        try {
            //创建目标对象

            result = clazz.newInstance();
            //拷贝属性
            BeanUtils.copyProperties(source,result);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static <o,v>List<v> copyBeanList(List<o> list,Class<v> clazz){


       return list.stream()
                .map(o -> copyBean(o,clazz))
                .collect(Collectors.toList());
    }


}
