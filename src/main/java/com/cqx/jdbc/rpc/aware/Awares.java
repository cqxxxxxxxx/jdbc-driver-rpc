package com.cqx.jdbc.rpc.aware;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * aware统一维护
 * 基于SPI机制 构建aware
 * @deprecated 暂时没什么必要
 */
@Deprecated
public class Awares {
    private static List<BeforeBuildAware> beforeBuildAwares = new ArrayList<>();

    static {
        ServiceLoader<BeforeBuildAware> serviceLoader = ServiceLoader.load(BeforeBuildAware.class);
        for (BeforeBuildAware aware : serviceLoader) {
            beforeBuildAwares.add(aware);
        }
    }

    public static void awareBeforeBuild() {
        for (BeforeBuildAware beforeBuildAware : beforeBuildAwares) {
            beforeBuildAware.doAware();
        }
    }
}
