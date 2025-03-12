package com.hancloud.hancloud.member.exception;

public class MemberAlreadyExistsException extends RuntimeException {
	public MemberAlreadyExistsException(String message) {
		super(message);
	}
}
