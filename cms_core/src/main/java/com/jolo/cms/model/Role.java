package com.jolo.cms.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色对象 
 * 用来对应可以访问的功能 
 * @author devtao
 *
 */
@Entity
@Table(name="t_role")
public class Role {
	/**
	 * 角色id
	 */
	private int id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色类型 枚举类型
	 */
	private RoleType roleType;
	
	public Role(int id, String name, RoleType roleType) {
		super();
		this.id = id;
		this.name = name;
		this.roleType = roleType;
	}

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Enumerated(EnumType.ORDINAL)
	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
	
}
