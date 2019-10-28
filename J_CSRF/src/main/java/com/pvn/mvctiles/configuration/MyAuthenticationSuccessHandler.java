package com.pvn.mvctiles.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pvn.mvctiles.model.UserDetails;

@Component
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{
	private RequestCache requestCache = new HttpSessionRequestCache();
	
	public void setRequestCache(RequestCache requestCache) {
		this.requestCache = requestCache;
	}

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException 
	{
        request.getSession(false).setMaxInactiveInterval(600);
        
		UserDetails user = new UserDetails();
		user.setUserName(((User)authentication.getPrincipal()).getUsername());
		request.getSession(false).setAttribute("loggedInUser", user);
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest == null) 
		{
			clearAuthenticationAttributes(request);
			request.getRequestDispatcher("/app/user/dashboard").forward(request, response);
			return;
		}
		
		String targetUrlParameter = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl()
				|| (targetUrlParameter != null && StringUtils.hasText(request
						.getParameter(targetUrlParameter)))) {
			requestCache.removeRequest(request, response);
			super.onAuthenticationSuccess(request, response, authentication);

			return;
		}

		clearAuthenticationAttributes(request);

		// Use the DefaultSavedRequest URL
		String targetUrl = savedRequest.getRedirectUrl();
		logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}