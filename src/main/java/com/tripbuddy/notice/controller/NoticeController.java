package com.tripbuddy.notice.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripbuddy.attraction.model.dto.AttractionDto;
import com.tripbuddy.jwt.service.JwtService;
import com.tripbuddy.notice.model.dto.NoticeDto;
import com.tripbuddy.notice.model.service.NoticeService;
import com.tripbuddy.review.model.dto.ReviewDto;
import com.tripbuddy.user.model.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

@RestController
@RequestMapping("/notices")
@Api(tags = { "공지 컨트롤러" })
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "공지 작성", notes = "공지사항을 작성합니다.")
    @ApiImplicitParam(name = "noticeDto",
    value = "subject(required), content(required)",
    required = true ,
    dataType = "noticeDto", 
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"subject\" : \"아주 아주 중요한 공지사항입니다.\" "
    		+ ", \"content\" : \"모두가 꼭 지켜야만 하는 규칙입니다.\" }" )
	@PostMapping
	public ResponseEntity<Object> postNotice(@RequestBody NoticeDto noticeDto, HttpServletRequest request) {
		try {

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) (session.getAttribute("userinfo"))).getUserId();
			noticeDto.setUserId(userId);
			noticeService.postNotice(noticeDto);
			
			return new ResponseEntity<Object>(HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "공지 수정", notes = "공지사항을 수정합니다.")
    @ApiImplicitParam(name = "noticeDto",
    value = "subject(required), content(required), noticeId(required)" ,
    required = true ,
    dataType = "noticeDto", 
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"subject\" : \"수정된 공지사항입니다.\" "
    		+ ", \"content\" : \"모두가 지켜야 할 규칙입니다.\" " 
    		+ ", \"noticeId\" : 3 }" )
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"{\r\n" + 
            		"  \"noticeId\": 7,\r\n" + 
            		"  \"userId\": 0,\r\n" + 
            		"  \"userName\": null,\r\n" + 
            		"  \"subject\": \"수정된 공지사항입니다.\",\r\n" + 
            		"  \"content\": \"모두가 지켜야 할 규칙입니다.\",\r\n" + 
            		"  \"registerTime\": null\r\n" + 
            		"}"
        )
    )
)
	@PutMapping
	public ResponseEntity<Object> modifyNotice(@RequestBody NoticeDto noticeDto) {
		try {
			noticeService.modifyNotice(noticeDto);
			return new ResponseEntity<Object>(noticeDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	
	
	@ApiOperation(value = "공지 리스트", notes = "페이징 처리를 위한 page를 받아 공지사항 리스트를 가져옵니다.")
	@ApiImplicitParam(name = "page", value = "현재 페이지", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "query" , example = "1" )
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"noticeId\": 7,\r\n" + 
            		"    \"userId\": 25,\r\n" + 
            		"    \"userName\": \"관리자\",\r\n" + 
            		"    \"subject\": \"수정된 공지사항입니다.\",\r\n" + 
            		"    \"content\": \"모두가 지켜야 할 규칙입니다.\",\r\n" + 
            		"    \"registerTime\": \"2023-05-07 02:47:36\"\r\n" + 
            		"  }\r\n" + 
            		"]"
        )
    )
)
	@GetMapping
	public ResponseEntity<Object> getNotices(@RequestParam("page") int page, @RequestParam(name="sort", required=false) String sort, @RequestParam(name="keyword", required=false) String keyword) {
		try {
			Map<String, Object> req = new HashMap<String, Object>();
			req.put("sort", sort);
			if (keyword != null)
				req.put("keyword", keyword);
			int offset = (page-1)*20;
			req.put("offset", offset);
			List<NoticeDto> noticeList = noticeService.getNotices(req);
			return new ResponseEntity<Object>(noticeList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiResponse(code=200,
		    message="OK",
		    examples=@Example(
		        value = @ExampleProperty(
		            mediaType = "*/*",
		            value = 	"{\r\n" + 
		            		"  \"noticeId\": 7,\r\n" + 
		            		"  \"userId\": 25,\r\n" + 
		            		"  \"userName\": \"관리자\",\r\n" + 
		            		"  \"subject\": \"수정된 공지사항입니다.\",\r\n" + 
		            		"  \"content\": \"모두가 지켜야 할 규칙입니다.\",\r\n" + 
		            		"  \"registerTime\": \"2023-05-07 02:47:36\"\r\n" + 
		            		"}"
		        )
		    )
		)
	@ApiOperation(value = "공지 가져오기", notes = "공지사항의 기본 키를 받아 공지사항을 가져옵니다.")
	@ApiImplicitParam(name = "noticeId", value = "공지사항의 기본 키(noticeId)", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "path" , example = "1" )
	@GetMapping("/{noticeId}")
	public ResponseEntity<Object> viweNotice(@PathVariable("noticeId") int noticeId) {
		
		NoticeDto noticeDto;
		try {
			noticeDto  =  noticeService.viewNotice(noticeId);
			return new ResponseEntity<Object>(noticeDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiResponse(code=200,
			message="OK",
			examples=@Example(
					value = @ExampleProperty(
							mediaType = "*/*",
							value = 	"20"
							)
					)
			)
	@ApiOperation(value = "공지 총 개수 가져오기", notes = "공지사항 전체 게시글 수를 가져옵니다.")
	@GetMapping("/total")
	public ResponseEntity<Object> totalNotices(@RequestParam(name="keyword", required=false) String keyword) {
		try {
			Map<String, Object> req = new HashMap<String, Object>();
			System.out.println(keyword);
			if (keyword != null)
				req.put("keyword", keyword);
			int total =  noticeService.totalNotices(req);
			return new ResponseEntity<Object>(total, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "공지 삭제하기", notes = "공지사항의 기본 키를 받아 공지사항을 삭제합니다")
	@ApiImplicitParam(name = "noticeId", value = "공지사항의 기본 키(noticeId)", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "path" , example = "41" )
	@DeleteMapping("/{noticeId}")
	public ResponseEntity<Object> deleteNotice(@PathVariable("noticeId") int noticeId) {
		try {
			noticeService.deleteNotice(noticeId);
			return new ResponseEntity<Object>(HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		
	}
	
	
	

}
