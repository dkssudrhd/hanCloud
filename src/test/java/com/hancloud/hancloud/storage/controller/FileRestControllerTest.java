package com.hancloud.hancloud.storage.controller;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.multipart.MultipartFile;

import com.hancloud.hancloud.BaseDocumentTest;
import com.hancloud.hancloud.storage.service.FileService;

@WebMvcTest(
	controllers = FileRestController.class
)
class FileRestControllerTest extends BaseDocumentTest {
	@MockBean
	private FileService fileService;

	@Test
	@DisplayName("여러개의 파일 업로드")
	void uploadFilesTest() throws Exception {

		MockMultipartFile file1 = new MockMultipartFile("files", "file1.txt", "text/plain",
			"Sample content 1".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("files", "file2.txt", "text/plain",
			"Sample content 2".getBytes());

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
			.andDo(document(snippetPath,
				"여러 파일 올리기",
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

	@Test
	@DisplayName("한개 파일 업로드")
	void uploadFileTest() throws Exception {

		MockMultipartFile file1 = new MockMultipartFile("file", "file1.txt", "text/plain",
			"Sample content 1".getBytes());

		// Mocking FileService
		doNothing().when(fileService).storage(any(MultipartFile.class), eq("/test"));

		this.mockMvc.perform(RestDocumentationRequestBuilders.multipart("/storage")
				.file(file1)
				.param("path", "/test")
				.header("API-ID", "API id")
				.header("API-PASSWORD", "API password")
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isCreated())
			.andDo(document(snippetPath,
				"파일 하나 올리기",
				requestHeaders( // 요청 헤더 문서화
					headerWithName("API-ID").description("API 아이디"),
					headerWithName("API-PASSWORD").description("API 비밀번호")
				),
				requestParts( // Multipart 요청의 모든 부분 문서화
					partWithName("file").description("업로드할 파일")
				),
				responseFields( // 응답 필드 문서화
					fieldWithPath("header.resultCode").type(JsonFieldType.NUMBER).description("결과 코드"),
					fieldWithPath("header.successful").type(JsonFieldType.BOOLEAN).description("성공 여부"),
					fieldWithPath("body.data.message").type(JsonFieldType.STRING).description("성공 메시지")
				)
			));
	}

	@Test
	@DisplayName("폴더 생성")
	void uploadFolderTest() throws Exception {
		String path = "/test";
		doNothing().when(fileService).storageAdd(path);

		this.mockMvc.perform(RestDocumentationRequestBuilders.post("/storage/folder")
				.header("API-ID", "API id")
				.header("API-PASSWORD", "API password")
				.param("path", path)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isCreated())
			.andDo(document(snippetPath,
				"폴더 만들기",
				requestHeaders( // 요청 헤더 문서화
					headerWithName("API-ID").description("API 아이디"),
					headerWithName("API-PASSWORD").description("API 비밀번호")
				),
				responseFields( // 응답 필드 문서화
					fieldWithPath("header.resultCode").type(JsonFieldType.NUMBER).description("결과 코드"),
					fieldWithPath("header.successful").type(JsonFieldType.BOOLEAN).description("성공 여부"),
					fieldWithPath("body.data.message").type(JsonFieldType.STRING).description("성공 메시지")
				)
			));
	}

	@Test
	@DisplayName("파일 하나 삭제")
	void deleteFileTest() throws Exception {
		String path = "/test";
		doNothing().when(fileService).deleteFile(path);

		this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/storage")
				.header("API-ID", "API id")
				.header("API-PASSWORD", "API password")
				.param("path", path)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isNoContent())
			.andDo(document(snippetPath,
				"파일 하나 삭제하기",
				requestHeaders( // 요청 헤더 문서화
					headerWithName("API-ID").description("API 아이디"),
					headerWithName("API-PASSWORD").description("API 비밀번호")
				),
				responseFields( // 응답 필드 문서화
					fieldWithPath("header.resultCode").type(JsonFieldType.NUMBER).description("결과 코드"),
					fieldWithPath("header.successful").type(JsonFieldType.BOOLEAN).description("성공 여부"),
					fieldWithPath("body.data.message").type(JsonFieldType.STRING).description("성공 메시지")
				)
			));
	}

	@Test
	@DisplayName("폴더 삭제")
	void deleteFolderTest() throws Exception {
		String path = "/test";
		doNothing().when(fileService).storageRemove(path);

		this.mockMvc.perform(RestDocumentationRequestBuilders.delete("/storage/folder")
				.header("API-ID", "API id")
				.header("API-PASSWORD", "API password")
				.param("path", path)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isNoContent())
			.andDo(document(snippetPath,
				"폴더 삭제하기",
				requestHeaders( // 요청 헤더 문서화
					headerWithName("API-ID").description("API 아이디"),
					headerWithName("API-PASSWORD").description("API 비밀번호")
				),
				responseFields( // 응답 필드 문서화
					fieldWithPath("header.resultCode").type(JsonFieldType.NUMBER).description("결과 코드"),
					fieldWithPath("header.successful").type(JsonFieldType.BOOLEAN).description("성공 여부"),
					fieldWithPath("body.data.message").type(JsonFieldType.STRING).description("성공 메시지")
				)
			));
	}
}
