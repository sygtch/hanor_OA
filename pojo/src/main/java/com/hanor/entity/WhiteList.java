package com.hanor.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.*;

/**
 * 登录账户白名单实体
 * @author 郭天驰
 *
 */
@Table(WhiteList.TABLE_NAME)
@Comment("登录账户白名单")
public class WhiteList implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "white_list";
	
	/**
 	 * 登录账户ID
 	 */
    public static final String SYS_USER_ID = "sys_user_id";
	/**
 	 * 用户名称
 	 */
    public static final String USER_NAME = "user_name";
	/**
 	 * ip白名单
 	 */
    public static final String WHITE_IPS = "white_ips";
	/**
 	 * 创建时间
 	 */
    public static final String CREATE_TIME = "create_time";
	
	@Id(auto = false)
	@Comment("登录账户ID")
	@ColDefine(type = ColType.INT,width = 8,notNull = true)
    private Integer sys_user_id;
    
	@Column
	@Name
	@Comment("用户名称")
	@ColDefine(type = ColType.VARCHAR,width = 64)
    private String user_name;
    
	@Column
	@Comment("ip白名单")
	@ColDefine(type = ColType.VARCHAR,width = 255)
    private String white_ips;
    
	@Column
	@Comment("创建时间")
	@ColDefine(type = ColType.DATETIME)
    private Timestamp create_time;
    
	
    public void setSys_user_id (Integer sys_user_id){
    	this.sys_user_id = sys_user_id;
    }
    
	/**
 	 * 登录账户ID
 	 */
    public Integer  getSys_user_id(){
    	return this.sys_user_id;
    }
    
    public void setUser_name (String user_name){
    	this.user_name = user_name;
    }
    
	/**
 	 * 用户名称
 	 */
    public String  getUser_name(){
    	return this.user_name;
    }
    
    public void setWhite_ips (String white_ips){
    	this.white_ips = white_ips;
    }
    
	/**
 	 * ip白名单
 	 */
    public String  getWhite_ips(){
    	return this.white_ips;
    }
    
    public void setCreate_time (Timestamp create_time){
    	this.create_time = create_time;
    }
    
	/**
 	 * 创建时间
 	 */
    public Timestamp  getCreate_time(){
    	return this.create_time;
    }
    
}