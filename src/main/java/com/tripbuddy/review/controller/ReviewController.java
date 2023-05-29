package com.tripbuddy.review.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

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

import com.tripbuddy.comment.model.dto.CommentDto;
import com.tripbuddy.jwt.service.JwtService;
import com.tripbuddy.review.model.dto.ReviewDto;
import com.tripbuddy.review.model.service.ReviewService;
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
@RequestMapping("/reviews")
@Api(tags = { "리뷰 컨트롤러" })
public class ReviewController {

	@Autowired
	private ReviewService reviewService;
	@Autowired
	private JwtService jwtService;

	@ApiOperation(value = "리뷰 작성", notes = "플랜에 대한 리뷰를 작성합니다.")
	@ApiImplicitParam(name = "reviewDto", value = "subject(required), content(required), planId(required)", required = true, dataType = "reviewDto", dataTypeClass = Object.class, paramType = "body", example = " { \"subject\" : \"즐거웠던 제주 여행!\" "
			+ ", \"content\" : \"혼저옵서예\" " + ", \"planId\" : 27 }")
	@PostMapping
	public ResponseEntity<Object> postReview(@RequestBody ReviewDto reviewDto, HttpServletRequest request) {
		try {
			if (reviewDto.getSubject() == null || reviewDto.getContent() == null)
				throw new Exception();

			if (!jwtService.checkToken(request.getHeader("access-token"))) {
				return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
			}
			int userId = (Integer) jwtService.get(null).get("userId");
//			int userId = ((UserDto) (session.getAttribute("userinfo"))).getUserId();
			reviewDto.setUserId(userId);
			reviewService.postReview(reviewDto);
		} catch (Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}

	
	
	
	
//	@ApiOperation(value = "리뷰 리스트", notes = "페이징 처리를 위한 page와 정렬기준인 sort를 받아 리뷰 리스트를 반환합니다.\n정렬 기준은 review_id, rating 중에서 선택 가능합니다.")
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "page", value = "현재 페이지 값", required = true, dataType = "int", dataTypeClass = Integer.class, paramType = "query", example = "1"),
//			@ApiImplicitParam(name = "sort", value = "정렬 기준", required = false, dataType = "String", dataTypeClass = String.class, paramType = "query", example = "rating") })
//	@ApiResponse(code = 200, message = "OK", examples = @Example(value = @ExampleProperty(mediaType = "*/*", value = "[\r\n"
//			+ "  {\r\n" + "    \"reviewId\": 47,\r\n" + "    \"subject\": \"새 리뷰 제목2\",\r\n"
//			+ "    \"content\": \"새 리뷰 내용2\",\r\n" + "    \"userId\": 25,\r\n" + "    \"userName\": \"관리자\",\r\n"
//			+ "    \"registTime\": \"2023-05-02 13:15:09\",\r\n" + "    \"planId\": 27,\r\n" + "    \"rating\": 0,\r\n"
//			+ "  },\r\n" + "  {\r\n" + "    \"reviewId\": 41,\r\n" + "    \"subject\": \"여행 리뷰\",\r\n"
//			+ "    \"content\": \"재미있었어요!\",\r\n" + "    \"userId\": 25,\r\n" + "    \"userName\": \"관리자\",\r\n"
//			+ "    \"registTime\": \"2023-05-02 11:49:08\",\r\n" + "    \"planId\": 27,\r\n" + "    \"rating\": 3,\r\n"
//			+ "  }\r\n" + "  ]")))
//	@GetMapping
//	public ResponseEntity<Object> listReview(@ApiIgnore @RequestParam Map<String, String> req) {
//		List<ReviewDto> reviewList = null;
//
//		req.put("page", Integer.toString((Integer.parseInt(req.get("page")) - 1) * 10));
//		try {
//			reviewList = reviewService.listReview(req);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
//		}
//		return new ResponseEntity<Object>(reviewList, HttpStatus.OK);
//	}

	@ApiOperation(value = "리뷰 게시글 가져오기", notes = "리뷰 기본 키를 받아 리뷰, 댓글, 별점을 가져옵니다.")
	@ApiImplicitParam(name = "reviewId", value = "리뷰의 기본 키(reviewId) ", required = true, dataType = "int", dataTypeClass = Integer.class, paramType = "path", example = "1")
	@ApiResponse(code = 200, message = "OK", examples = @Example(value = @ExampleProperty(mediaType = "*/*", value = "{\r\n" + 
			"  \"comments\": [\r\n" + 
			"    {\r\n" + 
			"      \"userId\": 26,\r\n" + 
			"      \"reviewId\": 41,\r\n" + 
			"      \"commentId\": 11,\r\n" + 
			"      \"registerTime\": \"2023-05-02 13:58:37\",\r\n" + 
			"      \"userName\": \"김싸피\",\r\n" + 
			"      \"content\": \"와! 가보고 싶어요.\",\r\n" + 
			"      \"mine\": false\r\n" + 
			"    },\r\n" + 
			"    {\r\n" + 
			"      \"userId\": 25,\r\n" + 
			"      \"reviewId\": 41,\r\n" + 
			"      \"commentId\": 12,\r\n" + 
			"      \"registerTime\": \"2023-05-02 13:58:46\",\r\n" + 
			"      \"userName\": \"관리자\",\r\n" + 
			"      \"content\": \"그쵸?\",\r\n" + 
			"      \"mine\": false\r\n" + 
			"    }\r\n" + 
			"  ],\r\n" + 
			"  \"review\": {\r\n" + 
			"    \"reviewId\": 41,\r\n" + 
			"    \"subject\": \"여행 리뷰\",\r\n" + 
			"    \"content\": \"재미있었어요!\",\r\n" + 
			"    \"userId\": 25,\r\n" + 
			"    \"userName\": \"관리자\",\r\n" + 
			"    \"registTime\": \"2023-05-02 11:49:08\",\r\n" + 
			"    \"planId\": 27,\r\n" + 
			"    \"rating\": 3,\r\n" + 
			"    \"planDto\": {\r\n" + 
			"      \"planId\": 27,\r\n" + 
			"      \"title\": \"여행계획\",\r\n" + 
			"      \"startDate\": \"2023-05-02\",\r\n" + 
			"      \"endDate\": \"2023-05-31\",\r\n" + 
			"      \"courses\": [\r\n" + 
			"        {\r\n" + 
			"          \"contentId\": 125266,\r\n" + 
			"          \"planId\": 27,\r\n" + 
			"          \"order\": 1,\r\n" + 
			"          \"day\": 1,\r\n" + 
			"          \"attractionDto\": {\r\n" + 
			"            \"contentId\": 125266,\r\n" + 
			"            \"contentTypeId\": 12,\r\n" + 
			"            \"title\": \"국립 청태산자연휴양림\",\r\n" + 
			"            \"addr1\": \"강원도 횡성군 둔내면 청태산로 610\",\r\n" + 
			"            \"addr2\": \"\",\r\n" + 
			"            \"firstImage\": \"http://tong.visitkorea.or.kr/cms/resource/21/2657021_image2_1.jpg\",\r\n" + 
			"            \"readCount\": 0,\r\n" + 
			"            \"sidoCode\": 32,\r\n" + 
			"            \"gugunCode\": 18,\r\n" + 
			"            \"latitude\": 37.52251412,\r\n" + 
			"            \"longitude\": 128.2919115\r\n" + 
			"          }\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"          \"contentId\": 125405,\r\n" + 
			"          \"planId\": 27,\r\n" + 
			"          \"order\": 2,\r\n" + 
			"          \"day\": 1,\r\n" + 
			"          \"attractionDto\": {\r\n" + 
			"            \"contentId\": 125405,\r\n" + 
			"            \"contentTypeId\": 12,\r\n" + 
			"            \"title\": \"토함산자연휴양림\",\r\n" + 
			"            \"addr1\": \"경상북도 경주시 양북면 불국로 1208-45\",\r\n" + 
			"            \"addr2\": \"\",\r\n" + 
			"            \"firstImage\": \"\",\r\n" + 
			"            \"readCount\": 0,\r\n" + 
			"            \"sidoCode\": 35,\r\n" + 
			"            \"gugunCode\": 2,\r\n" + 
			"            \"latitude\": 35.7619577,\r\n" + 
			"            \"longitude\": 129.3655037\r\n" + 
			"          }\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"          \"contentId\": 125406,\r\n" + 
			"          \"planId\": 27,\r\n" + 
			"          \"order\": 1,\r\n" + 
			"          \"day\": 2,\r\n" + 
			"          \"attractionDto\": {\r\n" + 
			"            \"contentId\": 125406,\r\n" + 
			"            \"contentTypeId\": 12,\r\n" + 
			"            \"title\": \"비슬산자연휴양림\",\r\n" + 
			"            \"addr1\": \"대구광역시 달성군 유가읍 일연선사길 61\",\r\n" + 
			"            \"addr2\": \"\",\r\n" + 
			"            \"firstImage\": \"http://tong.visitkorea.or.kr/cms/resource/62/219162_image2_1.jpg\",\r\n" + 
			"            \"readCount\": 0,\r\n" + 
			"            \"sidoCode\": 4,\r\n" + 
			"            \"gugunCode\": 3,\r\n" + 
			"            \"latitude\": 35.69138039,\r\n" + 
			"            \"longitude\": 128.5159774\r\n" + 
			"          }\r\n" + 
			"        },\r\n" + 
			"        {\r\n" + 
			"          \"contentId\": 125407,\r\n" + 
			"          \"planId\": 27,\r\n" + 
			"          \"order\": 2,\r\n" + 
			"          \"day\": 2,\r\n" + 
			"          \"attractionDto\": {\r\n" + 
			"            \"contentId\": 125407,\r\n" + 
			"            \"contentTypeId\": 12,\r\n" + 
			"            \"title\": \"불정자연휴양림\",\r\n" + 
			"            \"addr1\": \"경상북도 문경시 불정길 180\",\r\n" + 
			"            \"addr2\": \"(불정동)\",\r\n" + 
			"            \"firstImage\": \"http://tong.visitkorea.or.kr/cms/resource/83/1070183_image2_1.jpg\",\r\n" + 
			"            \"readCount\": 0,\r\n" + 
			"            \"sidoCode\": 35,\r\n" + 
			"            \"gugunCode\": 7,\r\n" + 
			"            \"latitude\": 36.61882624,\r\n" + 
			"            \"longitude\": 128.1342659\r\n" + 
			"          }\r\n" + 
			"        }\r\n" + 
			"      ]\r\n" + 
			"    }\r\n" + 
			"  },\r\n" + 
			"  \"myRate\": 3\r\n" + 
			"}")))
	@GetMapping("/{reviewId}")
	public ResponseEntity<Object> getReview(@PathVariable int reviewId, HttpServletRequest request) {
		Map<String, Integer> req = new HashMap<String, Integer>();
		ReviewDto reviewDto = null;
		List<CommentDto> commentList = null;

		if (!jwtService.checkToken(request.getHeader("access-token"))) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		int userId = 0;
		try {
			userId = (Integer) jwtService.get(null).get("userId");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		int userId = ((UserDto) session.getAttribute("userinfo")).getUserId();

		req.put("userId", userId);
		req.put("reviewId", reviewId);
		
		int myRate = 0;
		Map<String, Object> res = new HashMap<String, Object>();
		try {
			reviewDto = reviewService.getReview(reviewId);
			commentList = reviewService.listComment(reviewId);
			myRate = reviewService.getRate(req);
			res.put("review", reviewDto);
			res.put("comments", commentList);
			res.put("myRate", myRate);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Object>(res, HttpStatus.OK);
	}

	@ApiOperation(value = "인기 리뷰리스트 가져오기", notes = "rating 점수 상위의 인기 리뷰 6개를 가져옵니다.")
	@ApiResponse(code = 200, message = "OK", examples = @Example(value = @ExampleProperty(mediaType = "*/*", value = "[\r\n"
			+ "  {\r\n" + "    \"reviewId\": 44,\r\n" + "    \"subject\": \"새 리뷰 제목\",\r\n"
			+ "    \"content\": \"새 리뷰 내용\",\r\n" + "    \"userId\": 25,\r\n" + "    \"userName\": \"관리자\",\r\n"
			+ "    \"registTime\": \"2023-05-02 13:14:18\",\r\n" + "    \"planId\": 27,\r\n" + "    \"rating\": 5,\r\n"
			+ "  },\r\n" + "  {\r\n" + "    \"reviewId\": 51,\r\n" + "    \"subject\": \"새 리뷰 제목3636\",\r\n"
			+ "    \"content\": \"새 리뷰 내용3\",\r\n" + "    \"userId\": 25,\r\n" + "    \"userName\": \"관리자\",\r\n"
			+ "    \"registTime\": \"2023-05-02 13:17:15\",\r\n" + "    \"planId\": 27,\r\n" + "    \"rating\": 4,\r\n"
			+ "  },\r\n" + "  {\r\n" + "    \"reviewId\": 41,\r\n" + "    \"subject\": \"여행 리뷰\",\r\n"
			+ "    \"content\": \"재미있었어요!\",\r\n" + "    \"userId\": 25,\r\n" + "    \"userName\": \"관리자\",\r\n"
			+ "    \"registTime\": \"2023-05-02 11:49:08\",\r\n" + "    \"planId\": 27,\r\n" + "    \"rating\": 3,\r\n"
			+ "  },\r\n" + "  {\r\n" + "    \"reviewId\": 47,\r\n" + "    \"subject\": \"새 리뷰 제목2\",\r\n"
			+ "    \"content\": \"새 리뷰 내용2\",\r\n" + "    \"userId\": 25,\r\n" + "    \"userName\": \"관리자\",\r\n"
			+ "    \"registTime\": \"2023-05-02 13:15:09\",\r\n" + "    \"planId\": 27,\r\n" + "    \"rating\": 0,\r\n"
			+ "  }\r\n" + "]")))
	@GetMapping("/hot")
	public ResponseEntity<Object> listHotReview() {
		List<ReviewDto> reviewList = null;

		try {
			reviewList = reviewService.listHotReview();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Object>(reviewList, HttpStatus.OK);
	}

	@ApiOperation(value = "리뷰 수정", notes = "리뷰를 수정합니다.")
	@ApiImplicitParam(name = "reviewDto", value = "subject(required), content(required), reviewId(required), planId(reqired) ", required = true, dataType = "userDto", dataTypeClass = Object.class, paramType = "body", example = " { \"subject\" : \"제주도 보단 부산이지\" "
			+ ", \"content\" : \"퍼뜩 오이소~\" " + ", \"planId\" : 27 " + ", \"reviewId\" : 41 }")
	@ApiResponse(code = 200, message = "OK", examples = @Example(value = @ExampleProperty(mediaType = "*/*", value = "{\r\n"
			+ "  \"reviewId\": 54,\r\n" + "  \"subject\": \"제주도 보단 부산이지\",\r\n" + "  \"content\": \"퍼뜩 오이소~\",\r\n"
			+ "  \"userId\": 0,\r\n" + "  \"planId\": 27,\r\n" + "  \"rating\": 0,\r\n" + "}")))
	@PutMapping
	public ResponseEntity<Object> modifyReview(@RequestBody ReviewDto reviewDto) {
		try {
			if (reviewDto.getSubject() == null || reviewDto.getContent() == null)
				throw new Exception();
			reviewService.modifyReview(reviewDto);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Object>(reviewDto, HttpStatus.OK);
	}

	@ApiOperation(value = "리뷰 삭제하기", notes = "리뷰 기본 키를 받아 리뷰를 삭제합니다")
	@ApiImplicitParam(name = "reviewId", value = "리뷰의 기본 키(reviewId) ", required = true, dataType = "int", dataTypeClass = Integer.class, paramType = "path", example = "41")
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<Object> deleteReview(@PathVariable("reviewId") int reviewId) {
		try {
			reviewService.deleteReview(reviewId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@ApiOperation(value = "리뷰 평점", notes = "리뷰에 별점을 매깁니다.")
	@ApiImplicitParam(name = "req", value = " reviewId(required), rating(required) ", required = true, dataType = "Map<String, Object>", dataTypeClass = Object.class, paramType = "body", example = " { \"reviewId\" : 471 "
			+ ", \"rating\" : 4 }")
	@PostMapping("/rating")
	public ResponseEntity<Object> rateReview(@RequestBody Map<String, Object> req, HttpServletRequest request) {

		if (!jwtService.checkToken(request.getHeader("access-token"))) {
			return new ResponseEntity<Object>(HttpStatus.UNAUTHORIZED);
		}
		int userId = 0;
		try {
			userId = (Integer) jwtService.get(null).get("userId");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		int userId = ((UserDto) session.getAttribute("userinfo")).getUserId();

		req.put("userId", userId);
		float result = -1;

		try {
			reviewService.rateReview(req);
			int reviewId = (Integer)req.get("reviewId");
			result = reviewService.getRateAvg(reviewId);
		} catch (Exception e) {
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Object>(result, HttpStatus.OK);
	}

	@ApiOperation(value = "리뷰의 댓글 리스트", notes = "리뷰에 달린 댓글을 가져옵니다.")
	@ApiImplicitParam(name = "reviewId", value = "리뷰의 기본 키(reviewId) ", required = true, dataType = "int", dataTypeClass = Integer.class, paramType = "path", example = "41")
	@ApiResponse(code = 200, message = "OK", examples = @Example(value = @ExampleProperty(mediaType = "*/*", value = "[\r\n"
			+ "  {\r\n" + "    \"userId\": 26,\r\n" + "    \"reviewId\": 41,\r\n" + "    \"commentId\": 11,\r\n"
			+ "    \"registerTime\": \"2023-05-02 13:58:37\",\r\n" + "    \"userName\": \"김싸피\",\r\n"
			+ "    \"content\": \"와! 가보고 싶어요.\",\r\n" + "    \"mine\": false\r\n" + "  },\r\n" + "  {\r\n"
			+ "    \"userId\": 25,\r\n" + "    \"reviewId\": 41,\r\n" + "    \"commentId\": 12,\r\n"
			+ "    \"registerTime\": \"2023-05-02 13:58:46\",\r\n" + "    \"userName\": \"관리자\",\r\n"
			+ "    \"content\": \"그쵸?\",\r\n" + "    \"mine\": false\r\n" + "  }\r\n" + "]")))
	@GetMapping("/{reviewId}/comments")
	public ResponseEntity<Object> listComment(@PathVariable("reviewId") int reviewId) {
		List<CommentDto> commentList = null;

		try {
			commentList = reviewService.listComment(reviewId);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Object>(commentList, HttpStatus.OK);
	}
	
	
	@GetMapping
	public ResponseEntity<Object> getReviews(@RequestParam("page") int page, @RequestParam(name="sort", required=false) String sort, @RequestParam(name="keyword", required=false) String keyword) {

	    try {
	        Map<String, Object> req = new HashMap<String, Object>();
	        req.put("sort", sort);
	        if (keyword != null)
	            req.put("keyword", keyword);    
	        int offset = (page-1)*9;
	        req.put("offset", offset);
	        List<ReviewDto> reviewList = reviewService.getReviews(req);
	        return new ResponseEntity<Object>(reviewList, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
	    }
	    
	}
	
	@GetMapping("/total")
	public ResponseEntity<Object> totalReviews(@RequestParam(name="keyword", required=false) String keyword) {
	    try {
	        Map<String, Object> req = new HashMap<String, Object>();
	        if (keyword != null)
	            req.put("keyword", keyword);
	        int total =  reviewService.totalReviews(req);
	        return new ResponseEntity<Object>(total, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
	    }
	    
	}
	
	@GetMapping("/ids/{id}")
	public ResponseEntity<Object> getReviewsById(@PathVariable String id) {
	    try {
	    	List<ReviewDto> reviewList = null;
	  
	    	reviewList =  reviewService.getReviewsById(id);
	        return new ResponseEntity<Object>(reviewList, HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
	    }
	    
	}
	
	
	

	
	

}
