package com.tripbuddy.user.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripbuddy.jwt.service.JwtService;
import com.tripbuddy.review.model.dto.ReviewDto;
import com.tripbuddy.user.model.dto.UserDto;
import com.tripbuddy.user.model.service.UserService;
import com.tripbuddy.util.MailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import io.swagger.v3.oas.models.media.MediaType;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/users")
@Api(tags = { "유저 컨트롤러" })
public class UserController {
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MailService mailService;
	
	@ApiOperation(value = "로그인", notes = "아이디와 비밀번호를 받아 로그인을 시도합니다.")
    @ApiImplicitParam(name = "userDto",
    value = "id(required), password(required)",
    required = true ,
    dataType = "Object", 
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"id\" : \"buddy\" "
    		+ ", \"password\" : \"1234\" }" )
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"{\r\n" + 
            		"  \"userId\": 26,\r\n" + 
            		"  \"id\": \"buddy\",\r\n" + 
            		"  \"name\": \"김버디\",\r\n" + 
            		"  \"tel\": \"010-1111-2222\",\r\n" + 
            		"  \"email\": \"buddy@tripbuddy.com\",\r\n" + 
            		"  \"lastAccess\": \"2023-05-02 13:24:06\",\r\n" + 
            		"  \"admin\": false\r\n" + 
            		"}"
        )
    )
)
	@PostMapping("/login")
	public ResponseEntity<Object> login(@ApiIgnore @RequestBody UserDto userDto) {		

		Map<String, Object> res = new HashMap<String, Object>();
		try {
			userDto = userService.login(userDto);
			if (userDto == null) {
				return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
			} else {
				String accessToken = jwtService.createAccessToken("userId", userDto.getUserId());
				String refreshToken = jwtService.createRefreshToken("userId", userDto.getUserId());
				userService.saveRefreshToken(userDto.getUserId(), refreshToken);
				res.put("access-token", accessToken);
				res.put("refresh-token", refreshToken);
				res.put("userinfo", userDto);
			}
//			session.setAttribute("userinfo", userDto);
			return new ResponseEntity<Object>(res, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	

	@ApiOperation(value = "유저별 리뷰 반환", notes = "유저의 기본 키(userId)을 입력 받아 해당 유저가 작성한 리뷰들을 모두 가져옵니다.")
	@ApiImplicitParam(name = "userId", value = "유저의 기본 키(userId)", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "path" , example = "25" )
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"reviewId\": 41,\r\n" + 
            		"    \"subject\": \"여행 리뷰\",\r\n" + 
            		"    \"content\": \"재미있었어요!\",\r\n" + 
            		"    \"userId\": 25,\r\n" + 
            		"    \"userName\": \"관리자\",\r\n" + 
            		"    \"registTime\": \"2023-05-02 11:49:08\",\r\n" + 
            		"    \"planId\": 27,\r\n" + 
            		"    \"rating\": 3,\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"reviewId\": 44,\r\n" + 
            		"    \"subject\": \"새 리뷰 제목\",\r\n" + 
            		"    \"content\": \"새 리뷰 내용\",\r\n" + 
            		"    \"userId\": 25,\r\n" + 
            		"    \"userName\": \"관리자\",\r\n" + 
            		"    \"registTime\": \"2023-05-02 13:14:18\",\r\n" + 
            		"    \"planId\": 27,\r\n" + 
            		"    \"rating\": 5,\r\n" + 
            		"  }\r\n" + 
            		"]"
        )
    )
)
	@GetMapping("/{userId}/reviews")
	public ResponseEntity<Object> listReviewByUser(@PathVariable("userId") int userId) {
		List<ReviewDto> reviewList = null;
		
		try {
			reviewList = userService.listReviewByUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Object>(reviewList, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "로그아웃", notes = "해당 세션을 무효화하여 로그아웃 처리합니다.")
	@GetMapping("/logout")
	public ResponseEntity<Object> logout(HttpServletRequest request) {

		if (!jwtService.checkToken(request.getHeader("access-token"))) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		try {
			int userId = (Integer) jwtService.get(null).get("userId");
			userService.deleteUserToken(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value = "회원가입", notes = "회원가입을 시도합니다.\n성공 시 세션에 유저 정보를 담아 로그인 처리를 합니다.")
    @ApiImplicitParam(name = "userDto",
    value = "id(required), password(required), name(required), tel(required), email(required) ",
    required = true ,
    dataType = "userDto", 
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"id\" : \"good_day\" "
    		+ ", \"password\" : \"1234\" " 
    		+ ", \"name\" : \"조은날\" " 
    		+ ", \"tel\" : \"010-7777-3333\" " 
    		+ ", \"email\" : \"sunny_day@tripbuddy.com\" }" )
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"{\r\n" + 
            		"  \"userId\": 35,\r\n" + 
            		"  \"id\": \"lazy_day123\",\r\n" + 
            		"  \"name\": \"최트립\",\r\n" + 
            		"  \"tel\": \"010-4444-3333\",\r\n" + 
            		"  \"email\": \"lazy_day2@tripbuddy.com\",\r\n" + 
            		"  \"lastAccess\": \"2023-05-07 16:20:04\",\r\n" + 
            		"  \"admin\": false\r\n" + 
            		"}"
        )
    )
)
	@PostMapping
	public ResponseEntity<Object> signup(@RequestBody UserDto userDto) {		
		try {
			userService.signup(userDto);
//			userDto = userService.login(userDto);
//			session.setAttribute("userinfo", userDto);
			return new ResponseEntity<Object>(null, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	
	@ApiOperation(value = "회원 삭제", notes = "해당 유저를 삭제합니다.")
	@DeleteMapping
	public ResponseEntity<Object> deleteUser(HttpServletRequest request) {		
		try {

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto)(session.getAttribute("userinfo"))).getUserId();
			userService.deleteUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
//		session.invalidate();
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "유저 정보", notes = "유저의 id를 받아 유저의 간략한 정보를 가져옵니다.")
	@ApiImplicitParam(name = "id", value = "id", required = true ,dataType = "String", dataTypeClass = String.class, paramType = "path" , example = "ssafyzzang" )
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"{\r\n" + 
            		"  \"userId\": 29,\r\n" + 
            		"  \"id\": \"trippark\",\r\n" + 
            		"  \"name\": \"박트립\",\r\n" + 
            		"  \"admin\": false\r\n" + 
            		"}"
        )
    )
)
	@GetMapping("/{id}")
	public ResponseEntity<Object> getUserInfo(@PathVariable("id") String id) {
		UserDto userDto = null;
		try {
			userDto = userService.getUserInfo(id);
			if (userDto == null) {
				return new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<Object>(userDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/auth/{userid}")
	public ResponseEntity<Object> checkAuthUser(@PathVariable("userid") int userId, HttpServletRequest request) {
		UserDto userDto = null;
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		try {
			if (jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(null, HttpStatus.ACCEPTED);
			}
			else return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/auth/regen/{userid}")
	public ResponseEntity<Object> regenToken(@PathVariable("userid") int userId, HttpServletRequest request) {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		String token = request.getHeader("refresh-token");
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			if (jwtService.checkToken(token)) {
				String dbToken = userService.getRefreshToken(userId);
				System.out.println(token + ", db : " + dbToken);
				if (token.equals(dbToken)) {
					String accessToken = jwtService.createAccessToken("userId", userId);
					res.put("access-token", accessToken);
					return new ResponseEntity<Object>(res, HttpStatus.ACCEPTED);
				}
			}
			return new ResponseEntity<Object>(null, HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	
	@ApiOperation(value = "유저 정보 수정", notes = "해당 세션 유저의 프로필 이미지 및 비밀번호를 수정합니다.")
	@ApiImplicitParam(name = "userDto",
    value = "profileImg(optional), password(optional) ",
    required = true ,
    dataType = "userDto", 
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"profileImg\" : \"sunny_day.jpg\" "
    		+ ", \"password\" : \"moneyday\" }" )
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"{\r\n" + 
            		"  \"userId\": 26,\r\n" + 
            		"  \"password\": \"moneyday\",\r\n" + 
            		"  \"profileImg\": \"sunny_day.jpg\",\r\n" + 
            		"  \"admin\": false\r\n" + 
            		"}"
        )
    )
)
	@PutMapping
	public ResponseEntity<Object> modifyUser(@RequestBody UserDto userDto, HttpServletRequest request) {		
		try {

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto)(session.getAttribute("userinfo"))).getUserId();
			userDto.setUserId(userId);
			userService.modifyUser(userDto);
			return new ResponseEntity<Object>(userDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "유저 비밀번호 초기화", notes = "이메일과 아이디를 받아 비밀번호 초기화 메일을 전송합니다.")
	@ApiImplicitParam(name = "userDto",
    value = "email(required), id(reqired) ",
    required = true ,
    dataType = "userDto", 
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"email\" : \"good_day@tripbuddy.com\" "
    		+ ", \"id\" : \"tripbuddy\" }")
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"{\r\n" + 
            		"  \"msg\": \"이메일을 전송했습니다.\"\r\n" + 
            		"}"
        )
    )
)
	@PostMapping("/email")
	public ResponseEntity<Object> sendEmail(@RequestBody UserDto userDto) {		
		Map<String, String> res = new HashMap<String, String>();
		try {
			int userId = userService.checkUserByEmail(userDto);
			if (userId == -1) {
				res.put("msg", "일치하는 회원 정보가 없습니다.");
				return new ResponseEntity<Object>(res, HttpStatus.OK);
			} else {
				userDto.setUserId(userId);
				userDto.setPassword(mailService.sendMail(userDto.getEmail()));
				userService.modifyUser(userDto);
				res.put("msg", "이메일을 전송했습니다.");
				return new ResponseEntity<Object>(res, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/check/{id}")
	public ResponseEntity<Object> checkId(@PathVariable("id") String id) {
		try {
			int result = userService.checkId(id);
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.CONFLICT);
	}
}

