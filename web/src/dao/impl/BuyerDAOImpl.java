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
import dao.cruddao.CRUDDao;

public class BuyerDAOImpl implements CRUDDao<Buyer, String>{

	private HashMap<String, Buyer> buyers;
	private String contextPath;
	
	public BuyerDAOImpl() {
		
	}
	
	public BuyerDAOImpl(String contextPath) {
		this.contextPath = contextPath;
		loadGuests(contextPath);
	}
	@Override
	public int count() {
		ArrayList<Buyer> guesList = new ArrayList<Buyer>(findAll());
		 return guesList.size();
	}

	@Override
	public boolean add(Buyer entity) {
		if(existsById(entity.getUsername())) {
			return false;
		}
		buyers.put(entity.getUsername(), entity);
		save();
		return true;
	}

	@Override
	public boolean update(Buyer entity) {
		if(!existsById(entity.getUsername())) {
			return false;
		}
		buyers.put(entity.getUsername(), entity);
		save();
		return true;
	}

	@Override
	public boolean delete(Buyer entity) {
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
		return buyers.containsKey(id);
	}

	@Override
	public boolean isDeleted(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Buyer> findAll() {
		loadGuests(contextPath);;
		Collection<Buyer> guestList = buyers.values();
		return guestList;
	}

	@Override
	public Buyer findById(String id) {
		return buyers.get(id);
	}

	@Override
	public boolean save() {
		ObjectMapper mapper = new ObjectMapper();
		File file = new File(contextPath + File.separator + "data" + File.separator + "buyers.json");
	
		try {
			mapper.writeValue(file, buyers);
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
	
	private void loadGuests(String contextPath) {
		BufferedReader in = null;
		File file = new File(contextPath + File.separator + "data" + File.separator + "buyers.json");
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
}
