package com.hancloud.hancloud.storage.service;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.hancloud.hancloud.storage.exception.FileNameDuplicationException;
import com.hancloud.hancloud.storage.exception.FileNotFoundException;
import com.hancloud.hancloud.storage.exception.FileUploadException;

/**
 *
 * 파일 관련 서비스
 *
 * @author 한민기
 *
 */
public interface FileService {
	/**
	 * 파일 단체 저장
	 *
	 * @param files 단체 저장할 파일
	 * @param filePath 파일의 저장 위치
	 */
	void storage(MultipartFile[] files, String filePath);

	/**
	 * 파일 한개 저장
	 * 파일 이름 확인 -> 저장할 위치 확인 -> 파일 중복 확인 -> 파일 저장
	 *
	 * @param file 저장할 파일
	 * @param filePath 파일의 위치
	 */
	void storage(MultipartFile file, String filePath);

	/**
	 * multipartFile 에서 파일의 확장자 얻기
	 *
	 * @param file 얻을 파일
	 * @return 확장자 ex) .png
	 * @throws FileNotFoundException 파일의 확장자가 없을 경우
	 */
	String getFileExtension(MultipartFile file);

	/**
	 * 파일의 이름 추출
	 *
	 * @param file 추출할 파일
	 * @return 파일의 이름
	 * @throws FileUploadException 파일의 이름이 null 일 경우
	 */
	String getFileName(MultipartFile file);

	/**
	 * 저장할 파일의 경로 찾기
	 *
	 * @param path 저장할 파일의 경로
	 * @throws FileNotFoundException 파일의 경로가 없을경우
	 */
	void filePathIsExists(Path path);

	/**
	 * 이름 중복 체크
	 *
	 * @param path 파일의 위치
	 * @throws FileNameDuplicationException 중복일 경우
	 */
	void fileNameDuplicationCheck(Path path);

	/**
	 * 파일 불러오기
	 *
	 * @param filePath 불러올 파일의 위치
	 * @return 파일
	 * @throws FileNotFoundException 파일을 불러 올 수 없을 떄
	 */
	Resource loadFileAsResource(String filePath);

	/**
	 * 파일 삭제
	 *
	 * @param filePath 파일의 위치
	 *
	 */
	void deleteFile(String filePath);

	/**
	 * 파일의 저장소 만들기
	 *
	 * @param filePath 저장소 파일의 이름 및 위치
	 */
	void storageAdd(String filePath);

	/**
	 * 저장소 삭제 (저장소에 있는 파일 다 삭제)
	 *
	 * @param filePath 저장소에 있는 파일 다 삭제
	 */
	void storageRemove(String filePath);

	/**
	 * 이미지인지 확인
	 *
	 * @param filePath 파일의 위치
	 */
	void isImage(String filePath);
}
