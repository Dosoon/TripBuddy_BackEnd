package com.tripbuddy.plan.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
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

import com.tripbuddy.attraction.model.dto.AreaDto;
import com.tripbuddy.jwt.service.JwtService;
import com.tripbuddy.plan.model.dto.CourseDto;
import com.tripbuddy.plan.model.dto.PlanDto;
import com.tripbuddy.plan.model.service.PlanService;
import com.tripbuddy.user.model.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;

@RestController
@RequestMapping("/plans")
@Api(tags = { "플랜 컨트롤러" })
public class PlanController {
	
	@Autowired
	private PlanService planService;
	@Autowired
	private JwtService jwtService;
	
	@ApiOperation(value = "썸네일이 있는 플랜 리스트", notes = "유저가 속한, 썸네일이 있는 플랜 리스트를 가져옵니다. ('마이 플랜' 카테고리 용)")
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"planId\": 27,\r\n" + 
            		"    \"title\": \"즐거운 여행\",\r\n" + 
            		"    \"startDate\": \"2023-05-02\",\r\n" + 
            		"    \"endDate\": \"2023-05-31\",\r\n" + 
            		"    \"lastModified\": \"2023-05-07 18:08:52\",\r\n" + 
            		"    \"thumbnail\": \"http://tong.visitkorea.or.kr/cms/resource/21/2657021_image2_1.jpg\",\r\n" + 
            		"    \"members\": [\r\n" + 
            		"      {\r\n" + 
            		"        \"userId\": 25,\r\n" + 
            		"        \"name\": \"관리자\",\r\n" + 
            		"        \"admin\": false\r\n" + 
            		"      },\r\n" + 
            		"      {\r\n" + 
            		"        \"userId\": 26,\r\n" + 
            		"        \"name\": \"김싸피\",\r\n" + 
            		"        \"profileImg\": \"sunny_day.jpg\",\r\n" + 
            		"        \"admin\": false\r\n" + 
            		"      }\r\n" + 
            		"    ]\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"planId\": 29,\r\n" + 
            		"    \"title\": \"새로운 여행 계획\",\r\n" + 
            		"    \"lastModified\": \"2023-05-07 18:02:27\",\r\n" + 
            		"    \"members\": [\r\n" + 
            		"      {\r\n" + 
            		"        \"userId\": 25,\r\n" + 
            		"        \"name\": \"관리자\",\r\n" + 
            		"        \"admin\": false\r\n" + 
            		"      }\r\n" + 
            		"    ]\r\n" + 
            		"  }\r\n" + 
            		"]"
        )
    )
)
	@GetMapping("/thumbnails")
	public ResponseEntity<Object> listPlanByUserWithThumbnail(HttpServletRequest request){
		List<PlanDto> planList = null;
		try {

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) session.getAttribute("userinfo")).getUserId(); 
			planList = planService.listPlanByUserWithThumbnail(userId);
			
			return new ResponseEntity<Object>(planList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	@ApiOperation(value = "플랜 리스트", notes = "유저가 속한 플랜 리스트를 가져옵니다. (리뷰 작성 및 수정용)")
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"planId\": 27,\r\n" + 
            		"    \"title\": \"즐거운 여행\",\r\n" + 
            		"    \"startDate\": \"2023-05-02\",\r\n" + 
            		"    \"endDate\": \"2023-05-31\",\r\n" + 
            		"    \"lastModified\": \"2023-05-07 13:20:21\",\r\n" + 
            		"  }\r\n" + 
            		"]"
        )
    )
)
	@GetMapping
	public ResponseEntity<Object> listPlanByUser(HttpServletRequest request){
		List<PlanDto> planList = null;
		try {
			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) session.getAttribute("userinfo")).getUserId(); 
			planList = planService.listPlanByUser(userId);
			
			return new ResponseEntity<Object>(planList, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	
	
	
	@ApiOperation(value = "플랜 가져오기", notes = "플랜 기본 키를 받아 플랜 정보를 가져옵니다.")
	@ApiImplicitParam(name = "planId", value = "플랜의 기본 키(planId) ", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "path" , example = "27" )
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"{\r\n" + 
            		"  \"planId\": 27,\r\n" + 
            		"  \"title\": \"즐거운 여행\",\r\n" + 
            		"  \"startDate\": \"2023-05-02\",\r\n" + 
            		"  \"endDate\": \"2023-05-31\",\r\n" + 
            		"  \"lastModified\": \"2023-05-07 13:20:21\",\r\n" + 
            		"  \"courses\": [\r\n" + 
            		"    {\r\n" + 
            		"      \"contentId\": 125266,\r\n" + 
            		"      \"planId\": 27,\r\n" + 
            		"      \"order\": 1,\r\n" + 
            		"      \"day\": 1,\r\n" + 
            		"      \"attractionDto\": {\r\n" + 
            		"        \"contentId\": 125266,\r\n" + 
            		"        \"contentTypeId\": 12,\r\n" + 
            		"        \"title\": \"국립 청태산자연휴양림\",\r\n" + 
            		"        \"addr1\": \"강원도 횡성군 둔내면 청태산로 610\",\r\n" + 
            		"        \"addr2\": \"\",\r\n" + 
            		"        \"firstImage\": \"http://tong.visitkorea.or.kr/cms/resource/21/2657021_image2_1.jpg\",\r\n" + 
            		"        \"readCount\": 0,\r\n" + 
            		"        \"sidoCode\": 32,\r\n" + 
            		"        \"gugunCode\": 18,\r\n" + 
            		"        \"latitude\": 37.52251412,\r\n" + 
            		"        \"longitude\": 128.2919115,\r\n" + 
            		"      }\r\n" + 
            		"    },\r\n" + 
            		"    {\r\n" + 
            		"      \"contentId\": 125405,\r\n" + 
            		"      \"planId\": 27,\r\n" + 
            		"      \"order\": 2,\r\n" + 
            		"      \"day\": 2,\r\n" + 
            		"      \"attractionDto\": {\r\n" + 
            		"        \"contentId\": 125405,\r\n" + 
            		"        \"contentTypeId\": 12,\r\n" + 
            		"        \"title\": \"토함산자연휴양림\",\r\n" + 
            		"        \"addr1\": \"경상북도 경주시 양북면 불국로 1208-45\",\r\n" + 
            		"        \"addr2\": \"\",\r\n" + 
            		"        \"firstImage\": \"\",\r\n" + 
            		"        \"readCount\": 0,\r\n" + 
            		"        \"sidoCode\": 35,\r\n" + 
            		"        \"gugunCode\": 2,\r\n" + 
            		"        \"latitude\": 35.7619577,\r\n" + 
            		"        \"longitude\": 129.3655037,\r\n" + 
            		"      }\r\n" + 
            		"    },\r\n" + 
            		"    {\r\n" + 
            		"      \"contentId\": 125406,\r\n" + 
            		"      \"planId\": 27,\r\n" + 
            		"      \"order\": 3,\r\n" + 
            		"      \"day\": 3,\r\n" + 
            		"      \"attractionDto\": {\r\n" + 
            		"        \"contentId\": 125406,\r\n" + 
            		"        \"contentTypeId\": 12,\r\n" + 
            		"        \"title\": \"비슬산자연휴양림\",\r\n" + 
            		"        \"addr1\": \"대구광역시 달성군 유가읍 일연선사길 61\",\r\n" + 
            		"        \"addr2\": \"\",\r\n" + 
            		"        \"firstImage\": \"http://tong.visitkorea.or.kr/cms/resource/62/219162_image2_1.jpg\",\r\n" + 
            		"        \"readCount\": 0,\r\n" + 
            		"        \"sidoCode\": 4,\r\n" + 
            		"        \"gugunCode\": 3,\r\n" + 
            		"        \"latitude\": 35.69138039,\r\n" + 
            		"        \"longitude\": 128.5159774,\r\n" + 
            		"      }\r\n" + 
            		"    }\r\n" + 
            		"  ],\r\n" + 
            		"  \"members\": null\r\n" + 
            		"}"
        )
    )
)
	@GetMapping("/{planId}")
	public ResponseEntity<Object> getPlanInfo(@PathVariable("planId") int planId) {
		PlanDto planDto = null;
		try {
			planDto = planService.getPlanInfo(planId);
			
			return new ResponseEntity<Object>(planDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	
	
	@ApiOperation(value = "새 플랜 작성", notes = "새 플랜을 작성합니다.")
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"{\r\n" + 
            		"  \"planId\": 29,\r\n" + 
            		"  \"title\": \"새로운 여행 계획\",\r\n" + 
            		"  \"lastModified\": \"2023-05-07 18:02:27\",\r\n" + 
            		"  \"courses\": [],\r\n" + 
            		"}"
        )
    )
)
	@PostMapping
	public ResponseEntity<Object> postPlan(HttpServletRequest request) {
		PlanDto planDto = null;
		try {
			Map<String, Object> req = new HashMap<String, Object>();

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) session.getAttribute("userinfo")).getUserId();
			req.put("userId", userId);
			planDto = planService.postPlan(req);
			
			return new ResponseEntity<Object>(planDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "플랜 탈퇴", notes = "플랜 기본 키를 받아 플랜을 나갑니다.")
	@ApiImplicitParam(name = "planId", value = "플랜의 기본 키(planId) ", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "path" , example = "3" )
	@DeleteMapping("/{planId}/users")
	public ResponseEntity<Object> deletePlanMember(@PathVariable("planId") int planId, HttpServletRequest request) {
		try {
			Map<String, Object> req = new HashMap<String, Object>();

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) session.getAttribute("userinfo")).getUserId();
			req.put("userId", userId);
			req.put("planId", planId);
			planService.deletePlanMember(req);
			
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "플랜 가입", notes = "플랜 기본 키를 받아 플랜에 참여합니다.")
	@ApiImplicitParam(name = "planId", value = "플랜의 기본 키(planId) ", required = true ,dataType = "int", dataTypeClass = Integer.class, paramType = "path" , example = "3" )
	@PostMapping("/{planId}/users")
	public ResponseEntity<Object> postPlanMember(@PathVariable("planId") int planId, HttpServletRequest request) {
		try {
			Map<String, Object> req = new HashMap<String, Object>();
			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) session.getAttribute("userinfo")).getUserId();
			req.put("userId", userId);
			req.put("planId", planId);
			planService.postPlanMember(req);
			
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "플랜 스크랩", notes = "플랜 기본 키를 받아 그 플랜을 스크랩합니다.")
	@PostMapping("/{planId}")
	public ResponseEntity<Object> scrapPlan(@ApiParam(name = "planId", example="28") @PathVariable("planId") int planId, HttpServletRequest request) {
		try {
			Map<String, Object> req = new HashMap<String, Object>();

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) session.getAttribute("userinfo")).getUserId();
			req.put("userId", userId);
			req.put("planId", planId);
			planService.scrapPlan(req);
			
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "플랜 수정", notes = "플랜을 수정합니다.")
	@ApiImplicitParam(name = "req",
	value = "1. title(optional), startDate(optional), endDate(optional) : 셋 중 하나는 존재해야 합니다. \r\n"
	        + "2. planId(required) \r\n"
	        + "3. courses(required)  \r\n"
	        + " : { contentId(required), order(required), day(required) }를 필요로 하는 course의 collection 형태입니다." ,
	required = true ,
	dataType = "Map<String, Object>", 
	dataTypeClass = Object.class,
	paramType = "body",
	example = "{ \"title\" : \"즐거운 여행\" , \r\n" + 
			"\"startDate\" : \"2023-05-02\" , \r\n" + 
			"\"endDate\" : \"2023-05-31\" , \r\n" + 
			"\"planId\" : 27, \r\n" + 
			"\"courses\" : \r\n" + 
			" [  {\"contentId\" : 125266, \"order\" : 1, \"day\" : 1 },\r\n" + 
			" {\"contentId\" : 125405, \"order\" : 2, \"day\" : 2 },\r\n" + 
			" {\"contentId\" : 125406, \"order\" : 3, \"day\" : 3 }  \r\n" + 
			"]}" )
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"{\r\n" + 
            		"  \"title\": \"즐거운 여행\",\r\n" + 
            		"  \"startDate\": \"2023-05-02\",\r\n" + 
            		"  \"endDate\": \"2023-05-31\",\r\n" + 
            		"  \"planId\": 27,\r\n" + 
            		"  \"courses\": [\r\n" + 
            		"    {\r\n" + 
            		"      \"contentId\": 125266,\r\n" + 
            		"      \"order\": 1,\r\n" + 
            		"      \"day\": 1\r\n" + 
            		"    },\r\n" + 
            		"    {\r\n" + 
            		"      \"contentId\": 125405,\r\n" + 
            		"      \"order\": 2,\r\n" + 
            		"      \"day\": 2\r\n" + 
            		"    },\r\n" + 
            		"    {\r\n" + 
            		"      \"contentId\": 125406,\r\n" + 
            		"      \"order\": 3,\r\n" + 
            		"      \"day\": 3\r\n" + 
            		"    }\r\n" + 
            		"  ]\r\n" + 
            		"}"
        )
    )
)
	@PutMapping
	public ResponseEntity<Object> modifyPlan(@RequestBody Map<String, Object> req) {
		try {
			System.out.println(req.get("courses"));
			planService.modifyPlan(req);
			return new ResponseEntity<Object>(req, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("{planId}/state")
	public ResponseEntity<Object> getPlanJoinInfo(@PathVariable("planId") int planId, HttpServletRequest request) {
		boolean planJoinInfo = false;
		try {
			Map<String, Integer> req = new HashMap<String, Integer>();

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) session.getAttribute("userinfo")).getUserId();
			req.put("userId", userId);
			req.put("planId", planId);
	
			planJoinInfo = planService.getPlanJoinInfo(req);
			
			return new ResponseEntity<Object>(planJoinInfo, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> getPlanList() {
		List<PlanDto> list = null;
		try {
			
			list = planService.getPlanList();
			
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.CONFLICT);
		}
	}
	

	
	
	
}
