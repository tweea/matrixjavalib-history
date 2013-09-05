/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 用户信息。
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "UserInfo.findAll", query = "select o from UserInfo o"),
	@NamedQuery(name = "UserInfo.findAll.size", query = "select count(o) from UserInfo o")
})
@Table(name = "TEST_USER")
public class UserInfo
	implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1218387201153161009L;

	@Id
	@Column(nullable = false)
	private String yhm;

	private String mm;

	private Long nl;

	private String xb;

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getMm() {
		return mm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public Long getNl() {
		return nl;
	}

	public void setNl(Long nl) {
		this.nl = nl;
	}

	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}
}
