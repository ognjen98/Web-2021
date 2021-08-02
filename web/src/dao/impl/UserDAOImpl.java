package dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.User;
import dao.cruddao.UserDAO;

public class UserDAOImpl implements UserDAO{

	private HashMap<String, User> users;
	private String contextPath;
	
	public UserDAOImpl() {
		
	}
	
	public UserDAOImpl(String contextPath) {
		this.contextPath = contextPath;
		loadUsers(contextPath);
	}
	
	@Override
	public int count() {
		 ArrayList<User> userList = new ArrayList<User>(findAll());
		 return userList.size();
	}

	@Override
	public boolean add(User entity) {
		loadUsers(contextPath);
		if(existsById(entity.getUsername())) {
			return false;
		}
		users.put(entity.getUsername(), entity);
		save();
		return true;
	}

	@Override
	public boolean update(User entity) {
		loadUsers(contextPath);
		if(!existsById(entity.getUsername())) {
			return false;
		}
		users.put(entity.getUsername(), entity);
		save();
		return true;
	}

	@Override
	public boolean delete(User entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean deleteById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsById(String id) {
		loadUsers(contextPath);
		return users.containsKey(id);
	}

	@Override
	public boolean isDeleted(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<User> findAll() {
		loadUsers(contextPath);
		Collection<User> userList = users.values();
		return userList;
	}

	@Override
	public User findById(String id) {
		loadUsers(contextPath);
		return users.get(id);
	}

	@Override
	public boolean save() {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(contextPath + File.separator + "data" + File.separator + "users.json");
		
		try {
			mapper.writeValue(file, users);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean saveAll(Collection<User> entities) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void loadUsers(String contextPath) {
		BufferedReader in = null;
		File file = new File(contextPath + File.separator + "data" + File.separator + "users.json");
	
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String,User>> typeRef 
        = new TypeReference<HashMap<String,User>>() {};

		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			users = mapper.readValue(in, typeRef);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
}
