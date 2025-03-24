package com.hancloud.hancloud.storage.repository;

/**
 * 저장소 관련 customer 레포지토리
 *
 * @author 한민기
 *
 */
public interface HanStorageCustomRepository {
	/**
	 * 유저가 사용할수 있는지 확인하는 메소드
	 *
	 * @param memberId 멤버 아이디
	 * @param path    파일의 path
	 * @return 사용가능 여부
	 */
	boolean isMemberPathCan(String memberId, String path);
}
