package com.ssafy.message.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.message.dto.Message;

public interface MessageService {

	List<Message> getRecieveMessages(String userId) throws SQLException;

	List<Message> getSendMessages(String userId) throws SQLException;

	int writeMessage(Message message) throws SQLException;

	Message getMessage(int messageNo) throws SQLException;

	int updateRead(Message message) throws SQLException;

}
