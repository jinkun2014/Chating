package xx.yy.simple.chat.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 */
@Slf4j
@Configuration
@ConditionalOnClass(name = "javax.servlet.http.HttpServletRequest")
public class CORSConf {

	@Bean
	public CorsFilter corsFilter() {
		if (log.isDebugEnabled()) {
			StringBuilder sb = new StringBuilder();
			sb.append("\n");
			sb.append("----------------------------------------------------------");
			sb.append("\n\t");
			sb.append("Class: \t{}.java");
			sb.append("\n\t");
			sb.append("Desc: \t{}");
			sb.append("\n");
			sb.append("----------------------------------------------------------");
			log.debug(sb.toString(), "CORSConf", "跨域配置");
		}
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		// 允许向该服务器提交请求的URI，*表示全部允许
		config.addAllowedOrigin(CorsConfiguration.ALL);
		// 允许cookies跨域
		config.setAllowCredentials(true);
		// 允许访问的头信息,*表示全部
		config.addAllowedHeader(CorsConfiguration.ALL);
		// 允许提交请求的方法，*表示全部允许
		config.addAllowedMethod(CorsConfiguration.ALL);
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	/**
	 * 配置过滤器
	 */
	@Bean
	public FilterRegistrationBean corsFilterRegistration() {
		FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(corsFilter());
		registration.addUrlPatterns("/*");
		registration.setName("corsFilter");
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registration;
	}
}
