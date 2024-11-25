package com.hancloud.hancloud.member.service;

import com.hancloud.hancloud.member.dto.request.CreateMemberRequest;
import com.hancloud.hancloud.member.dto.response.CreatedApiMemberResponse;

/**
 * 멤버 관련 서비스
 *
 * @author 한민기
 */
public interface MemberService {
    /**
     * 해당 유저 만들기
     *
     * @param createMemberRequest 유저 관련 request
     */
    void addMember(CreateMemberRequest createMemberRequest);

    /**
     * ApiMember 만들기
     *
     */
    CreatedApiMemberResponse addApiMember(String memberId);
}
