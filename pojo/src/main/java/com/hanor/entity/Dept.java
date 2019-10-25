package com.hanor.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * dept实体
 * @author 郭天驰
 *
 */
@Table(Dept.TABLE_NAME)
@Comment("dept")
public class Dept implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public static final String TABLE_NAME = "dept";
	
	/**
 	 * 部门编号
 	 */
    public static final String DEPT_ID = "dept_id";
	/**
 	 * 部门名称
 	 */
    public static final String DEPT_NAME = "dept_name";
	/**
 	 * 排序
 	 */
    public static final String SORT_NO = "sort_no";
	/**
 	 * 创建时间
 	 */
    public static final String CREATE_TIME = "create_time";
	
	@Id
	@Comment("部门编号")
	@ColDefine(type = ColType.INT,width = 8,notNull = true)
    private Integer dept_id;
    
	@Column
	@Comment("部门名称")
	@ColDefine(type = ColType.VARCHAR,width = 255)
    private String dept_name;
    
	@Column
	@Comment("排序")
	@ColDefine(type = ColType.INT,width = 2,notNull = true)
    private Integer sort_no;
    
	@Column
	@Comment("创建时间")
	@ColDefine(type = ColType.DATETIME,notNull = true)
    private Timestamp create_time;
    
	
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
    
    public void setSort_no (Integer sort_no){
    	this.sort_no = sort_no;
    }
    
	/**
 	 * 排序
 	 */
    public Integer  getSort_no(){
    	return this.sort_no;
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