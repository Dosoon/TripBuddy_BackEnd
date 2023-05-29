package com.tripbuddy.follow.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tripbuddy.follow.model.dto.FollowDto;
import com.tripbuddy.follow.model.service.FollowService;
import com.tripbuddy.jwt.service.JwtService;
import com.tripbuddy.user.model.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

@RestController
@RequestMapping("/follow")
@Api(tags = { "팔로우 컨트롤러" })
public class FollowController {

	@Autowired
	private FollowService followService;
	@Autowired
	private JwtService jwtService;
	
	@ApiOperation(value = "유저 팔로우", notes = "팔로우 할 대상의 유저 기본 키(followeeId)를 받아 팔로우 처리합니다.")
    @ApiImplicitParam(name = "req",
    value = "followeeId(required)",
    required = true ,
    dataType = "Map<String, Object>", 
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"followeeId\" : 29 }" )
	@PostMapping
	public ResponseEntity<Object> followUser(@RequestBody Map<String, Object> req, HttpServletRequest request) {
		try {

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
			
			req.put("followerId", userId);
			followService.followUser(req);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "언팔로우", notes = "언팔로우 할 대상의 유저 기본 키(followingId)를 받아 언팔로우 처리합니다.")
	@ApiImplicitParam(name = "followingId", value = "언팔로우 할 대상의 유저 기본 키", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "path" , example = "29" )
	@DeleteMapping("/{followingId}")
	public ResponseEntity<Object> unfollowUser(@PathVariable("followingId") int followingId, HttpServletRequest request) {
		try {
			Map<String, Object> req = new HashMap<String, Object>();

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
			
//			int userId = ((UserDto)(session.getAttribute("userinfo"))).getUserId();
			req.put("followerId", userId);
			req.put("followeeId", followingId);
			followService.unfollowUser(req);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	
	@ApiOperation(value = "팔로잉 리스트", notes = "팔로워의 기본 키를 받아 그 아이디가 팔로잉하고 있는 유저들의 리스트를 반환합니다.")
	@ApiImplicitParam(name = "followerId", value = "팔로잉하는 유저의 ID(기본 키)", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "path" , example = "29" )
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"followerId\": 29,\r\n" + 
            		"    \"followeeId\": 25,\r\n" + 
            		"    \"followerName\": \"박싸피\",\r\n" + 
            		"    \"followeeName\": \"관리자\",\r\n" + 
            		"    \"followBack\": 1\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"followerId\": 29,\r\n" + 
            		"    \"followeeId\": 26,\r\n" + 
            		"    \"followerName\": \"박싸피\",\r\n" + 
            		"    \"followeeName\": \"김싸피\",\r\n" + 
            		"    \"followBack\": 1\r\n" + 
            		"  }\r\n" + 
            		"]"
        )
    )
)
	@GetMapping("/followees/{followerId}")
	public ResponseEntity<Object> listFollowing(@PathVariable("followerId") int followerId) {
		try {
			List<FollowDto> followingList = followService.listFollowing(followerId);
			return new ResponseEntity<Object>(followingList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "팔로워 리스트", notes = "팔로위의 기본 키를 받아 그 아이디를 팔로잉하고 있는 유저들의 리스트를 반환합니다.")
	@ApiImplicitParam(name = "followeeId", value = "팔로잉 대상 유저의 ID(기본 키)", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "path" , example = "29" )
	@GetMapping("/followers/{followeeId}")
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"followerId\": 25,\r\n" + 
            		"    \"followeeId\": 29,\r\n" + 
            		"    \"followerName\": \"관리자\",\r\n" + 
            		"    \"followeeName\": \"박싸피\",\r\n" + 
            		"    \"followBack\": 1\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"followerId\": 26,\r\n" + 
            		"    \"followeeId\": 29,\r\n" + 
            		"    \"followerName\": \"김싸피\",\r\n" + 
            		"    \"followeeName\": \"박싸피\",\r\n" + 
            		"    \"followBack\": 1\r\n" + 
            		"  }\r\n" + 
            		"]"
        )
    )
)
	public ResponseEntity<Object> listFollower(@PathVariable("followeeId") int followeeId) {
		try {
			List<FollowDto> followerList = followService.listFollower(followeeId);
			return new ResponseEntity<Object>(followerList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "팔로워/팔로잉 정보", notes = "유저 기본 키를 받아 해당 유저의 팔로워 및 팔로잉 정보를 숫자만 반환합니다.")
	@ApiImplicitParam(name = "userId", value = "정보를 탐색할 대상 유저의 ID(기본 키)", required = true, dataType = "int", dataTypeClass = Integer.class, example = "26")
	@GetMapping("/{userId}")
	@ApiResponse(code=200, message="OK", examples=@Example(value = @ExampleProperty( mediaType = "*/*", value="{\n\"follower\": 1, \n\"following\": 2\n}" )))
	public ResponseEntity<Object> followInfo(@PathVariable("userId") int userId) {
		try {
			Map<String, Integer> info = followService.followInfo(userId);
			return new ResponseEntity<Object>(info, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	
	
	
	@GetMapping("state/followees/{followeeId}")
	public ResponseEntity<Object> isFollowing(@PathVariable("followeeId") int followeeId, HttpServletRequest request) {
		try {
			Map<String, Integer> req = new HashMap<String, Integer>();

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto)(session.getAttribute("userinfo"))).getUserId();
			req.put("followerId", userId);
			req.put("followeeId", followeeId);
			
			boolean info = followService.isFollowing(req);
			return new ResponseEntity<Object>(info, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("state/reviews/{reviewId}")
	public ResponseEntity<Object> isReviewFollowing(@PathVariable("reviewId") int reviewId, HttpServletRequest request) {
		try {
			Map<String, Integer> req = new HashMap<String, Integer>();
			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
			
//			int userId = ((UserDto)(session.getAttribute("userinfo"))).getUserId();
			req.put("userId", userId);
			req.put("reviewId", reviewId);
			
			boolean info = followService.isReviewFollowing(req);
			return new ResponseEntity<Object>(info, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
}
