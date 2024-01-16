package com.example.demoauth.utilities;

import javax.servlet.http.HttpServletRequest;

public class RegisterUtility {
    public static String getSiteURL(HttpServletRequest request){
        String siteURL = request.getRequestURI();
        return siteURL.replace(request.getServletPath(), "");
    }
}
