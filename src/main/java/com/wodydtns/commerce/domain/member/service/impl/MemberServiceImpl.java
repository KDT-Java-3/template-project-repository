package com.wodydtns.commerce.domain.member.service.impl;

import com.wodydtns.commerce.domain.member.dto.CreateMemberRequest;
import com.wodydtns.commerce.domain.member.dto.DeleteMemberRequest;
import com.wodydtns.commerce.domain.member.dto.MemberResponseDto;
import com.wodydtns.commerce.domain.member.dto.UpdateMemberRequest;
import com.wodydtns.commerce.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    @Override
    public void createMember(CreateMemberRequest createMemberRequest) {
        throw new UnsupportedOperationException("Unimplemented method 'createMember'");
    }

    @Override
    public void updateMember(UpdateMemberRequest updateMemberRequest) {
        throw new UnsupportedOperationException("Unimplemented method 'updateMember'");
    }

    @Override
    public void deleteMember(DeleteMemberRequest deleteMemberRequest) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteMember'");
    }

    @Override
    public MemberResponseDto findMember(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findMember'");
    }

    @Override
    public List<MemberResponseDto> getMembers() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getMembers'");
    }

}
