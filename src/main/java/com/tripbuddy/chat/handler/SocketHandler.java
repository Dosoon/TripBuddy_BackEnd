package com.tripbuddy.chat.handler;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.tripbuddy.chat.user.ChatDto;
import com.tripbuddy.chat.user.NetworkService;
import com.tripbuddy.chat.user.NoticeDto;
import com.tripbuddy.chat.user.User;

@Component
public class SocketHandler extends TextWebSocketHandler {
	
	private AtomicInteger idx = null;
	private ObjectMapper objectMapper;
	
	@Autowired
	private NetworkService networkService;
	
	public SocketHandler() {
		super();
		new HashMap<String, User>();
		this.objectMapper = JsonMapper.builder()
			    .enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER)
			    .build();
		this.idx = new AtomicInteger(0);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
		int curIdx = idx.incrementAndGet();
		User user = new User(session, curIdx, null, -1, -1);
		networkService.addSession(user);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage msg) throws Exception {
		super.handleTextMessage(session, msg);
		int curIdx = idx.incrementAndGet();
		
		// ChatDTO 객체에 유저 메시지 맵핑
		ChatDto chat = objectMapper.reader().readValue(msg.getPayload(), ChatDto.class);
		
		// 메시지 타입에 따라 분기
		System.out.println(chat.toString());
		
		if (chat.getType().equals("signin")) {
			System.out.println("---- SIGNIN PROCEDURE ----");
			// 해당 세션에 대해 유저 정보 등록
			User newUser = networkService.getUser(session.getId());
			newUser.setUsername(chat.getUsername());
			newUser.setPlanNum(chat.getPlanNum());
			newUser.setEmojiIdx(chat.getEmojiIdx());
			
			networkService.addUser(session.getId(), newUser);
			
			ChatDto chatDto = new ChatDto();
			chatDto.setType("notice");
			chatDto.setContent(chat.getUsername() + "님이 입장했습니다.");
			chatDto.setUserIdx(newUser.getUserIdx());
			chatDto.setEmojiIdx(chat.getEmojiIdx());
			chatDto.setIdx(curIdx);
			chatDto.setPlanNum(chat.getPlanNum());
			System.out.println(":: WILL SEND :: " + chatDto.toString());
			
			networkService.sendBroadcast(chatDto, chat.getPlanNum());
			
			String state = networkService.getListState(newUser.getPlanNum());
			if (state != null) {
				ChatDto stateChatDto = new ChatDto();
				stateChatDto.setType("path");
				stateChatDto.setContent(state);
				stateChatDto.setIdx(idx.incrementAndGet());
				stateChatDto.setPlanNum(newUser.getPlanNum());
				stateChatDto.setUserIdx(newUser.getUserIdx());
				networkService.sendUnicast(session.getId(), stateChatDto);
			}
			
		} else if (chat.getType().equals("chat")) {
			User curUser = networkService.getUser(session.getId());
			ChatDto chatDto = new ChatDto();
			chatDto.setType("chat");
			chatDto.setContent(chat.getContent());
			chatDto.setUsername(curUser.getUsername());
			chatDto.setIdx(curIdx);
			chatDto.setPlanNum(curUser.getPlanNum());
			chatDto.setUserIdx(curUser.getUserIdx());
			chatDto.setEmojiIdx(curUser.getEmojiIdx());
			networkService.sendBroadcast(chatDto, chat.getPlanNum());
			
		} else if (chat.getType().equals("tab")) {
			ChatDto chatDto = new ChatDto();
			int planNum = chat.getPlanNum();
			
			chatDto.setType("tab");
			chatDto.setContent(chat.getContent());
			chatDto.setIdx(curIdx);
			chatDto.setPlanNum(chat.getPlanNum());
			chatDto.setUserIdx(networkService.getUser(session.getId()).getUserIdx());
			
			networkService.sendBroadcastExcept(chatDto, planNum);
		} else if (chat.getType().equals("path")) {
			ChatDto chatDto = new ChatDto();
			int planNum = chat.getPlanNum();
			
			chatDto.setType("path");
			chatDto.setContent(chat.getContent());
			chatDto.setIdx(curIdx);
			chatDto.setPlanNum(chat.getPlanNum());
			chatDto.setUserIdx(networkService.getUser(session.getId()).getUserIdx());
			
			networkService.setListState(planNum, chatDto.getContent());
			System.out.println(chatDto.getContent().toString());
			networkService.sendBroadcastExcept(chatDto, planNum);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
		int curIdx = idx.incrementAndGet();
		
		ChatDto chatDto = new ChatDto();
		User removedUser = networkService.getUser(session.getId());
		chatDto.setType("notice");
		chatDto.setContent(removedUser.getUsername() + "님이 퇴장했습니다.");
		chatDto.setIdx(removedUser.getUserIdx());
		chatDto.setEmojiIdx(removedUser.getEmojiIdx());
		chatDto.setIdx(curIdx);
		chatDto.setPlanNum(removedUser.getPlanNum());
		System.out.println(":: WILL SEND :: " + chatDto.toString());
		int planNum = removedUser.getPlanNum();
		
		networkService.removeUser(session.getId());
		networkService.sendBroadcast(chatDto, planNum);
	}
	
}
