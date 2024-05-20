package com.ssafy.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

// TODO: 14. Swagger JavaConfig 작성
// {서버주소}/swagger-ui.html로 접속

@Configuration
public class SwaggerConfiguration {

	// OpenAPI 객체 생성
	@Bean
	public OpenAPI openAPI() {
		Info info = new Info().title("SSAFY 여행 관통PJT API").description("<h3>관통PTJ에서 사용되는 REST API에 대한 문서를 제공한다.</h3>")
				.version("1.0")
				.contact(new Contact().name("SSAFY").email("ssafy@ssafy.com").url("https://edu.ssafy.com"));
		// OpenAPI 객체를 생성한다.
		return new OpenAPI().components(new Components()).info(info);
	}

	// 어떤 REST API를 문서화 할 것인지 매핑하기
	@Bean
	public GroupedOpenApi boardApi() {
		return GroupedOpenApi
				.builder()
				.group("board")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/boardapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
	
	@Bean
	public GroupedOpenApi userApi() {
		return GroupedOpenApi
				.builder()
				.group("user")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/userapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
	
	@Bean
	public GroupedOpenApi noticeApi() {
		return GroupedOpenApi
				.builder()
				.group("notice")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/noticeapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
	
	@Bean
	public GroupedOpenApi districtApi() {
		return GroupedOpenApi
				.builder()
				.group("district")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/districtapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
	
	@Bean
	public GroupedOpenApi attractionApi() {
		return GroupedOpenApi
				.builder()
				.group("attraction")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/attractionapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
	
	@Bean
	public GroupedOpenApi tripApi() {
		return GroupedOpenApi
				.builder()
				.group("trip")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/tripapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
	
	@Bean
	public GroupedOpenApi coomentApi() {
		return GroupedOpenApi
				.builder()
				.group("comment")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/commentapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
	
	
	@Bean
	public GroupedOpenApi hotplaceApi() {
		return GroupedOpenApi
				.builder()
				.group("hotplace")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/hotplaceapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
	
	@Bean
	public GroupedOpenApi favoriteApi() {
		return GroupedOpenApi
				.builder()
				.group("favorite")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/favoriteapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
	
	@Bean
	public GroupedOpenApi tripplanApi() {
		return GroupedOpenApi
				.builder()
				.group("tripplan")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/tripplanapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
	
	@Bean
	public GroupedOpenApi friendApi() {
		return GroupedOpenApi
				.builder()
				.group("friend")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/friendapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
	
	@Bean
	public GroupedOpenApi messageApi() {
		return GroupedOpenApi
				.builder()
				.group("message")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/messageapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
	
	@Bean
	public GroupedOpenApi newsApi() {
		return GroupedOpenApi
				.builder()
				.group("news")  // 그룹 이름 ( 원하는 이름으로 작성 )
				.pathsToMatch("/newsapi/**") // 경로 패턴이 일치하는 것들을 API로 인식
				.build();
	}
}
