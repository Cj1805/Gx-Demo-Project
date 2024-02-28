package config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.wellness.entities.UserInfo;
import com.wellness.exception.UserException;
import com.wellness.repository.UserDetailsRepository;


@Component
public class UserDetailsInfoService implements UserDetailsService {

	@Autowired
	private UserDetailsRepository detailsRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserInfo> user =  detailsRepository.findByEmail(username);
		return user.map(UserInfoUserDetails::new).orElseThrow(()-> new UsernameNotFoundException("User Not Found!!!"));
	}

}
