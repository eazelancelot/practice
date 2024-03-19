package com.example.practice.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.practice.entity.PersonInfo;
import com.example.practice.repository.PersonInfoDao;
import com.example.practice.service.ifs.PersonInfoService;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {

	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public void create(List<PersonInfo> infoList) {
		if (infoList == null) {
			System.out.println("infoList is null");
			return;
		}
		for (PersonInfo item : infoList) {
			if (item == null || !StringUtils.hasText(item.getId()) || item.getAge() < 0) {
				System.out.println("PersonInfo 錯誤");
				continue;
			}
			if (personInfoDao.existsById(item.getId())) {
				System.out.println(item.getId() + "已存在");
				continue;
			}
			personInfoDao.save(item);
			System.out.println(item.getId() + item.getName() + item.getAge() + item.getCity());
		}

	}

	@Override
	public void findAll() {
		List<PersonInfo> list = personInfoDao.findAll();
		checkAndPrintList(list);
	}

	@Override
	public void findById(String id) {
		if (!StringUtils.hasText(id)) {
			System.out.println("id 錯誤");
			return;
		}
		Optional<PersonInfo> op = personInfoDao.findById(id);
		if (op.isEmpty()) {
			System.out.println("id 找不到");
			return;
		}
		PersonInfo info = op.get();
		System.out.println(info.getId() + info.getName() + info.getAge() + info.getCity());
	}

	@Override
	public void findByAgeGreaterThan(int age) {
		List<PersonInfo> list = personInfoDao.findByAgeGreaterThan(age);
		checkAndPrintList(list);
	}

	@Override
	public void findByAgeLessThanOrGreaterThan(int age1, int age2) {
		List<PersonInfo> list = personInfoDao.findByAgeLessThanOrAgeGreaterThan(age1, age2);
		checkAndPrintList(list);
	}

	@Override
	public void findByAgeLessThanEqualOrderByAgeASC(int age) {
		List<PersonInfo> list = personInfoDao.findByAgeLessThanEqualOrderByAge(age);
		checkAndPrintList(list);
	}

	private void checkAndPrintList(List<PersonInfo> list) {
		if (list.isEmpty()) {
			System.out.println("找不到資料");
			return;
		}
		for (PersonInfo item : list) {
//			System.out.println(item.getId() + item.getName() + item.getAge() + item.getCity());
			System.out.printf("%s %s %d %s \n", item.getId(), item.getName(), item.getAge(), item.getCity());
		}
	}

	@Override
	public void findByCityContaining(String str) {
		List<PersonInfo> list = personInfoDao.findByCityContaining(str);
		checkAndPrintList(list);
	}

	@Override
	public String create(PersonInfo info) {
		System.out.println("===================");
		return "========================";
	}

	// @Transactional: 預設資料回朔是發生在 RuntimException 和其子類別的xxxException
	// 若是想要連同 RuntimException 的兄弟類別發生錯誤時也可以資料回朔，就把資料回朔的層級拉到父類別(Exception)
	// rollbackOn = Exception.class: 當發生 Exception 時，資料會回朔(資料不會更新)
	// 程式碼中的 IOException 和 RuntimeException 是兄弟類別
	// 可以測試拿掉 rollbackOn = Exception.class 看看資料是否會回朔
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateCityById3(String id, String city) throws IOException {
		personInfoDao.updateCityById2(id, city);
		throw new IOException("================");
	}

	// 前提是發生 RuntimeException
	// 情境1: Dao 有加 @Transactional，但 service 的方法沒有加 @Transactional
	// 結果: Transactional 無效；因為 non-transactional 方法呼叫 transactional 方法，transactional 無效
	// non-transactional 方法是指方法上沒有加 @Transactional
	@Override
	public int transTest1(String id, String city) {
		personInfoDao.updateCityById2(id, city);
		throw new RuntimeException("================");
	}

	// 前提是發生 RuntimeException
	// 情境2: Dao和service的方法都有加@Transactional
	// 結果: Transactional 有效；因為 transactional 方法呼叫 transactional 方法
	@Transactional
	@Override
	public int transTest2(String id, String city) {
		personInfoDao.updateCityById2(id, city);
		throw new RuntimeException("================");
	}

	// 前提是發生 RuntimeException
	// 情境3: Dao 和 publicMethod 方法都有加 @Transactional，service 方法沒有加 @Transactional，
	// Exception 發生在 publicMethod
	// 結果: Transactional 無效，等同於情境1
	@Override
	public int transTest3(String id, String city) {
		return publicMethod(id, city);
	}

	// 情境4: Dao 和 privateMethod 方法都有加 @Transactional，service 方法沒有加 @Transactional，
	// Exception 發生在 privateMethod
	// 結果: Transactional 無效，等同於情境3
	@Override
	public int transTest4(String id, String city) {
		return privateMethod(id, city);
	}

	// 情境5: Dao、privateMethod 和 service 方法都有加 @Transactional，
	// Exception 發生在 privateMethod
	// 結果: Transactional 有效
	@Transactional
	@Override
	public int transTest5(String id, String city) {
		return privateMethod(id, city);
	}
	
	// 情境6: Dao 和 service 方法都有加 @Transactional，privateMethod1沒有加，
	// Exception 發生在 privateMethod
	// 結果: Transactional 有效，同情境2
	@Transactional
	@Override
	public int transTest6(String id, String city) {
		return privateMethod1(id, city);
	}
	
	// 情境7: read-only(唯讀，修改會報錯)
	// readOnly 只存在於 org.springframework.transaction.annotation.Transactional 此 library 中
	@Transactional(readOnly = true)
	@Override
	public int transTest7(String id, String city) {
		return personInfoDao.updateCityById2(id, city);
	}

	// private 方法有加 @Transactional
	@Transactional
	private int privateMethod(String id, String city) {
		int a = personInfoDao.updateCityById2(id, city);
		throw new RuntimeException("================");
	}
	
	// private 方法沒加 @Transactional
	private int privateMethod1(String id, String city) {
		int a = personInfoDao.updateCityById2(id, city);
		throw new RuntimeException("================");
	}

	// public 方法有加 @Transactional
	@Transactional
	public int publicMethod(String id, String city) {
		int a = personInfoDao.updateCityById2(id, city);
		throw new RuntimeException("================");
	}

}
