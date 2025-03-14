package com.hancloud.hancloud.storage.service;

import com.hancloud.hancloud.storage.dto.response.MemberPathResponse;

public interface StorageService {
	MemberPathResponse canUse(String memberId, String path);
}
