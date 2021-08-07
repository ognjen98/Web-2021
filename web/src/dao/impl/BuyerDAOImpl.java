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

import beans.Buyer;
import beans.User;
import dao.cruddao.BuyerDAO;
import dao.cruddao.CRUDDao;

public class BuyerDAOImpl implements BuyerDAO{

	private HashMap<String, Buyer> buyers = new HashMap<String, Buyer>();
	private String contextPath;
	
	public BuyerDAOImpl() {
		
	}
	
	public BuyerDAOImpl(String contextPath) {
		this.contextPath = contextPath;
		loadBuyers(contextPath);
	}
	
	@Override
	public int count() {
		ArrayList<Buyer> guesList = new ArrayList<Buyer>(findAll());
		 return guesList.size();
	}

	@Override
	public boolean add(Buyer entity) {
		loadBuyers(contextPath);
		if(existsById(entity.getId().toString())) {
			return false;
		}
		int length = buyers.keySet().size();
		if(length == 0) {
			entity.setId(1);
			buyers.put("1", entity);
		}
		else {
			Integer nextId = ++length;
			entity.setId(nextId);
			buyers.put(nextId.toString(), entity);
		}
		save();
		return true;
	}

	@Override
	public boolean update(Buyer entity) {
		loadBuyers(contextPath);
		if(!existsById(entity.getId().toString())) {
			return false;
		}
		buyers.put(entity.getId().toString(), entity);
		save();
		return true;
	}

	@Override
	public boolean delete(Buyer entity) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public boolean deleteById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existsById(String id) {
		loadBuyers(contextPath);
		return buyers.containsKey(id);
	}
	
	public boolean existsByUsername(String username) {
		loadBuyers(contextPath);
		Collection<Buyer> userList =  buyers.values();
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
	public Collection<Buyer> findAll() {
		loadBuyers(contextPath);
		
		Collection<Buyer> guestList = buyers.values();
		System.out.println(guestList);
		return guestList;
	}

	@Override
	public Buyer findById(String id) {
		loadBuyers(contextPath);
		return buyers.get(id);
	}
	
	@Override
	public Buyer findByUsername(String username) {
		loadBuyers(contextPath);
		Collection<Buyer> buyerList = buyers.values();
		for(Buyer b : buyerList) {
			if(b.getUsername().equals(username))
				return b;
		}
		return null;
	}

	@Override
	public boolean save() {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File("D:\\web\\Web-2021\\web\\WebContent\\data\\" + "buyers.json");
	
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, buyers);
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
	public boolean saveAll(Collection<Buyer> entities) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void loadBuyers(String contextPath) {
		BufferedReader in = null;
		File file = new File("D:\\web\\Web-2021\\web\\WebContent\\data\\" + "buyers.json");
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<HashMap<String,Buyer>> typeRef 
        = new TypeReference<HashMap<String,Buyer>>() {};
        
		try {
			in = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			buyers = mapper.readValue(in, typeRef);
			
			
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

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}
}
