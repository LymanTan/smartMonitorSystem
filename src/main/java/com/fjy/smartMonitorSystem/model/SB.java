package com.fjy.smartMonitorSystem.model;

import java.io.Serializable;

/**
 * 封装信息
 * @author Tomato
 * netty使用该类封装，保证包名、类名等信息一致
 * @param <T>
 */


public class SB<T> implements Serializable{

	private int code;
	private String msg;
	private T data;
	public SB() {
	}
	public SB(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	@Override
	public String toString() {
		return "Test [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
}
