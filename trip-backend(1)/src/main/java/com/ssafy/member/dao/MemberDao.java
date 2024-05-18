package com.ssafy.member.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.dto.Member;

@Mapper
public interface MemberDao {

	public int join(Member member);
	public Member loginMember(Member member);
	public int modify(Member member);
	public int expire(String id);
	public String findPass(String id);
	public Member getUser(String id);
	public List<Member> getUserList() throws SQLException; 
}
