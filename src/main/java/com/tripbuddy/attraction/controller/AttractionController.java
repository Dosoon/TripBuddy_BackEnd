package com.tripbuddy.attraction.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tripbuddy.attraction.model.dto.AreaDto;
import com.tripbuddy.attraction.model.dto.AttractionDto;
import com.tripbuddy.attraction.model.service.AttractionService;
import com.tripbuddy.user.model.dto.UserDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/attrs")
@Api(tags = { "관광지 컨트롤러" })
public class AttractionController {
	
	
	@Autowired
	AttractionService attractionService;
	
	@ApiOperation(value = "시도리스트", notes = "시도코드를 모두 가져옵니다.")
	@GetMapping("/areacodes")
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 1,\r\n" + 
            		"    \"areaName\": \"서울\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 2,\r\n" + 
            		"    \"areaName\": \"인천\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 3,\r\n" + 
            		"    \"areaName\": \"대전\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 4,\r\n" + 
            		"    \"areaName\": \"대구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 5,\r\n" + 
            		"    \"areaName\": \"광주\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 6,\r\n" + 
            		"    \"areaName\": \"부산\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 7,\r\n" + 
            		"    \"areaName\": \"울산\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 8,\r\n" + 
            		"    \"areaName\": \"세종특별자치시\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 31,\r\n" + 
            		"    \"areaName\": \"경기도\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 32,\r\n" + 
            		"    \"areaName\": \"강원도\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 33,\r\n" + 
            		"    \"areaName\": \"충청북도\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 34,\r\n" + 
            		"    \"areaName\": \"충청남도\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 35,\r\n" + 
            		"    \"areaName\": \"경상북도\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 36,\r\n" + 
            		"    \"areaName\": \"경상남도\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 37,\r\n" + 
            		"    \"areaName\": \"전라북도\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 38,\r\n" + 
            		"    \"areaName\": \"전라남도\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 39,\r\n" + 
            		"    \"areaName\": \"제주도\"\r\n" + 
            		"  }\r\n" + 
            		"]"
        )
    )
)
	public ResponseEntity<Object> getSiDoAreaCodes(){
		List<AreaDto> list = null;
		try {
			list = attractionService.getSidoAreaCodes();
			
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	
	@ApiOperation(value = "구군 리스트", notes = "시도코드에 해당하는 구군을 모두 가져옵니다.")
	@ApiImplicitParam(name = "sido", value = "시도코드", required = true,dataType = "int", dataTypeClass = Integer.class, paramType = "path", defaultValue = "", example = "1")
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 1,\r\n" + 
            		"    \"areaName\": \"강남구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 2,\r\n" + 
            		"    \"areaName\": \"강동구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 3,\r\n" + 
            		"    \"areaName\": \"강북구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 4,\r\n" + 
            		"    \"areaName\": \"강서구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 5,\r\n" + 
            		"    \"areaName\": \"관악구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 6,\r\n" + 
            		"    \"areaName\": \"광진구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 7,\r\n" + 
            		"    \"areaName\": \"구로구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 8,\r\n" + 
            		"    \"areaName\": \"금천구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 9,\r\n" + 
            		"    \"areaName\": \"노원구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 10,\r\n" + 
            		"    \"areaName\": \"도봉구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 11,\r\n" + 
            		"    \"areaName\": \"동대문구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 12,\r\n" + 
            		"    \"areaName\": \"동작구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 13,\r\n" + 
            		"    \"areaName\": \"마포구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 14,\r\n" + 
            		"    \"areaName\": \"서대문구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 15,\r\n" + 
            		"    \"areaName\": \"서초구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 16,\r\n" + 
            		"    \"areaName\": \"성동구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 17,\r\n" + 
            		"    \"areaName\": \"성북구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 18,\r\n" + 
            		"    \"areaName\": \"송파구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 19,\r\n" + 
            		"    \"areaName\": \"양천구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 20,\r\n" + 
            		"    \"areaName\": \"영등포구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 21,\r\n" + 
            		"    \"areaName\": \"용산구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 22,\r\n" + 
            		"    \"areaName\": \"은평구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 23,\r\n" + 
            		"    \"areaName\": \"종로구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 24,\r\n" + 
            		"    \"areaName\": \"중구\"\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"areaCode\": 25,\r\n" + 
            		"    \"areaName\": \"중랑구\"\r\n" + 
            		"  }\r\n" + 
            		"]"
        )
    )
)
	@GetMapping("/areacodes/{sido}")
	public ResponseEntity<Object> getGugunAreaCodes(@PathVariable("sido") int sido){
		List<AreaDto> list = null;
		
		
		try {
			list = attractionService.getGugunAreaCodes(sido);
			
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	@ApiOperation(value = "관광지 검색", notes = "지도 혹은 검색창에서 조건에 해당하는 관광지를 찾습니다.")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "sido", value = "시도코드", required = false, dataTypeClass = Integer.class,  dataType = "int", paramType = "query", example="1"),
        @ApiImplicitParam(name = "gugun", value = "구군코드", required = false, dataTypeClass = Integer.class, dataType = "int", paramType = "query", example="1"),
        @ApiImplicitParam(name = "type", value = "관광지 타입 코드", required = false, dataTypeClass = Integer.class,  dataType = "int",paramType = "query", example="39"),
        @ApiImplicitParam(name = "page", value = "페이징 처리를 위한 페이지", required = true, dataTypeClass = Integer.class, dataType = "int", paramType = "query", example="1"),
        @ApiImplicitParam(name = "swLng", value = "longitude 비교를 위한 sw값", required = false, dataTypeClass = Double.class, dataType = "double", paramType = "query", example="127.0"),
        @ApiImplicitParam(name = "neLng", value = "longitude 비교를 위한 ne값", required = false,dataTypeClass = Double.class, dataType = "double", paramType = "query", example="127.1"),
        @ApiImplicitParam(name = "swLat", value = "latitude 비교를 위한 sw값", required = false,dataTypeClass = Double.class, dataType = "double",paramType = "query", example="37.4"),
        @ApiImplicitParam(name = "neLat", value = "latitude 비교를 위한 ne값", required = false,dataTypeClass = Double.class, dataType = "double", paramType = "query", example="37.6"),
        @ApiImplicitParam(name = "keyword", value = "검색 키워드", required = false, dataTypeClass = String.class, dataType = "String", paramType = "query", example=""),
	})
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"[\r\n" + 
            		"  {\r\n" + 
            		"    \"contentId\": 126486,\r\n" + 
            		"    \"contentTypeId\": 12,\r\n" + 
            		"    \"title\": \"도산공원\",\r\n" + 
            		"    \"addr1\": \"서울특별시 강남구 도산대로45길 20\",\r\n" + 
            		"    \"addr2\": \"\",\r\n" + 
            		"    \"firstImage\": \"http://tong.visitkorea.or.kr/cms/resource/60/2678560_image2_1.jpg\",\r\n" + 
            		"    \"readCount\": 0,\r\n" + 
            		"    \"sidoCode\": 1,\r\n" + 
            		"    \"gugunCode\": 1,\r\n" + 
            		"    \"latitude\": 37.52146325,\r\n" + 
            		"    \"longitude\": 127.0338117,\r\n" + 
            		"  },\r\n" + 
            		"  {\r\n" + 
            		"    \"contentId\": 126504,\r\n" + 
            		"    \"contentTypeId\": 12,\r\n" + 
            		"    \"title\": \"봉은사(서울)\",\r\n" + 
            		"    \"addr1\": \"서울특별시 강남구 봉은사로 531\",\r\n" + 
            		"    \"addr2\": \"\",\r\n" + 
            		"    \"firstImage\": \"http://tong.visitkorea.or.kr/cms/resource/37/2652137_image2_1.jpg\",\r\n" + 
            		"    \"readCount\": 0,\r\n" + 
            		"    \"sidoCode\": 1,\r\n" + 
            		"    \"gugunCode\": 1,\r\n" + 
            		"    \"latitude\": 37.51587822,\r\n" + 
            		"    \"longitude\": 127.0577486,\r\n" + 
            		"  }\r\n" + 
            		"  ]"
        )
    )
)
	@GetMapping("/search")
	public ResponseEntity<Object> search(@ApiIgnore @RequestParam Map<String, Object> req){
		List<AreaDto> list = null;
		
		try {
			list = attractionService.search(req);
			
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	
	@ApiOperation(value = "관광지 정보", notes = "관광지 코드(contentId)에 해당하는 관광지 정보를 가져옵니다.")
	@ApiResponse(code=200,
    message="OK",
    examples=@Example(
        value = @ExampleProperty(
            mediaType = "*/*",
            value = 	"{\r\n" + 
            		"  \"contentId\": 125408,\r\n" + 
            		"  \"contentTypeId\": 12,\r\n" + 
            		"  \"title\": \"청송 자연휴양림 퇴적암층 (청송 국가지질공원)\",\r\n" + 
            		"  \"addr1\": \"경상북도 청송군 부남면 청송로 3478-96\",\r\n" + 
            		"  \"addr2\": \"(부남면)\",\r\n" + 
            		"  \"firstImage\": \"http://tong.visitkorea.or.kr/cms/resource/48/2533748_image2_1.jpg\",\r\n" + 
            		"  \"readCount\": 0,\r\n" + 
            		"  \"sidoCode\": 35,\r\n" + 
            		"  \"gugunCode\": 21,\r\n" + 
            		"  \"latitude\": 36.31791942,\r\n" + 
            		"  \"longitude\": 129.0537206,\r\n" + 
            		"}"
        )
    )
)
	@GetMapping("/{contentId}")
	public ResponseEntity<Object> attractionInfo(@ApiParam(value = "관광지 코드(contentId)", example="126401") @PathVariable("contentId") int contentId){
		AttractionDto attractionDto = null;
		
		try {
			attractionDto = attractionService.attractionInfo(contentId);
			
			return new ResponseEntity<Object>(attractionDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
	}
	
	
	
	

}
