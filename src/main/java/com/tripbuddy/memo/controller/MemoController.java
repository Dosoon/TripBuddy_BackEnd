package com.tripbuddy.memo.controller;

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

import com.tripbuddy.jwt.service.JwtService;
import com.tripbuddy.memo.model.dto.MemoDto;
import com.tripbuddy.memo.model.service.MemoService;
import com.tripbuddy.user.model.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/memos")
@Api(tags = { "메모 컨트롤러" })
public class MemoController {

	@Autowired
	private MemoService memoService;
	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "메모 작성", notes = "플랜의 경로(관광지)에 대해 메모를 남깁니다.")
    @ApiImplicitParam(name = "memoDto",
    value = "planId(required), contentId(required), content(required)",
    required = true ,
    dataType = "memoDto",
    dataTypeClass = Object.class,
    paramType = "body",
    example = " { \"planId\" : 27 "
    		+ ", \"content\" : \"엄청나게 대단하고 멋진 경로입니다.\" " 
    		+ ", \"contentId\" : 125266 }")
	@PostMapping
	public ResponseEntity<Object> postMemo(@RequestBody MemoDto memoDto, HttpServletRequest request) {
		try {

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) (session.getAttribute("userinfo"))).getUserId();
			memoDto.setUserId(userId);
			
			memoService.postMemo(memoDto);

			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "메모 삭제하기", notes = "메모의 기본 키를 받아 메모를 삭제합니다.")
	@ApiImplicitParam(name = "memoId", value = "메모의 기본 키(memoId) ", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "path" , example = "3" )
	@DeleteMapping("/{memoId}")
	public ResponseEntity<Object> deleteMemo(@PathVariable("memoId") int memoId){
		try {
			memoService.deleteMemo(memoId);
			return new ResponseEntity<Object>(HttpStatus.OK);
			
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		
	}
	
	
	@ApiOperation(value = "메모 리스트 가져오기", notes = "플랜의 경로의 기본 키(contentId)에 해당하는 메모들을 가져옵니다.")
	@ApiImplicitParams({
	@ApiImplicitParam(name = "contentId", value = "관광지의 기본 키(contentId)", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "path" , example = "125266" ),
	@ApiImplicitParam(name = "planId", value = "플랜의 기본 키(planId)", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "path" , example = "27" )})
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"memoId\": 30,\r\n" + 
            		"    \"userId\": 26,\r\n" + 
            		"    \"planId\": 27,\r\n" + 
            		"    \"contentId\": 125266,\r\n" + 
            		"    \"userName\": \"김싸피\",\r\n" + 
            		"    \"content\": \"엄청나게 대단하고 멋진 경로입니다.\",\r\n" + 
            		"    \"mine\": true\r\n" + 
            		"  }\r\n" + 
            		"]"
        )
    )
)
	@GetMapping("/{planId}/{contentId}")
	public ResponseEntity<Object> getMemos(@ApiIgnore @PathVariable Map<String, Object> req, HttpServletRequest request){
		List<MemoDto> list = null;
		
		try {

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
			
			req.put("userId", userId);
			list = memoService.getMemos(req);
			
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		
	}
}
