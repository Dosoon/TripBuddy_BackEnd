package com.tripbuddy.notify.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripbuddy.jwt.service.JwtService;
import com.tripbuddy.memo.model.dto.MemoDto;
import com.tripbuddy.notice.model.dto.NoticeDto;
import com.tripbuddy.notify.model.dto.NotifyDto;
import com.tripbuddy.notify.model.service.NotifyService;
import com.tripbuddy.user.model.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/notifys")
@Api(tags = { "알림 컨트롤러" })
public class NotifyController {
	
	@Autowired
	private NotifyService notifyService;
	@Autowired
	private JwtService jwtService;
	
	@ApiOperation(value = "알림 정보", notes = "페이징 처리를 위한 page를 받아 알림 정보들을 10개씩 가져옵니다.")
	@ApiImplicitParam(name = "page", value = "현재 페이지 값", required = true, dataType = "int", dataTypeClass = Integer.class, paramType = "query" , example = "1" )
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"notifyId\": 30,\r\n" + 
            		"    \"userId\": 25,\r\n" + 
            		"    \"type\": \"invite\",\r\n" + 
            		"    \"read\": false,\r\n" + 
            		"    \"senderId\": 25,\r\n" + 
            		"    \"targetId\": 27,\r\n" + 
            		"    \"notifyMsg\": \"관리자님이 회원님을 [즐거운 여행] 플랜에 초대했습니다.\"\r\n" + 
            		"  }\r\n" + 
            		"]"
        )
    )
)
	@GetMapping
	public ResponseEntity<Object> getNotifys(@ApiIgnore @RequestParam Map<String, Object> req, HttpServletRequest request) {
		List<NotifyDto> list = null;
		try {
			System.out.println(req.toString());

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) (session.getAttribute("userinfo"))).getUserId();
			req.put("userId", userId);
			int page = Integer.parseInt(String.valueOf((String)req.get("page")));
			req.put("offset", ((page - 1) * 10));
			req.put("curLoadCount", (page * 10));
			
			list = notifyService.getNotifys(req);
			notifyService.updateRead(req);
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "최근 알림 정보", notes = "가장 최근 알림 3개를 가져옵니다. read처리를 하지 않습니다.")
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"notifyId\": 30,\r\n" + 
            		"    \"userId\": 25,\r\n" + 
            		"    \"type\": \"invite\",\r\n" + 
            		"    \"read\": false,\r\n" + 
            		"    \"senderId\": 25,\r\n" + 
            		"    \"targetId\": 27,\r\n" + 
            		"    \"notifyMsg\": \"관리자님이 회원님을 [즐거운 여행] 플랜에 초대했습니다.\"\r\n" + 
            		"  }\r\n" + 
            		"]"
        )
    )
)
	@GetMapping("/recent")
	public ResponseEntity<Object> getRecentNotifys(@ApiIgnore @RequestParam Map<String, Object> req, HttpServletRequest request) {
		List<NotifyDto> list = null;
		try {

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) (session.getAttribute("userinfo"))).getUserId();
			req.put("userId", userId);
			
			list = notifyService.getRecentNotifys(req);
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	

	@ApiOperation(value = "미열람 알림 여부", notes = "아직 읽지 않은 알림이 있는지 확인합니다.")
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"notifyId\": 30,\r\n" + 
            		"    \"userId\": 25,\r\n" + 
            		"    \"type\": \"invite\",\r\n" + 
            		"    \"read\": false,\r\n" + 
            		"    \"senderId\": 25,\r\n" + 
            		"    \"targetId\": 27,\r\n" + 
            		"    \"notifyMsg\": \"관리자님이 회원님을 [즐거운 여행] 플랜에 초대했습니다.\"\r\n" + 
            		"  }\r\n" + 
            		"]"
        )
    )
)
	@GetMapping("/notread")
	public ResponseEntity<Object> getNotreadNotifys(@ApiIgnore @RequestParam Map<String, Object> req, HttpServletRequest request) {
		List<NotifyDto> list = null;
		try {
//			System.out.println(req.toString());

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) (session.getAttribute("userinfo"))).getUserId();
			req.put("userId", userId);
			
			int result = notifyService.getNotreadNotifys(req);
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "알림 내 버튼 삭제", notes = "알림을 비활성화 합니다.")
    @ApiImplicitParam(name = "req",
    value = "notifyId(required)",
    required = true ,
    dataType = "Map<String, Object>", 
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"notifyId\" : 37 }")
	@PutMapping
	public ResponseEntity<Object> disableNotify(@RequestBody Map<String, Object> req) {
		try {
			notifyService.disableNotify(req);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}

	@ApiOperation(value = "초대 알림 수락", notes = "초대된 플랜의 모든 알림에 대해 수락됨 처리를 합니다.")
    @ApiImplicitParam(name = "req",
    value = "notifyId(required)",
    required = true ,
    dataType = "Map<String, Object>", 
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"targetId\" : 37 }")
	@PutMapping("/accept")
	public ResponseEntity<Object> acceptNotify(@RequestBody Map<String, Object> req) {
		try {
			notifyService.acceptNotify(req);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "초대 알림 전송", notes = "여행 계획에 대해 다른 유저에게 초대 알림을 보냅니다.")
    @ApiImplicitParam(name = "req",
    value = "planId(required), receiverId(required) : userId",
    required = true ,
    dataType = "Map<String, Object>", 
    dataTypeClass = Object.class,
    paramType = "body",
    example = "  { \"invitedUsers\" : [25,26,30,35] , \"planId\" : 27 }" )
	@PostMapping("/invite")
	public ResponseEntity<Object> postInviteNotify(@RequestBody Map<String, Object> req, HttpServletRequest request) {
		try {
			List<Integer> invitedUsers = (List<Integer>)(req.get("invitedUsers"));

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
			
			req.put("senderId", userId);
			for (Integer user : invitedUsers) {
				req.put("receiverId", user);
				notifyService.postInviteNotify(req);
			}
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	
}
