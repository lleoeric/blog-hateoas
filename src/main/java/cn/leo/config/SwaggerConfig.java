package cn.leo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 1. swagger配置类
 */
//开启 OpenApi
@EnableOpenApi
@Configuration
public class SwaggerConfig {

    public Docket docket(){
        Docket docket = new Docket(DocumentationType.OAS_30);
        docket.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mindskip.xzs.controller"))
                .paths(PathSelectors.any()).build();
        return docket;
    }

    private ApiInfo apiInfo(){
        Contact contact = new Contact("作者 mxy", "作者http://www.xxx.com","example@qq.com");
        ApiInfo apiInfo = new ApiInfo(
                "Swagger3接口文档",
                "SpringBoot 整合 Swagger3 生成接口文档!",
                "1.0.0",
                "termsOfServiceUrl",
                contact,
                "这里是license",
                "这里是licenseUrl",
                new ArrayList());
        return apiInfo;
    }

}