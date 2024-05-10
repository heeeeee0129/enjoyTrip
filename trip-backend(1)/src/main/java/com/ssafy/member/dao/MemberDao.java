package com.ssafy.member.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.dto.Member;

@Mapper
public interface MemberDao {

	public int join(Member member);
	public Member loginMember(Member member);
	public int modify(Member member);
	public int expire(String id);
	public String findPass(String id);
	public int checkId(String id); 
}
