package com.lmt.dao;

import com.lmt.domain.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
@Repository
public interface UserDao {
    @Select("select * from t_user where username=#{username} and type=1")
    User findUser(String username);

    @Insert("insert into t_user(nickname,username,password,email,avatar,type,create_time) values(#{nickname},#{username},#{password},#{email},#{avatar},#{type},#{createTime})")
    @Results({
     @Result(column = "create_time",property = "createTime")
    })
    void saveUser(User user);
    @Select("select * from t_user where username=#{username} and password=#{password}")
    User queryUser(String username,String password);
    @Update("update t_user set password=#{password} where id=#{id}")
    void changePassword(@Param("id") Long id,@Param("password") String password);
}
