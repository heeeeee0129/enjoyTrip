package com.ssafy.member.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.member.dao.MemberDao;
import com.ssafy.member.dto.Member;

@Service
public class MemberServiceImpl implements MemberService {
	
	
	private MemberDao memberDao;
	
	public MemberServiceImpl(MemberDao memberDao) {
		super();
		this.memberDao = memberDao;
	}

	@Override
	public int join(Member member) {
		return memberDao.join(member);
	}

	@Override
	public Member loginMember(Member member) {
		return memberDao.loginMember(member);
	}

	@Override
	public int modify(Member member) {
		return memberDao.modify(member);
	}

	@Override
	public int expire(String id) {
		return memberDao.expire(id);
	}

	@Override
	public String findPass(String id) {
		return memberDao.findPass(id);
	}

	@Override
	public Member getUser(String id) {
		return memberDao.getUser(id);
	}

	@Override
	public List<Member> getUserList() throws SQLException {
		return memberDao.getUserList();
	}
}
