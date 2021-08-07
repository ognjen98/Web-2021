package dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import beans.User;
import dao.cruddao.UserDAO;


public class UserDAOImpl implements UserDAO{

//	@JsonSerialize(keyUsing = UserSerializer.class) 
	private HashMap<String, User> users = new HashMap<String, User>();
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
		if(existsById(entity.getId().toString())) {
			return false;
		}
		int length = users.keySet().size();
		if(length == 0) {
			entity.setId(1);
			users.put("1", entity);
		}
		else {
			Integer nextId = ++length;
			entity.setId(nextId);
			users.put(nextId.toString(), entity);
		}
		save();
		return true;
	}

	@Override
	public boolean update(User entity) {
		loadUsers(contextPath);
		if(!existsById(entity.getId().toString())) {
			return false;
		}
		users.put(entity.getId().toString(), entity);
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
		users.clear();

	}

	@Override
	public boolean deleteById(String id) {
		users.remove(id);
		save();
		return true;
	}

	@Override
	public boolean existsById(String id) {
		loadUsers(contextPath);
		return users.containsKey(id);
	}
	
	public boolean existsByUsername(String username) {
		loadUsers(contextPath);
		Collection<User> userList =  users.values();
		for(User u : userList) {
			if(u.getUsername().equals(username))
				return true;
		}
		
		return false;
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
	public User findByUsername(String username) {
		loadUsers(contextPath);
		Collection<User> userList = users.values();
		for(User u : userList) {
			if(u.getUsername().equals(username))
				return u;
		}
		return null;
	}

	@Override
	public boolean save() {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("D:\\web\\Web-2021\\web\\WebContent\\data\\" + "users.json");
		//JSONObject json = new JSONObject(users);
		//JSONObject employeeDetails = new JSONObject();
		
//		JSONArray employeeList = (JSONArray) users
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
//			f.write(result);
//			f.flush();
//			System.out.println(result);
			//System.out.println(file);
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
		File file = new File("D:\\web\\Web-2021\\web\\WebContent\\data\\" + "users.json");
		//JSONParser jsonParser = new JSONParser();
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
//			for(String value : users.keySet()) {
//				System.out.println("166-"+value);
//			}
			//System.out.println("168-"+ users.get("cafilius2"));
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
