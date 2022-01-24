package dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Manager;
import beans.User;
import dao.cruddao.ManagerDAO;

public class ManagerDAOImpl implements ManagerDAO{

	private Map<String, Manager> managers = new HashMap<String, Manager>();
	private String contextPath;
	
	public ManagerDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public ManagerDAOImpl(String contextPath) {
		this.contextPath = contextPath;
		loadManagers(contextPath);
	}
	
	@Override
	public int count() {
		 ArrayList<Manager> userList = new ArrayList<Manager>(findAll());
		 return userList.size();
	}

	@Override
	public boolean add(Manager entity) {
		loadManagers(contextPath);
		if(existsById(entity.getUsername())) {
			return false;
		}
		managers.put(entity.getUsername(), entity);
		
		save();
		return true;
	}

	@Override
	public boolean update(Manager entity) {
		loadManagers(contextPath);
		if(!existsById(entity.getUsername())) {
			return false;
		}
		managers.put(entity.getUsername(), entity);
		save();
		return true;
	}

	@Override
	public boolean delete(Manager entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAll() {
		managers.clear();

	}

	@Override
	public boolean deleteById(String id) {
		managers.remove(id);
		save();
		return true;
	}

	@Override
	public boolean existsById(String id) {
		loadManagers(contextPath);
		return managers.containsKey(id);
	}
	
	public boolean existsByUsername(String username) {
		loadManagers(contextPath);
		Collection<Manager> userList =  managers.values();
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
	public Collection<Manager> findAll() {
		loadManagers(contextPath);
		Collection<Manager> userList = managers.values();
		return userList;
	}

	@Override
	public Manager findById(String id) {
		loadManagers(contextPath);
		return managers.get(id);
	}
	
	@Override
	public Manager findByUsername(String username) {
		loadManagers(contextPath);
		Collection<Manager> userList = managers.values();
		for(Manager u : userList) {
			if(u.getUsername().equals(username))
				return u;
		}
		return null;
	}

	@Override
	public boolean save() {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("D:\\web\\Web-2021\\web\\WebContent\\data\\" + "managers.json");
		//JSONObject json = new JSONObject(users);
		//JSONObject employeeDetails = new JSONObject();
		
//		JSONArray employeeList = (JSONArray) users
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, managers);
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
	public boolean saveAll(Collection<Manager> entities) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void loadManagers(String contextPath) {
		BufferedReader in = null;
		File file = new File("D:\\web\\Web-2021\\web\\WebContent\\data\\" + "managers.json");
		//JSONParser jsonParser = new JSONParser();
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String,Manager>> typeRef 
        = new TypeReference<HashMap<String,Manager>>() {};

		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			managers = mapper.readValue(in, typeRef);
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
