package com.tripbuddy.wish.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripbuddy.attraction.model.dto.AttractionDto;
import com.tripbuddy.jwt.service.JwtService;
import com.tripbuddy.user.model.dto.UserDto;
import com.tripbuddy.wish.model.dto.WishDto;
import com.tripbuddy.wish.model.service.WishService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/wishes")
@Api(tags = { "찜 컨트롤러" })
public class WishController {
	
	@Autowired
	private WishService wishService;
	@Autowired
	private JwtService jwtService;
	
	@ApiOperation(value = "관광지 찜하기", notes = "관광지 기본 키를 받아 찜 목록에 넣습니다.")
    @ApiImplicitParam(name = "req",
    value = "content_id(required)",
    required = true ,
    dataType = "Map<String, Object>",
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"contentId\" : 125266 }" )
	@PostMapping
	public ResponseEntity<Object> postWish(@ApiIgnore @RequestBody Map<String, Object> req, HttpServletRequest request) {
		try {

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto)(session.getAttribute("userinfo"))).getUserId();
			req.put("userId", userId);
			wishService.postWish(req);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "관광지 찜 여부", notes = "관광지 찜 여부를 체크합니다.")
	@ApiImplicitParam(name = "req",
	value = "content_id(required)",
	required = true ,
	dataType = "Map<String, Object>",
	dataTypeClass = Object.class,
	paramType = "body",
	example = " { \"contentId\" : 125266 }" )
	@GetMapping("/{contentId}")
	public ResponseEntity<Object> checkWish(@PathVariable("contentId") int contentId, HttpServletRequest request) {
		try {
			Map<String, Object> req = new HashMap<String, Object>();

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto)(session.getAttribute("userinfo"))).getUserId();
			req.put("contentId", contentId);
			req.put("userId", userId);
			int result = wishService.checkWish(req);
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "찜 삭제", notes = "관광지의 기본 키를 받아 찜 목록에서 삭제합니다.")
	@ApiImplicitParam(name = "contentId", value = "관광지의 기본 키(contentId)", required = true , dataTypeClass = Integer.class, dataType = "int", paramType = "path" , example = "125266" )
	@DeleteMapping("/{contentId}")
	public ResponseEntity<Object> deleteWish(@PathVariable("contentId") int contentId, HttpServletRequest request) {
		try {

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto)(session.getAttribute("userinfo"))).getUserId();
			Map<String, Object> req = new HashMap<String, Object>();
			req.put("contentId", contentId);
			req.put("userId", userId);
			wishService.deleteWish(req);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	@ApiOperation(value = "찜한 관광지 리스트", notes = "페이징 처리를 위한 page를 받아 내가 찜한 관광지 리스트를 반환합니다.")
	@ApiImplicitParam(name = "page", value = "현재 페이지 값", required = true , dataTypeClass = Integer.class, dataType = "int", paramType = "query" , example = "1" )
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"contentId\": 125405,\r\n" + 
            		"    \"contentTypeId\": 12,\r\n" + 
            		"    \"title\": \"토함산자연휴양림\",\r\n" + 
            		"    \"addr1\": \"경상북도 경주시 양북면 불국로 1208-45\",\r\n" + 
            		"    \"addr2\": \"\",\r\n" + 
            		"    \"zipCode\": \"38120\",\r\n" + 
            		"    \"firstImage\": \"\",\r\n" + 
            		"    \"firstImage2\": \"\",\r\n" + 
            		"    \"readCount\": 142991,\r\n" + 
            		"    \"sidoCode\": 35,\r\n" + 
            		"    \"gugunCode\": 2,\r\n" + 
            		"    \"latitude\": 35.7619577,\r\n" + 
            		"    \"longitude\": 129.3655037,\r\n" + 
            		"    \"mlevel\": \"6\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"contentId\": 125406,\r\n" + 
            		"    \"contentTypeId\": 12,\r\n" + 
            		"    \"title\": \"비슬산자연휴양림\",\r\n" + 
            		"    \"addr1\": \"대구광역시 달성군 유가읍 일연선사길 61\",\r\n" + 
            		"    \"addr2\": \"\",\r\n" + 
            		"    \"zipCode\": \"42991\",\r\n" + 
            		"    \"firstImage\": \"http://tong.visitkorea.or.kr/cms/resource/62/219162_image2_1.jpg\",\r\n" + 
            		"    \"firstImage2\": \"http://tong.visitkorea.or.kr/cms/resource/62/219162_image3_1.jpg\",\r\n" + 
            		"    \"readCount\": 168414,\r\n" + 
            		"    \"sidoCode\": 4,\r\n" + 
            		"    \"gugunCode\": 3,\r\n" + 
            		"    \"latitude\": 35.69138039,\r\n" + 
            		"    \"longitude\": 128.5159774,\r\n" + 
            		"    \"mlevel\": \"6\"\r\n" + 
            		"  }\r\n" + 
            		" ]"
        )
    )
)

	@GetMapping
	public ResponseEntity<Object> getWishes(@RequestParam("page") int page, HttpServletRequest request) {
		try {
			Map<String, Object> req = new HashMap<String, Object>();

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto)(session.getAttribute("userinfo"))).getUserId();
			page = (page - 1) * 10;
			req.put("userId", userId);
			req.put("offset", page);
			List<AttractionDto> wishList = wishService.getWishes(req);
			return new ResponseEntity<Object>(wishList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	
}
