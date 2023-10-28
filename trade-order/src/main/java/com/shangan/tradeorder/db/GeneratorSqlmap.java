package com.shangan.tradeorder.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GeneratorSqlmap {
    public static void main(String[] args) throws Exception {
        try {
            GeneratorSqlmap generatorSqlmap = new GeneratorSqlmap();
            generatorSqlmap.generator();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generator() {
        File configFile = new File("trade-order/src/main/resources/mybatis-generator-config.xml");
        List<String> warningInfos = new ArrayList<>();

        DefaultShellCallback callback = new DefaultShellCallback(true);

    }

}
