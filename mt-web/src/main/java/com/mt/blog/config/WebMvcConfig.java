package com.mt.blog.config;

import com.mt.blog.interceptor.MyInterceptor;
import com.mt.blog.utils.ReadProteriesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    ReadProteriesUtil readProteriesUtil;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //资源开放
        registry.addResourceHandler("/**").addResourceLocations("file:"+ readProteriesUtil.FILE_SPACE + "/");
    }

    @Bean
    public MyInterceptor createInterceptor(){
        return new MyInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 加入拦截器
        // 拦截部分关于用户请求
        registry.addInterceptor(createInterceptor()).addPathPatterns("/user/*","/article/*","/file/*","/mail/bindEmail")
                .excludePathPatterns("/user/getArticleUser/*","/user/getUserFollowFan","/article/getArticle","/article/getHotArticle","/article/getLabelArticle","/article/updArticleInformation","/article/getArticleById","/comment/insComment");
    }
}
