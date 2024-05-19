package com.ssafy.message.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.message.dto.Message;

@Mapper
public interface MessageDao {

	List<Message> getRecieveMessages(String userId) throws SQLException;

	List<Message> getSendMessages(String userId) throws SQLException;

	int writeMessage(Message message) throws SQLException;

	Message getMessage(int messageNo) throws SQLException;

	int updateRead(Message message) throws SQLException;

}
