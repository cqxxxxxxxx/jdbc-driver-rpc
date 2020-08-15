package com.cqx.jdbc.rpc;

public class Constants {
    public static final int MAJOR_VERSION = 1;
    public static final int MINOR_VERSION = 0;

    public static final String requestFactoryClazzKey = "requestFactoryClazz";
    public static final String requestFactoryClazzDefValue = "com.cqx.jdbc.rpc.client.DefRequestFactory";

    public static final String rpcSerializerClazzKey = "rpcSerializerClazz";
    public static final String rpcSerializerClazzDefValue = "com.cqx.jdbc.rpc.client.DefJacksonSerializer";

    public static final String authenticationKey = "Authentication";
    public static final String authenticationDefValue = "";

    public static final String JVM_VENDOR = System.getProperty("java.vendor");
    public static final String JVM_VERSION = System.getProperty("java.version");

    public static final String OS_NAME = System.getProperty("os.name");
    public static final String OS_ARCH = System.getProperty("os.arch");
    public static final String OS_VERSION = System.getProperty("os.version");
    public static final String PLATFORM_ENCODING = System.getProperty("file.encoding");

}
