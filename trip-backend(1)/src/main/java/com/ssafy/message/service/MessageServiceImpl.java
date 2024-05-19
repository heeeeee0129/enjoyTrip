package com.ssafy.message.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.message.dao.MessageDao;
import com.ssafy.message.dto.Message;

@Service
public class MessageServiceImpl implements MessageService{
	
	private MessageDao messageDao;

	public MessageServiceImpl(MessageDao messageDao) {
		super();
		this.messageDao = messageDao;
	}

	@Override
	public List<Message> getRecieveMessages(String userId) throws SQLException {
		return messageDao.getRecieveMessages(userId);
	}

	@Override
	public List<Message> getSendMessages(String userId) throws SQLException {
		return messageDao.getSendMessages(userId);
	}

	@Override
	public int writeMessage(Message message) throws SQLException {
		return messageDao.writeMessage(message);
	}

	@Override
	public Message getMessage(int messageNo) throws SQLException {
		return messageDao.getMessage(messageNo);
	}

	@Override
	public int updateRead(Message message) throws SQLException {
		return messageDao.updateRead(message);
	}
	
	
}
