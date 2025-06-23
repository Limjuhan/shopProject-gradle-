//package config;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.context.annotation.Primary;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
//
//import java.util.Properties;
//
//
//
//@Configuration
//@EnableAspectJAutoProxy // AOP 사용을 위한 설정
//public class MvcConfig implements WebMvcConfigurer {
//
//	// 예외처리 객체
//	@Bean
//	public SimpleMappingExceptionResolver exceptionHandler() {
//
//		SimpleMappingExceptionResolver ser = new SimpleMappingExceptionResolver();
//
//		Properties pr = new Properties();
//		pr.put("exception.ShopException", "exception");
//		// exception.ShopException 예외 발생시 exception.jsp페이지 이동
//		ser.setExceptionMappings(pr);
//		return ser;
//	}
//
//	// 기본 웹파일 처리를 위한 설정
////	@Override
////	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
////		configurer.enable();
////	}
//
//	@Bean
//	@Primary
//	@ConfigurationProperties("spring.datasource")
//	public DataSourceProperties dataSourceProperties() {
//		return new DataSourceProperties();
//	}
//
//	@Bean
//	@ConfigurationProperties("spring.datasource.hikari")
//	public HikariDataSource dataSource(DataSourceProperties properties) {
//		return properties.initializeDataSourceBuilder()
//				.type(HikariDataSource.class)
//				.build();// Connection pool 객체
//	}
//
////	@Override
////	public void addInterceptors(InterceptorRegistry registry) {
////
////		registry.addInterceptor(new BoardInterceptor())
////		.addPathPatterns("/board/write")
////		.addPathPatterns("/board/update")
////		.addPathPatterns("/board/delete");
////	}
//
//	@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // /upload/editor/** URL로 요청 시 C:/upload/editor/ 실제 파일에서 읽어옴
//        registry.addResourceHandler("/upload/editor/**")
//                .addResourceLocations("file:///C:/upload/editor/");
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
