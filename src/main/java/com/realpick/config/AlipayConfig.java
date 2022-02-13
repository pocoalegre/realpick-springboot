package com.realpick.config;

public class AlipayConfig {

    //沙箱APP_ID
    public static final String APP_ID = "2021000118635648";

    //接口名称
    public static final String METHOD = "alipay.trade.page.pay";

    //应用私钥
    public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCm2lIMMVe6hrYiR8VUGt2eyP055giFyOnWEWHBrmyRY74nRzZSygZ6TyxDyoUAzloiBtMlvH8hZGGMq1fNzh7eOGv2JZWmMXzuCBd4EO6QeKFjenhoJ324xJO+3fek+c5RiwtU7krDs6t9gAnx023UbCKwT5bckeHD4LF9CWoenCetHE29F32Gvobng0jHidTFadWGdXAGK3D3/w6C+jCYeOyvtjH7tX78kTLIEEYs1JNSTOvWLANnTpWIncq2w7bSz57gPmxP+H+VQlP3u44rQXLFKMM9aUmgcrjPLQiYh8pFJpaUC913p/ZzSat59+AEnVvl3PQFF2TEyhLj6a7bAgMBAAECggEAbUOI+U0SuyZefWJsmaAiQ0oxccnvCNVCNBob3z6/ABLGQmiC7YI47b2NKjOkF5wKMEAx44Nl6VMYAotuK2rwket237FLJ5M+0Y3g+JJ194vXfBKGaQZ7cRTh/9rRwmqILqQUXqDRuwico8nGOWOQh8vsh3MHQxgRNTmeYVhZ8wKVGJGw/nZ1jkTA6/rW54mQVzyBMqedpI7S2DARqKLOe74n56tFP5FERyT98G6JLNNR32Vypnwo/dVxvTyYWQPN9KxMGQpiCPKcxaOHVejB+bfXuQqxEuZAaa5mx/jwiC7xEoHcRxwkXmD4Y0sAm1qZQYL4Z4HO4pKEZjPhLvnEEQKBgQDnc6sILrVjzX/mlnxXrCbi58v0DcjMHVqx4oggvj+bMRfA4hESq3chEotLPwsjl5PRpFyipjA4sAYRIflnRr7TwIpytFpcSV+TIohPtZ9Po42a61K06Wjy1MtseDekXBfhlyCCfwnRE4G9l2MGkcl9F7D7ybQ64K2VfSJM1q43IwKBgQC4jKxkuO9Q2pG6wUcn4Bj5OMNL7w0LfBUjhDqtXbjvwiaywzSIjIpu+LsspKo+I6DunZGY39Iu4VuYh10484E1shtdfnk0gXj4hRK7+MOcaRE9lCmW0+Uebr/syLmJJAykzquslV9R/mPrZVmk4AB//4z6R1IQdi3eF7mTrZiA6QKBgHvldSaaKktUlCTRZZpg8yf/wTPRtS1egVA73Xb5oH7o1rhPy6fc1ZaEoMAU7VeOKnjMrTH8GFAgGf9N2n4oZrpflT01wjEordrLX4Cbzr4I5SKK2ekyERX8RzC3YcG/8qC2D5cLfivHb2oYAdBgdiVaa0k9fhcOcp1VMOlE+llVAoGAUBPSNf488XN1qnh1PKsziuiDbW5z1ZjovzmAEHvLZFV1XznukJWS9+X7qbplmILzMLa0V4Y++FKW4Q9TdwPJ/BelYiMfypWnfQaZA3FEx5ibf4HLrgORtmvN4ZuTThuTVOvMeB10d+qPRrP6et0rul6TuIG77SKqcmUPBDJeKwkCgYEAqSebjLNKN/a78+/5AvTrOL03144iwzJKxM/oekQ5JvQUa8bxQZxOzzoaO6+zkbGNLaHgVNITrGGLcpKqvpywM5JAMkx0zjNtajqXSGAepj5bxBAaw2tKqfskGz6QAA0zp42dFLH/3uPihffuXafLqtlTDB2g2p4EBsavxik4xj0=";

    //支付宝公钥
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwkrwiuogyYhxF8cgQFcEvEfUItUxS8PJcjOwcVqtIjvBX+g/ssZHphyU1kIVR4Ji/TrKmTHdusOk/lbmahsntRfeUYmXTI7x3G3e2VsX6jE97RtCY0qdTSfz4x5HnycKClAbMb8taZkzw6Z6BOtHK8s39g3qXJ6ufjwcVH27tO3IRLOzHAORrqT5jvDm0gvF12jjhOgY1DjIkY6q6F0z6sTXXWQxl/V2D9ZFR2Kj2D8foseqinCyhxZ73RNRxokfRJfinYvLxKTFxuhogYUlq+QiGd0z8hfpqRyl7cVgMELPE+wkOfpbnEXMzG8CGGB5GjHj1bFuEWd65ZJA+D6LxwIDAQAB";

    //字符编码
    public static final String CHARSET = "UTF-8";

    //支付宝网关地址
    public static final String GATEWAY_URL = "https://openapi.alipaydev.com/gateway.do";

    //格式化格式
    public static final String FORMAT = "JSON";

    //签名方式
    public static final String SIGN_TYPE = "RSA2";

    //支付宝异步通知路径,付款完毕后服务器异步通知页面路径,必须为公网地址
    public static final String NOTIFY_URL = "http://bmyfr8.natappfree.cc/pay/notify";

    //支付宝同步通知路径,付款完毕后服务器同步通知页面路径,可以不是公网地址
    public static final String RETURN_URL = "http://localhost:8181/userOrder";

}
