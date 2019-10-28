package com.esb.user;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 主要用途：Mybatis注解的常見使用方式.
 */
@Mapper
public interface UserMapper {
    /**
     * 方式1：使用注解編實SQL。
     */
    @Select("select * from t_user")
	/*
	 * @Results({
	 * 
	 * @Result(property = "userId", column = "USER_ID"),
	 * 
	 * @Result(property = "username", column = "USERNAME"),
	 * 
	 * @Result(property = "password", column = "PASSWORD"),
	 * 
	 * @Result(property = "phoneNum", column = "PHONE_NUM") })
	 */
    List<User> list();

    /**
     * 方式2：使用注解指定某個工具類的方法来動態編寫SQL.
     */
    @SelectProvider(type = UserSqlProvider.class, method = "listByUsername")
    List<User> listByUsername(String username);

    /**
     * 延伸：上述兩種方式都可以附加@Results注解来指定结果集的映射關系.
     *
     * PS：如果符合下劃線轉駝峰的匹配項可以直接省略不寫。
     */
    @Results({
            @Result(property = "userId", column = "USER_ID"),
            @Result(property = "username", column = "USERNAME"),
            @Result(property = "password", column = "PASSWORD"),
            @Result(property = "phoneNum", column = "PHONE_NUM")
    })
    @Select("select * from t_user")
    List<User> listSample();

    /**
     * 延伸：無論什麼方式,如果涉及多個参數,则必须加上@Param注解,否则無法使用EL表達表獲取参數。
     */
    @Select("select * from t_user where username like #{username} and password like #{password}")
    User get(@Param("username") String username, @Param("password") String password);

    @SelectProvider(type = UserSqlProvider.class, method = "getBadUser")
    User getBadUser(@Param("username") String username, @Param("password") String password);
    
    @Insert("INSERT INTO `t_user` VALUES (#{userId},#{username},#{password},#{phoneNum})")
    //@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void saveUser(User user);
    
    
    /*
     * 以下為seckill功能
     */
    
    @Select("select * from sk_user where id = #{id}")
    public User getById(@Param("id")long id);

    @Update("update sk_user set password = #{password} where id = #{id}")
    public void update(User toBeUpdate);
}

