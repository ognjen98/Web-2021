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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import beans.Basket;
import beans.Order;
import dao.cruddao.BasketDAO;

public class BasketDAOImpl implements BasketDAO{

	private Map<String, Basket> restaurants = new HashMap<String, Basket>();
	private String contextPath;
	
	public BasketDAOImpl() {
		
	}
	
	public BasketDAOImpl(String contextPath) {
		this.contextPath = contextPath;
		loadRestaurants(contextPath);
	}
	
	@Override
	public int count() {
		 ArrayList<Basket> userList = new ArrayList<Basket>(findAll());
		 return userList.size();
	}

	@Override
	public boolean add(Basket entity) {
		loadRestaurants(contextPath);
		
		Integer currId = restaurants.keySet().size();
		if(currId == 0) {
			entity.setId(1);
			restaurants.put(entity.getId().toString(), entity);
		}
		else {
			Integer nextId = ++currId;
			entity.setId(nextId);
			restaurants.put(entity.getId().toString(), entity);
			
		}
	
		save();
		return true;
	}

	@Override
	public boolean update(Basket entity) {
		loadRestaurants(contextPath);
		if(!existsById(entity.getId().toString())) {
			return false;
		}
		restaurants.put(entity.getId().toString(), entity);
		save();
		return true;
	}

	@Override
	public boolean delete(Basket entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAll() {
		restaurants.clear();

	}

	@Override
	public boolean deleteById(String id) {
		restaurants.remove(id);
		save();
		return true;
	}

	@Override
	public boolean existsById(String id) {
		loadRestaurants(contextPath);
		return restaurants.containsKey(id);
	}
	

	@Override
	public boolean isDeleted(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Basket> findAll() {
		loadRestaurants(contextPath);
		Collection<Basket> userList = restaurants.values();
		return userList;
	}

	@Override
	public Basket findById(String id) {
		loadRestaurants(contextPath);
		return restaurants.get(id);
	}
	
	

	@Override
	public boolean save() {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("D:\\web\\Web-2021\\web\\WebContent\\data\\" + "baskets.json");
		//JSONObject json = new JSONObject(users);
		//JSONObject employeeDetails = new JSONObject();
		
//		JSONArray employeeList = (JSONArray) users
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, restaurants);
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
	public boolean saveAll(Collection<Basket> entities) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void loadRestaurants(String contextPath) {
		BufferedReader in = null;
		File file = new File("D:\\web\\Web-2021\\web\\WebContent\\data\\" + "baskets.json");
		//JSONParser jsonParser = new JSONParser();
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String,Basket>> typeRef 
        = new TypeReference<HashMap<String,Basket>>() {};

		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			restaurants = mapper.readValue(in, typeRef);
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
