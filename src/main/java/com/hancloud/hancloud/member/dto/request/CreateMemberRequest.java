package com.hancloud.hancloud.member.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CreateMemberRequest(
	@NotNull(message = "이메일을 입력해주세요.")
	String email,

	@NotNull(message = "비밀번호를 입력해주세요.")
	@Size(min = 6, max = 50, message = "비밀번호는 6자 이상 50자 이하로 설정해주세요.")
	String password,

	@NotNull(message = "이름을 입력해주세요.")
	@Size(min = 1, max = 10, message = "이름은 1자 이상 10자 이하로 설정해주세요.")
	String name
) {
}
