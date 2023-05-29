package com.tripbuddy.chat.user;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

@Service
public class NetworkService {
	
	private static ConcurrentHashMap<String, User> sessionMap;
	private static ConcurrentHashMap<Integer, AtomicInteger> userCountByPlanNumberMap;
	private static ConcurrentHashMap<Integer, String> curListStateByPlanNumberMap;
	private static ObjectMapper objectMapper;
	
	public NetworkService() {
		super();
		// SessionID와 User 객체를 key:value로 저장
		sessionMap = new ConcurrentHashMap<String, User>();
		userCountByPlanNumberMap = new ConcurrentHashMap<Integer, AtomicInteger>();
		curListStateByPlanNumberMap = new ConcurrentHashMap<Integer, String>();
		objectMapper = JsonMapper.builder()
			    .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
			    .build();
	}
	
	public User getUser(String sessionID) {
		return sessionMap.get(sessionID);
	}
	
	public void setListState(int planNum, String state) {
		curListStateByPlanNumberMap.put(planNum, state);
	}
	
	public String getListState(int planNum) {
		return curListStateByPlanNumberMap.get(planNum);
	}

	public boolean addUser(String sessionID, User user) {
		sessionMap.put(sessionID, user);
		
		int planNum = user.getPlanNum();
		
		if (userCountByPlanNumberMap.putIfAbsent(planNum, new AtomicInteger(1)) != null) {
			userCountByPlanNumberMap.get(planNum).incrementAndGet();
		}
		return true;
	}
	
	public void sendUnicast(String sessionID, ChatDto chatDto) {
		TextMessage tm;
		try {
			tm = new TextMessage(objectMapper.writer().writeValueAsString(chatDto));
			sessionMap.get(sessionID).getSession().sendMessage(tm);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendBroadcast(ChatDto chatDto, int planNum) {
		TextMessage tm;
		try {
			tm = new TextMessage(objectMapper.writer().writeValueAsString(chatDto));
			for (ConcurrentHashMap.Entry<String, User> entry : sessionMap.entrySet()) {
				if (entry.getValue().getPlanNum() == planNum) {
					System.out.println("send to in " + entry.getValue().getPlanNum() + " user " + entry.getValue().getUsername());
					try {
					entry.getValue().getSession().sendMessage(tm);
					} catch (Exception e) {}
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendBroadcastExcept(ChatDto chatDto, int planNum) {
		TextMessage tm;
		try {
			tm = new TextMessage(objectMapper.writer().writeValueAsString(chatDto));
			for (ConcurrentHashMap.Entry<String, User> entry : sessionMap.entrySet()) {
				if (entry.getValue().getPlanNum() == planNum) {
					if (entry.getValue().getUserIdx() == chatDto.getUserIdx()) continue;
					System.out.println("send to in " + entry.getValue().getPlanNum() + " user " + entry.getValue().getUsername());
					try {
						entry.getValue().getSession().sendMessage(tm);
					} catch (Exception e) {}
				}
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean removeUser(String sessionID) {
		int planNum = getUser(sessionID).getPlanNum();
		if (userCountByPlanNumberMap.get(planNum).decrementAndGet() == 0) {
			curListStateByPlanNumberMap.remove(planNum);
		}
		
		sessionMap.remove(sessionID);
		return true;
	}

	public void addSession(User user) {
		sessionMap.put(user.getSession().getId(), user);
	}

}
