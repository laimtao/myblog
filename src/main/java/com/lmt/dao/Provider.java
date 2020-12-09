package com.lmt.dao;

import com.lmt.domain.Blog;
import org.apache.ibatis.jdbc.SQL;

public class Provider {
    public String select(Blog blog){
        return new SQL(){
            {
                SELECT("*");
                FROM("t_blog");
                if (blog.getTitle()!=null && !"".equals(blog.getTitle())){
                    String title="%"+blog.getTitle()+"%";
                    WHERE("title like '"+title+"'");
                }
                if (blog.getTypeId()!=null && !"".equals(blog.getTypeId())){
                    WHERE("type_id = "+blog.getTypeId());
                }
                if (blog.isRecommend()==true){
                    WHERE("recommend=1");
                }
            }
        }.toString();
    }
}
