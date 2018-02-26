package com.post2facebook;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.post2facebook.viewController.FileValidator;

@Configuration 
@ComponentScan
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	 
	@Bean
	 public ViewResolver getViewResolver() {
		 InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		 resolver.setPrefix("/WEB-INF/view/");
		 resolver.setSuffix(".jsp");
		 resolver.setViewClass(JstlView.class);
		 return resolver;
	 }
	
	@Override
	    public void configureViewResolvers(ViewResolverRegistry registry) {
	        registry.viewResolver(getViewResolver());
	    }

	@Bean(name="multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(10240); //10Kb
        resolver.setDefaultEncoding("UTF-8");
        resolver.setResolveLazily(true);
        return resolver;
	}
	

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("validation");
        return messageSource;
    }

    @Bean(name="fileValidator")
    public FileValidator fileValidator() {
    	FileValidator fileV = new FileValidator();
    	return fileV;
    }
	
}
