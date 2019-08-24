package com.tsrj.qiezi.httprequest;


import java.util.HashMap;


/**
 *
 */
public class HttpHead {

    private int userId;
    private String session;
    private String cv;// 应用版本
    private String tt;//手机型号
    private String os;//手机类型
    //        private String sysInfo;//设备信息   品牌-系统版本
    private String lan;//语言 zh-cn 简体 en 英文
    private String osv; //系统版本号

    public static HashMap getHearMap() {
        //String session = PreferencesUtils.getSession();
        HashMap headMap = new HashMap();
        //headMap.put("sessionId", session);
//        LogUtil.info("sessionId:"+session);
        return  headMap;
    }

    public HttpHead() {
    }

}
