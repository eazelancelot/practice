package com.example.practice.service.ifs;

import java.io.IOException;
import java.util.List;

import com.example.practice.entity.PersonInfo;

public interface PersonInfoService {

	public String create(PersonInfo info);

	public void create(List<PersonInfo> infoList);

	public void findAll();

	public void findById(String id);

	public void findByAgeGreaterThan(int age);

	public void findByAgeLessThanOrGreaterThan(int age1, int age2);

	public void findByAgeLessThanEqualOrderByAgeASC(int age);

	public void findByCityContaining(String str);

	public int updateCityById3(String id, String city) throws IOException;

	public int transTest1(String id, String city);

	public int transTest2(String id, String city);
	
	public int transTest3(String id, String city);
	
	public int transTest4(String id, String city);
	
	public int transTest5(String id, String city);
	
	public int transTest6(String id, String city);
	
	public int transTest7(String id, String city);

}
