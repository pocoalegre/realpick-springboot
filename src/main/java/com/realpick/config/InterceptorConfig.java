package com.realpick.config;

import com.realpick.interceptor.CheckToeknInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private CheckToeknInterceptor checkToeknInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        List<String> addPath = new ArrayList<>();
        addPath.add("/admin/**");
        addPath.add("/banner/**");
        addPath.add("/category/**");
        addPath.add("/delivery/**");
        addPath.add("/orderDetail/**");
        addPath.add("/order/**");
        addPath.add("/product/**");
        addPath.add("/productParam/**");
        addPath.add("/productSku/**");
        addPath.add("/shoppingCart/**");
        addPath.add("/userAddr/**");
        addPath.add("/user/**");

        List<String> excludePath = new ArrayList<>();
        excludePath.add("/admin/regist");
        excludePath.add("/admin/login");
        excludePath.add("/admin/createCode");
        excludePath.add("/banner/indexList");
        excludePath.add("/category/indexList");
        excludePath.add("/product/indexList");
        excludePath.add("/product/listByCategory");
        excludePath.add("/product/voById");
        excludePath.add("/productParam/byProductId");
        excludePath.add("/productSku/byProductId");
        excludePath.add("/user/regist");
        excludePath.add("/user/login");
        excludePath.add("/user/createCode");

        //设置拦截规则
        registry.addInterceptor(checkToeknInterceptor)
                .addPathPatterns(addPath)
                .excludePathPatterns(excludePath);
    }
}
