package com.ssafy.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@MapperScan(basePackages = {"com.ssafy.*.dao"})
public class WebMvcConfiguration implements WebMvcConfigurer {
	
//	private SessionInterceptor sessionInterceptor;
//	
//	public WebMvcConfiguration(SessionInterceptor sessionInterceptor) {
//		this.sessionInterceptor = sessionInterceptor;
//	}
	
//	TODO: 10. Interceptor 등록
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(sessionInterceptor).addPathPatterns(Arrays.asList("/regist"));
//	}
	
//	TODO: 12. CORS 설정
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") // 모든 요청 주소를 허용
			.allowedOrigins("http://localhost:5174/") // 화이트리스트 ( 요청을 허용할 서버 주소 )
			.allowedMethods( // 허용할 Http Method
					HttpMethod.GET.name(),
					HttpMethod.POST.name(),
					HttpMethod.PUT.name(),
					HttpMethod.PATCH.name(), // 부분 수정 요청 시
					HttpMethod.DELETE.name(),
					HttpMethod.HEAD.name(), // preflight
					HttpMethod.OPTIONS.name() // preflight
			)
			.maxAge(1800); // 1800초 동안 preflight 결과를 캐시에 저장
	}
}
