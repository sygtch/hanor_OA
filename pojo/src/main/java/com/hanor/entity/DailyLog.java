package com.hanor.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * daily_log实体
 * @author 郭天驰
 *
 */
@Table(DailyLog.TABLE_NAME)
@Comment("daily_log")
public class DailyLog implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "daily_log";
	
	/**
 	 * 日志编号
 	 */
    public static final String LOG_ID = "log_id";
	/**
 	 * 当天日期
 	 */
    public static final String LOG_DATE = "log_date";
	/**
 	 * 当前登录员工的账户ID
 	 */
    public static final String SYS_USER_ID = "sys_user_id";
	/**
 	 * 当前登录员工的账户名称
 	 */
    public static final String USER_NAME = "user_name";
	/**
 	 * 当前登录员工所属部门编号
 	 */
    public static final String DEPT_ID = "dept_id";
	/**
 	 * 当前登录员工所属部门名称
 	 */
    public static final String DEPT_NAME = "dept_name";
	/**
 	 * 工作日志标题
 	 */
    public static final String LOG_TITLE = "log_title";
	/**
 	 * 工作日志内容
 	 */
    public static final String LOG_CONTENT = "log_content";
	/**
 	 * 创建时间
 	 */
    public static final String CREATE_TIME = "create_time";
	
	@Id
	@Comment("日志编号")
	@ColDefine(type = ColType.INT,width = 8,notNull = true)
    private Integer log_id;
    
	@Column
	@Comment("当天日期")
	@ColDefine(type = ColType.DATE,notNull = true)
    private String log_date;
    
	@Column
	@Comment("当前登录员工的账户ID")
	@ColDefine(type = ColType.INT,width = 8,notNull = true)
    private Integer sys_user_id;
    
	@Column
	@Comment("当前登录员工的账户名称")
	@ColDefine(type = ColType.VARCHAR,width = 32,notNull = true)
    private String user_name;
    
	@Column
	@Comment("当前登录员工所属部门编号")
	@ColDefine(type = ColType.INT,width = 8,notNull = true)
    private Integer dept_id;
    
	@Column
	@Comment("当前登录员工所属部门名称")
	@ColDefine(type = ColType.VARCHAR,width = 255,notNull = true)
    private String dept_name;
    
	@Column
	@Comment("工作日志标题")
	@ColDefine(type = ColType.VARCHAR,width = 255,notNull = true)
    private String log_title;
    
	@Column
	@Comment("工作日志内容")
	@ColDefine(type = ColType.TEXT,notNull = true)
    private String log_content;
    
	@Column
	@Comment("创建时间")
	@ColDefine(type = ColType.DATETIME,notNull = true)
    private Timestamp create_time;
    
	
    public void setLog_id (Integer log_id){
    	this.log_id = log_id;
    }
    
	/**
 	 * 日志编号
 	 */
    public Integer  getLog_id(){
    	return this.log_id;
    }
    
    public void setLog_date (String log_date){
    	this.log_date = log_date;
    }
    
	/**
 	 * 当天日期
 	 */
    public String  getLog_date(){
    	return this.log_date;
    }
    
    public void setSys_user_id (Integer sys_user_id){
    	this.sys_user_id = sys_user_id;
    }
    
	/**
 	 * 当前登录员工的账户ID
 	 */
    public Integer  getSys_user_id(){
    	return this.sys_user_id;
    }
    
    public void setUser_name (String user_name){
    	this.user_name = user_name;
    }
    
	/**
 	 * 当前登录员工的账户名称
 	 */
    public String  getUser_name(){
    	return this.user_name;
    }
    
    public void setDept_id (Integer dept_id){
    	this.dept_id = dept_id;
    }
    
	/**
 	 * 当前登录员工所属部门编号
 	 */
    public Integer  getDept_id(){
    	return this.dept_id;
    }
    
    public void setDept_name (String dept_name){
    	this.dept_name = dept_name;
    }
    
	/**
 	 * 当前登录员工所属部门名称
 	 */
    public String  getDept_name(){
    	return this.dept_name;
    }
    
    public void setLog_title (String log_title){
    	this.log_title = log_title;
    }
    
	/**
 	 * 工作日志标题
 	 */
    public String  getLog_title(){
    	return this.log_title;
    }
    
    public void setLog_content (String log_content){
    	this.log_content = log_content;
    }
    
	/**
 	 * 工作日志内容
 	 */
    public String  getLog_content(){
    	return this.log_content;
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