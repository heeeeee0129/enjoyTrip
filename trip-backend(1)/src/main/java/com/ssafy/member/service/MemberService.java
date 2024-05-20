package com.ssafy.member.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.member.dto.Member;

public interface MemberService {

	public int join(Member member);
	public Member loginMember(Member member);
	public int modify(Member member);
	public int expire(String id);
	public String findPass(String id);
	public Member getUser(String id);
	public List<Member> getUserList() throws SQLException;
	public List<Member> getSuggestList(String userId) throws SQLException;
}
