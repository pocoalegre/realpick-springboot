package com.realpick.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /* swagger帮助生成接口文档 */
    //1.配置生成文档信息
    //2.配置生成规则

    /* Docket封装接口文档 */
    @Bean
    public Docket getDocket(){

        //创建封面信息对象
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        apiInfoBuilder.title("雀石优选后端接口说明")
                      .description("雀石优选后端接口规范")
                      .version("0.0.1-SNAPSHOT");
        ApiInfo apiInfo = apiInfoBuilder.build();

        //指定文档封面信息
        return new Docket(DocumentationType.SWAGGER_2)
                        .apiInfo(apiInfo)
                        .select()
                        .apis(RequestHandlerSelectors.basePackage("com.realpick.controller"))
                        .paths(PathSelectors.any())
                        .build();
    }

}
