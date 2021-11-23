package com.realpick.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class CodeGenerator {

    public static void main(String[] args) {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java"); //生成路径
        gc.setAuthor("pocoalegre"); //设置作者
        gc.setOpen(false); //是否打开输出目录
        gc.setFileOverride(true); //覆盖已有文件
        gc.setSwagger2(true); //开启swagger2
        gc.setServiceName("%sService"); //生成service接口名字首字母为I，这样设置就没有
        gc.setBaseResultMap(true); //生成resultMap
        mpg.setGlobalConfig(gc);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/realpick?characterEncoding=utf-8&useSSL=false");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.realpick");
        mpg.setPackageInfo(pc);

        //策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setNaming(NamingStrategy.underline_to_camel); //数据库表映射到实体的命名
        sc.setColumnNaming(NamingStrategy.underline_to_camel); //数据库表字段映射到实体的命名
        sc.setEntityLombokModel(true); //使用lombok注解
        sc.setLogicDeleteFieldName("deleted"); //逻辑删除属性名称
        String[] tables = new String[]{
                "admins", "users", "user_addr", "banner", "product", "product_sku",
                "product_param", "product_img", "category", "shopping_cart",
                "orders", "order_detail", "delivery"
        }; //自动生成表
        sc.setInclude(tables); //需要包含的表名
        mpg.setStrategy(sc);

        //执行生成
        mpg.execute();

    }
}
