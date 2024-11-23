package com.hancloud.hancloud.storage.controller;

import com.hancloud.hancloud.BaseDocumentTest;
import com.hancloud.hancloud.storage.dto.response.FileSuccessResponse;
import com.hancloud.hancloud.storage.service.FileService;
import com.hancloud.hancloud.util.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.multipart.MultipartFile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = FileRestController.class
)
class FileRestControllerTest extends BaseDocumentTest {
    @MockBean
    private FileService fileService;

    @Test
    @DisplayName("여러개의 파일 업로드")
    void uploadFilesTest() throws Exception {

        MockMultipartFile file1 = new MockMultipartFile("files", "file1.txt", "text/plain", "Sample content 1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("files", "file2.txt", "text/plain", "Sample content 2".getBytes());

        // Mocking FileService
        doNothing().when(fileService).storage(any(MultipartFile[].class), eq("/test"));

        this.mockMvc.perform(RestDocumentationRequestBuilders.multipart("/storage/multi")
                        .file(file1)
                        .file(file2)
                        .param("path", "/test")
                        .header("API-ID", "API id")
                        .header("API-PASSWORD", "API password")
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andDo(document("file-upload-multi",
                        requestHeaders( // 요청 헤더 문서화
                                headerWithName("API-ID").description("API 아이디"),
                                headerWithName("API-PASSWORD").description("API 비밀번호")
                        ),
                        requestParts( // Multipart 요청의 모든 부분 문서화
                                partWithName("files").description("업로드할 파일")
                        ),
                        responseFields( // 응답 필드 문서화
                                fieldWithPath("header.resultCode").type(JsonFieldType.NUMBER).description("결과 코드"),
                                fieldWithPath("header.successful").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("body.data.message").type(JsonFieldType.STRING).description("성공 메시지")
                        )
                ));
    }


}