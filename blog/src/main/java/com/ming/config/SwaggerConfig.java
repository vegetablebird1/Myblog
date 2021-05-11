package com.ming.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    // API 文档地址：swagger-ui.html

    @Bean
    public Docket docket(Environment environment) {
        //当前环境为dev才存在swagger页面
        Profiles profiles = Profiles.of("dev");
        boolean flag = environment.acceptsProfiles(profiles);

        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        parameterBuilder.name("Authorization") // Updates the parameter name
                .description("JSON Web Token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        parameters.add(parameterBuilder.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.any()) // Any RequestHandler satisfies this condition
                .paths(PathSelectors.any())// Any path satisfies this condition
                .build()
                .globalOperationParameters(parameters); // Adds default parameters which will be applied to all operations.
    }

    //swagger信息
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder() // Builds the api information
                .title("myblog")
                .description("Api文档")
                .version("1.0.0")
                .build();
    }
}

