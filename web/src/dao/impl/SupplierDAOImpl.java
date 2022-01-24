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
import beans.Supplier;
import beans.User;
import dao.cruddao.SupplierDAO;

public class SupplierDAOImpl implements SupplierDAO{

	private Map<String, Supplier> suppliers = new HashMap<String, Supplier>();

	private String contextPath;
	
	public SupplierDAOImpl() {
		// TODO Auto-generated constructor stub
	}
	
	public SupplierDAOImpl(String contextPath) {
		this.contextPath = contextPath;
		loadSuppliers(contextPath);
	}
	
	@Override
	public int count() {
		 ArrayList<Supplier> userList = new ArrayList<Supplier>(findAll());
		 return userList.size();
	}

	@Override
	public boolean add(Supplier entity) {
		loadSuppliers(contextPath);
		if(existsById(entity.getUsername())) {
			return false;
		}
		suppliers.put(entity.getUsername(), entity);
		
		save();
		return true;
	}

	@Override
	public boolean update(Supplier entity) {
		loadSuppliers(contextPath);
		if(!existsById(entity.getUsername())) {
			return false;
		}
		suppliers.put(entity.getUsername(), entity);
		save();
		return true;
	}

	@Override
	public boolean delete(Supplier entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteAll() {
		suppliers.clear();

	}

	@Override
	public boolean deleteById(String id) {
		suppliers.remove(id);
		save();
		return true;
	}

	@Override
	public boolean existsById(String id) {
		loadSuppliers(contextPath);
		return suppliers.containsKey(id);
	}
	
	public boolean existsByUsername(String username) {
		loadSuppliers(contextPath);
		Collection<Supplier> userList =  suppliers.values();
		for(Supplier u : userList) {
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
	public Collection<Supplier> findAll() {
		loadSuppliers(contextPath);
		Collection<Supplier> userList = suppliers.values();
		return userList;
	}

	@Override
	public Supplier findById(String id) {
		loadSuppliers(contextPath);
		return suppliers.get(id);
	}
	
	@Override
	public Supplier findByUsername(String username) {
		loadSuppliers(contextPath);
		Collection<Supplier> userList = suppliers.values();
		for(Supplier u : userList) {
			if(u.getUsername().equals(username))
				return u;
		}
		return null;
	}

	@Override
	public boolean save() {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("D:\\web\\Web-2021\\web\\WebContent\\data\\" + "suppliers.json");
		//JSONObject json = new JSONObject(users);
		//JSONObject employeeDetails = new JSONObject();
		
//		JSONArray employeeList = (JSONArray) users
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, suppliers);
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
	public boolean saveAll(Collection<Supplier> entities) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void loadSuppliers(String contextPath) {
		BufferedReader in = null;
		File file = new File("D:\\web\\Web-2021\\web\\WebContent\\data\\" + "suppliers.json");
		//JSONParser jsonParser = new JSONParser();
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String,Supplier>> typeRef 
        = new TypeReference<HashMap<String,Supplier>>() {};

		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			suppliers = mapper.readValue(in, typeRef);
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
