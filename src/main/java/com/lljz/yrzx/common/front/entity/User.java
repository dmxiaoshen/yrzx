package com.lljz.yrzx.common.front.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lljz.yrzx.base.entity.BaseEntity;

@Entity
@Table(name = "user")
public class User extends BaseEntity {

	/**  */
	private static final long serialVersionUID = -7480817165684973187L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
