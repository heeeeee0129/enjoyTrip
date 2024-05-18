package com.ssafy.message.service;

import org.springframework.stereotype.Service;

import com.ssafy.message.dao.MessageDao;

@Service
public class MessageServiceImpl implements MessageService{
	
	private MessageDao messageDao;

	public MessageServiceImpl(MessageDao messageDao) {
		super();
		this.messageDao = messageDao;
	}
	
	
}
