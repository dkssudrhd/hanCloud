package com.hancloud.hancloud.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
    Header header;
    Body<T> body;

    public ApiResponse(Header header){
        this.header = header;
    }
    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Header {
        private boolean isSuccessful;
        private int resultCode;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Body<T> {
        private T data;

        public Body(T data) {
            this.data = data;
        }
    }
    public static <T> ApiResponse<T> success(T body) {
        return new ApiResponse<>(
                new Header(true, HttpStatus.OK.value()),
                new Body<>(body));
    }
    public static <T> ApiResponse<T> createdSuccess(T body) {
        return new ApiResponse<>(
                new Header(true, HttpStatus.CREATED.value()),
                new Body<>(body));
    }
    public static <T> ApiResponse<T> deleteSuccess(T body) {
        return new ApiResponse<>(
                new Header(true, HttpStatus.NO_CONTENT.value()),
                new Body<>(body));
    }

    public static <T> ApiResponse<T> fail(int errorCode, T body) {
        return new ApiResponse<>(
                new Header(false, errorCode),
                new Body<>(body));
    }
    public static <T> ApiResponse<T> notFoundFail(T body) {
        return new ApiResponse<>(
                new Header(false, HttpStatus.NOT_FOUND.value()),
                new Body<>(body));
    }
    public static <T> ApiResponse<T> badRequestFail(T body) {
        return new ApiResponse<>(
                new Header(false, HttpStatus.BAD_REQUEST.value()),
                new Body<>(body));
    }

}
