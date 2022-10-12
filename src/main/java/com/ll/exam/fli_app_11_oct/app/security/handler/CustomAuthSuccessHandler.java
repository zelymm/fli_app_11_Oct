package com.ll.exam.fli_app_11_oct.app.security.handler;

import com.ll.exam.fli_app_11_oct.app.security.dto.MemberContext;
import com.ll.exam.fli_app_11_oct.util.Ut;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        clearSession(request);

        SavedRequest savedRequest = requestCache.getRequest(request, response);

        /*
         prevPage가 존재하는 경우 = 사용자가 직접 /auth/login 경로로 로그인 요청
         기존 Session의 prevPage attribute 제거
         */
        String prevPage = (String) request.getSession().getAttribute("prevPage");
        if (prevPage != null) {
            request.getSession().removeAttribute("prevPage");
        }

        // 기본 URI
        String url = "/";

        /*
        savedRequest 존재하는 경우 = 인증 권한이 없는 페이지 접근
        Security Filter가 인터셉트하여 savedRequest에 세션 저장
        */
        if (savedRequest != null) {
            url = savedRequest.getRedirectUrl();
        } else if (prevPage != null && prevPage.length() > 0) {
            // 회원가입 -> 로그인으로 넘어온 경우 "/"로 redirect
            if (prevPage.contains("/member/join")) {
                url = "/";
            } else {
                url = prevPage;
            }
        }

        MemberContext memberContext = (MemberContext) authentication.getPrincipal();
        url = Ut.url.modifyQueryParam(url, "msg", memberContext.getName() + "님 환영합니다.");
        redirectStrategy.sendRedirect(request, response, url);
    }

    // 로그인 실패 후 성공 시 남아있는 에러 세션 제거
    private void clearSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
