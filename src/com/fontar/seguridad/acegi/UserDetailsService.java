package com.fontar.seguridad.acegi;

import java.util.Properties;

import org.acegisecurity.userdetails.UserDetails;
import org.acegisecurity.userdetails.UsernameNotFoundException;
import org.acegisecurity.userdetails.memory.InMemoryDaoImpl;
import org.acegisecurity.userdetails.memory.UserMap;
import org.springframework.dao.DataAccessException;

public class UserDetailsService extends InMemoryDaoImpl {

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		super.afterPropertiesSet();
	}

	@Override
	public UserMap getUserMap() {
		// TODO Auto-generated method stub
		return super.getUserMap();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		// TODO Auto-generated method stub
		return super.loadUserByUsername(username);
	}

	@Override
	public void setUserMap(UserMap userMap) {
		// TODO Auto-generated method stub
		super.setUserMap(userMap);
	}

	@Override
	public void setUserProperties(Properties props) {
		// TODO Auto-generated method stub
		super.setUserProperties(props);
	}

	
	
}
