package com.hancloud.hancloud.storage.dto.enums;

public enum StorageAuth {
	all, nothing, read, write;

	public static boolean canNonMemberRead(StorageAuth auth) {
		return switch (auth) {
			case all, read -> true;
			default -> false;
		};
	}
}
