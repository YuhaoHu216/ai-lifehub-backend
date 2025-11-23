package space.huyuhao.user.enums;

public enum OAuthType {

    QQ("qq", "QQOAuthService"),
    WECHAT("wechat", "WeChatOAuthService"),
    GITHUB("github", "GithubOAuthService");

    private final String code;          // 前端传的类型
    private final String serviceName;   // 对应策略类名（用于工厂）

    OAuthType(String code, String serviceName) {
        this.code = code;
        this.serviceName = serviceName;
    }

    public String getCode() {
        return code;
    }

    public String getServiceName() {
        return serviceName;
    }

    // 通过 code 找类型（前端传 "qq"、"wechat"）
    public static OAuthType fromCode(String code) {
        for (OAuthType type : OAuthType.values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("不支持的第三方登录类型: " + code);
    }
}
