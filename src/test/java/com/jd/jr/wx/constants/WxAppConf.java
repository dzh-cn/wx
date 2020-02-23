package com.jd.jr.wx.constants;

public enum WxAppConf {
    me("wx7085803d253ace50", "aee86d03ec76aef86c6b2409f52dda6b", "oLJwdt4JuRVIoECGiJ8ZL85Qg50k"),
    ze("wx81c571702b5f3680", "ea00bac8ac711f0fb6aa5cc68ec03c5d", "oylauwX9xtuanvKxpsm8DTE-Ow_8"),
    campls("wxbed254b432da90b8", "e559856e8c5cc56a9afa9805a8181c67", "otC9mt_XtmNkWmOi1iLf5D8FBDeM"),
    ;

    WxAppConf(String app, String sec, String openId) {
        this.app = app;
        this.sec = sec;
        this.openId = openId;
    }

    private String app;
    private String sec;
    private String openId;

    public String getApp() {
        return app;
    }

    public String getSec() {
        return sec;
    }

    public String getOpenId() {
        return openId;
    }
}
