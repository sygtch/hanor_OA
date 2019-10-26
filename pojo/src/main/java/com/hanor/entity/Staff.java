package com.hanor.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.*;

/**
 * staff实体
 * @author 郭天驰
 *
 */
@Table(Staff.TABLE_NAME)
@Comment("staff")
public class Staff implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "staff";
	
	/**
 	 * 员工编号
 	 */
    public static final String STAFF_ID = "staff_id";
	/**
 	 * 员工名称
 	 */
    public static final String STAFF_NAME = "staff_name";
	/**
 	 * 员工后台登录账户
 	 */
    public static final String STAFF_ACCOUNT = "staff_account";
	/**
 	 * 员工电话
 	 */
    public static final String STAFF_PHONE = "staff_phone";
	/**
 	 * 取值：dept-部门经理 user-普通员工
 	 */
    public static final String STAFF_TYPE = "staff_type";
	/**
 	 * 部门编号
 	 */
    public static final String DEPT_ID = "dept_id";
	/**
 	 * 部门名称
 	 */
    public static final String DEPT_NAME = "dept_name";
	/**
 	 * 关联sys_user表的user_id
 	 */
    public static final String SYS_USER_ID = "sys_user_id";
	/**
 	 * 创建时间
 	 */
    public static final String CREATE_TIME = "create_time";
	
	@Id
	@Comment("员工编号")
	@ColDefine(type = ColType.INT,width = 8,notNull = true)
    private Integer staff_id;

	@Name
	@Column
	@Comment("员工名称")
	@ColDefine(type = ColType.VARCHAR,width = 64)
    private String staff_name;
    
	@Column
	@Comment("员工后台登录账户")
	@ColDefine(type = ColType.VARCHAR,width = 64,notNull = true)
    private String staff_account;
    
	@Column
	@Comment("员工电话")
	@ColDefine(type = ColType.VARCHAR,width = 20,notNull = true)
    private String staff_phone;
    
	@Column
	@Comment("取值：dept-部门经理 user-普通员工")
	@ColDefine(type = ColType.VARCHAR,width = 16,notNull = true)
    private String staff_type;
    
	@Column
	@Comment("部门编号")
	@ColDefine(type = ColType.INT,width = 8,notNull = true)
    private Integer dept_id;
    
	@Column
	@Comment("部门名称")
	@ColDefine(type = ColType.VARCHAR,width = 255,notNull = true)
    private String dept_name;
    
	@Column
	@Comment("关联sys_user表的user_id")
	@ColDefine(type = ColType.INT,width = 8,notNull = true)
    private Integer sys_user_id;
    
	@Column
	@Comment("创建时间")
	@ColDefine(type = ColType.DATETIME,notNull = true)
    private Timestamp create_time;
    
	
    public void setStaff_id (Integer staff_id){
    	this.staff_id = staff_id;
    }
    
	/**
 	 * 员工编号
 	 */
    public Integer  getStaff_id(){
    	return this.staff_id;
    }
    
    public void setStaff_name (String staff_name){
    	this.staff_name = staff_name;
    }
    
	/**
 	 * 员工名称
 	 */
    public String  getStaff_name(){
    	return this.staff_name;
    }
    
    public void setStaff_account (String staff_account){
    	this.staff_account = staff_account;
    }
    
	/**
 	 * 员工后台登录账户
 	 */
    public String  getStaff_account(){
    	return this.staff_account;
    }
    
    public void setStaff_phone (String staff_phone){
    	this.staff_phone = staff_phone;
    }
    
	/**
 	 * 员工电话
 	 */
    public String  getStaff_phone(){
    	return this.staff_phone;
    }
    
    public void setStaff_type (String staff_type){
    	this.staff_type = staff_type;
    }
    
	/**
 	 * 取值：dept-部门经理 user-普通员工
 	 */
    public String  getStaff_type(){
    	return this.staff_type;
    }
    
    public void setDept_id (Integer dept_id){
    	this.dept_id = dept_id;
    }
    
	/**
 	 * 部门编号
 	 */
    public Integer  getDept_id(){
    	return this.dept_id;
    }
    
    public void setDept_name (String dept_name){
    	this.dept_name = dept_name;
    }
    
	/**
 	 * 部门名称
 	 */
    public String  getDept_name(){
    	return this.dept_name;
    }
    
    public void setSys_user_id (Integer sys_user_id){
    	this.sys_user_id = sys_user_id;
    }
    
	/**
 	 * 关联sys_user表的user_id
 	 */
    public Integer  getSys_user_id(){
    	return this.sys_user_id;
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