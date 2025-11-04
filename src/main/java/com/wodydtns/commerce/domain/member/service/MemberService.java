package com.wodydtns.commerce.domain.member.service;

import java.util.List;

import com.wodydtns.commerce.domain.member.dto.CreateMemberRequest;
import com.wodydtns.commerce.domain.member.dto.DeleteMemberRequest;
import com.wodydtns.commerce.domain.member.dto.MemberResponseDto;
import com.wodydtns.commerce.domain.member.dto.UpdateMemberRequest;

public interface MemberService {

    void createMember(CreateMemberRequest createMemberRequest);

    void updateMember(UpdateMemberRequest updateMemberRequest);

    void deleteMember(DeleteMemberRequest deleteMemberRequest);

    MemberResponseDto findMember(Long id);

    List<MemberResponseDto> getMembers();
}
