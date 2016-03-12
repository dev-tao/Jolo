package com.jolo.cms.model;

/**
 * 定义自定义异常
 * @author devtao
 *
 */
public class CmsException  extends RuntimeException{
	private static final long serivalVersionID =1L;
	
	public CmsException(){
		super();
	}
	
	public CmsException(String message ,Throwable cause){
		super(message,cause);
	}
	
	public CmsException(String message){
		super(message);
	}
	
	public CmsException(Throwable cause){
		super(cause);
	}
	
}
