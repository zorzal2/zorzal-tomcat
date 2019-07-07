package com.fontar.web.seguridad;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.navigator.menu.PermissionsAdapter;

import org.acegisecurity.Authentication;
import org.acegisecurity.ui.logout.LogoutHandler;
import org.acegisecurity.ui.rememberme.RememberMeServices;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fontar.util.SessionHelper;
import com.fontar.web.seguridad.menu.MenuPermissionAdapterFactory;

public class TokenBasedRememberMeServices  implements RememberMeServices , LogoutHandler {
	
	public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication){
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		MenuPermissionAdapterFactory factory  = (MenuPermissionAdapterFactory) context.getBean( "menuPermissionAdapterFactory" );
		PermissionsAdapter adapter = factory.newInstance();
		SessionHelper.setMenuPermissionsAdapter( request, adapter );
	}

	public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	public void loginFail(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
		
	}

}
