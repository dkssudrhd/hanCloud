package com.hancloud.hancloud.storage.service;

import com.hancloud.hancloud.storage.dto.response.MemberPathResponse;

public interface StorageService {
	MemberPathResponse memberCanUse(String memberId, String path);

	MemberPathResponse nonMemberCanUse(String path);
}
