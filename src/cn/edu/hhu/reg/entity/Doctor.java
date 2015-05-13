package cn.edu.hhu.reg.entity;

import java.io.Serializable;

/**
 * 医生信息
 * @author qlm
 *
 */
public class Doctor implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	/**
	 * 医生姓名
	 */
	private String nickname;
	
	/**
	 * 年龄
	 */
	private Integer age;
	
	/**
	 * 性别 1：男  0：女
	 */
	private Integer gender;
	
	/**
	 * 科室id
	 */
	private Integer departmentId;
	
	private String departmentName;
	
	/**
	 * 介绍
	 */
	private String introduction;
	
	/**
	 * 日最大允许挂号人数
	 */
	private Integer regMax;
	
	
	/**
	 * 状态  0：可以挂号 1：不可挂号
	 */
	private Integer status;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}
	
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public Integer getRegMax() {
		return regMax;
	}

	public void setRegMax(Integer regMax) {
		this.regMax = regMax;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Doctor() {
	}
	
}
