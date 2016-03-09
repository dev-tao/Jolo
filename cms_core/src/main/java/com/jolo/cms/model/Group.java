package com.jolo.cms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户组对象，使用该对象获取可以发布文章的栏目信息
 * @author devtao
 *
 */
@Entity
@Table(name="t_group")
public class Group {
	/**
	 * 组id
	 */
	private int id;
	/**
	 * 组名称
	 */
	private String name;
	/**
	 * 组描述
	 */
	private String description;
	
	
	public Group(int id, String name, String desc) {
		super();
		this.id = id;
		this.name = name;
		this.description = desc;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String desc) {
		this.description = desc;
	}
	
}
