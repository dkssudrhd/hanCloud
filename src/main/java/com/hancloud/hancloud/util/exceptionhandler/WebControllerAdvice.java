package com.hancloud.hancloud.util.exceptionhandler;


import com.hancloud.hancloud.member.exception.ApiMemberNotFoundException;
import com.hancloud.hancloud.storage.exception.*;
import com.hancloud.hancloud.util.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
public class WebControllerAdvice {

    /**
     * 내가 놓치고 있는 예외
     *
     * @param ex 예외
     * @return 예외 메시지
     */
    @ExceptionHandler(
            RuntimeException.class
    )
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<ErrorResponseForm> runtimeExceptionHandler(Exception ex) {
        log.error("{}: {}", ex.getClass().getName(), ex.getMessage());

        return ApiResponse.fail(500,
               ErrorResponseForm.builder()
                       .title(ex.getMessage() + "원인을 알수 없는 오류가 발생하였습니다.")
                       .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                       .timestamp(ZonedDateTime.now().toString())
                       .build()
        );
    }

    /**
     * NOT_FOUND 처리 메소드
     *
     * @return 예외 메시지
     */
    @ExceptionHandler({
            FileNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<ErrorResponseForm> notFoundHandler(Exception ex) {
        return ApiResponse.notFoundFail(
                ErrorResponseForm.builder()
                        .title(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(ZonedDateTime.now().toString())
                        .build()
        );
    }

    /**
     * BAD_REQUEST 처리 메소드
     *
     * @param ex 예외 종류
     * @return 예외 메시지
     */
    @ExceptionHandler({
            ApiMemberNotFoundException.class,
            FileAlreadyExistsException.class,
            FileCreateException.class,
            FileDeleteFailedException.class,
            FileNameDuplicationException.class,
            FileUploadException.class,
            FileIsNotImageException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponseForm> badRequestHandler(Exception ex) {
        return ApiResponse.badRequestFail(
                ErrorResponseForm.builder()
                        .title(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(ZonedDateTime.now().toString())
                        .build()
        );

    }
}
