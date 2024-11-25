package com.hancloud.hancloud.team.exception;

public class TeamMemberAlreadyExistsException extends RuntimeException {
    public TeamMemberAlreadyExistsException(String message) {
        super(message);
    }
}
