package com.example.project02last.utils;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import java.util.Collections;




//mybatis-plus代码生成器
public class CodeGenerator {
    public static void main(String[] args) {
          generate();
    }
    private static void generate(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/qing?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true", "root", "ytclove2020")
                .globalConfig(builder -> {
                    builder.author("杨添辰") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("C:\\Users\\Administrator\\Desktop\\project02last\\src\\main\\java\\"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.example.project02last") // 设置父包名
                            .moduleName(null) // 设置父包模块名  3.5.2之后mapperXml改为了xml
                            .pathInfo(Collections.singletonMap(OutputFile.xml,"C:\\Users\\Administrator\\Desktop\\project02last\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder().enableLombok();//使用lombok
                    builder.controllerBuilder().enableHyphenStyle()
                            .enableRestStyle()
                    ;//开启驼峰转连字符 就是url里面的_转换为驼峰

                    builder.addInclude("user") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀

                })
                //.templateEngine(new FreemarkerTemplateEngine())
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }
}
