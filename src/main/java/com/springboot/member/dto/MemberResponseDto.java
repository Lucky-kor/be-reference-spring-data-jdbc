package com.springboot.member.dto;

import lombok.*;

// TODO 변경: Builder 패턴 적용
@Builder
@Getter
public class MemberResponseDto {
    private long memberId;
    private String email;
    private String name;
    private String phone;
}
