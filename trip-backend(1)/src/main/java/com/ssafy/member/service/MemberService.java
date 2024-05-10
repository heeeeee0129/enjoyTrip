package com.ssafy.member.service;

import com.ssafy.member.dto.Member;

public interface MemberService {

	public int join(Member member);
	public Member loginMember(Member member);
	public int modify(Member member);
	public int expire(String id);
	public String findPass(String id);
	public Member getUser(String id);
}
