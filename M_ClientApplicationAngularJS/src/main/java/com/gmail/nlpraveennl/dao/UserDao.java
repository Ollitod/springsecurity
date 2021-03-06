package com.gmail.nlpraveennl.dao;

import java.util.List;

import com.gmail.nlpraveennl.model.UserDetails;
import com.gmail.nlpraveennl.vo.UserVO;

public interface UserDao
{

	/**
	 * @param userDetails
	 * @return
	 */
	public UserDetails addUser(UserDetails userDetails);

	/**
	 * @param userDetails
	 * @return
	 */
	public UserDetails modifyUser(UserDetails userDetails);

	/**
	 * @return
	 */
	public List<UserDetails> listUser();

	/**
	 * @return
	 */
	public List<UserVO> listUserVO();

	/**
	 * @param userId
	 * @return
	 */
	public UserDetails getUser(int userId);

	/**
	 * @param userName
	 * @return
	 */
	public UserDetails getMyDetails(String userName);
	
}
