package com.wzfuji.db.entity;

// Generated May 9, 2012 3:14:09 PM by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * MaxentUserProfile generated by hbm2java
 */
@Entity
@Table(name = "MaxentUserProfile", catalog = "movierecommendationsystem")
public class MaxentUserProfile implements java.io.Serializable {

	private int userid;
	private byte[] serializedObject;

	public MaxentUserProfile() {
	}

	public MaxentUserProfile(int userid, byte[] serializedObject) {
		this.userid = userid;
		this.serializedObject = serializedObject;
	}

	@Id
	@Column(name = "userid", unique = true, nullable = false)
	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	@Column(name = "serialized_object", nullable = false)
	@Lob 
	public byte[] getSerializedObject() {
		return this.serializedObject;
	}

	public void setSerializedObject(byte[] serializedObject) {
		this.serializedObject = serializedObject;
	}

}
