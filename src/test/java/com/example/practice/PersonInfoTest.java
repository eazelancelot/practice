package com.example.practice;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import com.example.practice.entity.PersonInfo;
import com.example.practice.repository.PersonInfoDao;
import com.example.practice.service.ifs.PersonInfoService;
import com.example.practice.vo.JoinVo;

@SpringBootTest
public class PersonInfoTest {

	@Autowired
	private PersonInfoService personInfoService;

	@Autowired
	private PersonInfoDao personInfoDao;

	@Test
	public void createTest() {
//		PersonInfo p1 = new PersonInfo("Q_Q", "", -5, "Tainan");
//		List<PersonInfo> list = new ArrayList<>();
//		list.add(p1);
//		personInfoService.create(list);
	}

	@Test
	public void findByAgeTest() {
		personInfoService.findByAgeGreaterThan(16);
	}

	@Test
	public void findByageOrderByTest() {
		personInfoService.findByAgeLessThanEqualOrderByAgeASC(30);
	}

	@Test
	public void findByContainingTest() {
		personInfoService.findByCityContaining("nin");
	}

	@Test
	public void test() {
		String str1 = null;
		String str2 = "";
		String str3 = "  ";
		String str4 = "ABC";
		System.out.println(str1 + StringUtils.hasText(str1));
		System.out.println(str2 + StringUtils.hasText(str2));
		System.out.println(str3 + StringUtils.hasText(str3));
		System.out.println(str4 + StringUtils.hasText(str4));
		System.out.println("==================================");
		System.out.println(str1 + !StringUtils.hasText(str1));
		System.out.println(str2 + !StringUtils.hasText(str2));
		System.out.println(str3 + !StringUtils.hasText(str3));
		System.out.println(str4 + !StringUtils.hasText(str4));
	}
	// =====================================

	@Test
	public void insertTest() {
		int a = personInfoDao.insert("D03", "DDD");
		System.out.println("======>>>>>>" + a);
	}

	@Test
	public void insertTest1() {
		int a = personInfoDao.insert("D04", "D_D", "KH");
		System.out.println("======>>>>>>" + a);
	}

	@Test
	public void updateTest() {
		int a = personInfoDao.updateCityById("D04", "Taipei");
		System.out.println("======>>>>>>" + a);
		a = personInfoDao.updateCityById("D03", "Taipei");
		System.out.println("======>>>>>>" + a);
	}

	@Test
	public void updateTest1() {
		int a = personInfoDao.updateCityByAgeLessThanEqual(0, "Taoyuan");
		System.out.println("======>>>>>>" + a);
	}

	@Test
	public void updateTest2() {
		int a = personInfoDao.updateCityById2("D03", "New Taipei");
		System.out.println("======>>>>>>" + a);
	}

	@Test
	public void updateTest3() {
		try {
			int a = personInfoService.transTest1("D02", "New Taipei");
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateTest4() {
		int a = personInfoDao.updateCityById2("D02", "New Taipei");
		throw new RuntimeException("==========================");
	}

	// 情境1: Dao 有加 @Transactional，但 service 的方法沒有加 @Transactional
	// 結果: Transactional 無效
	@Test
	public void transTest1() {
		personInfoService.transTest1("D02", "New Taipei");
	}

	// 情境2: Dao和service的方法都有加@Transactional
	// 結果: Transactional 有效
	@Test
	public void transTest2() {
		personInfoService.transTest2("D02", "New Taipei");
	}

	/// 情境3: Dao 和 publicMethod 方法都有加 @Transactional，service 方法沒有加 @Transactional，
	// Exception 發生在 publicMethod
	// 結果: Transactional 無效
	@Test
	public void transTest3() {
		personInfoService.transTest3("D02", "New Taipei");
	}

	// 情境4: Dao 和 privateMethod 方法都有加 @Transactional，service 方法沒有加 @Transactional，
	// Exception 發生在 privateMethod
	// 結果: Transactional 無效
	@Test
	public void transTest4() {
		personInfoService.transTest4("D02", "New Taipei");
	}

	// 情境5: Dao、privateMethod 和 service 方法都有加 @Transactional，
	// Exception 發生在 privateMethod
	// 結果: Transactional 有效
	@Test
	public void transTest5() {
		personInfoService.transTest5("D02", "New Taipei");
	}

	// 情境6: Dao 和 service 方法都有加 @Transactional，privateMethod 沒有加
	// Exception 發生在 privateMethod
	// 結果: Transactional 有效
	@Test
	public void transTest6() {
		personInfoService.transTest6("D02", "New Taipei");
	}

	// 情境7: @Test 方法上加上 @Transactional，測試內容是呼叫 @Transactional 無效的 transTest1
	// 結果:
	@Transactional
	@Test
	public void transTest7() {
		personInfoService.transTest1("D02", "New Taipei");
	}
	
	@Test
	public void transTest8() {
		personInfoService.transTest7("D02", "New Taipei");
	}
	
	//============================
	@Test
	public void selectTest() {
		List<PersonInfo> res = personInfoDao.findByCity("KH");
		System.out.println(res.size());
	}
	
	@Test
	public void selectTest1() {
		List<PersonInfo> res = personInfoDao.findByCity1("KH");
		System.out.println(res.size());
	}
	
	@Test
	public void selectTest2() {
		List<PersonInfo> res = personInfoDao.findByCity2("KH");
		System.out.println(res.size());
	}
	
	@Test
	public void selectTest3() {
		List<PersonInfo> res = personInfoDao.findByCity3("KH");
		System.out.println(res.size());
	}
	
	@Test
	public void selectDistinctTest() {
		List<PersonInfo> res = personInfoDao.findDistinctCity();
		System.out.println(res.size());
	}
	
	@Test
	public void selectOrderByTest() {
		List<PersonInfo> res = personInfoDao.findByCityOrderByAge("KH");
		System.out.println(res.size());
	}
	
	@Test
	public void selectOrderByLimitTest() {
		List<PersonInfo> res = personInfoDao.findByCityOrderByAgeLimit("KH", 2);
		System.out.println(res.size());
	}
	
	@Test
	public void selectOrderByLimitTest2() {
		List<PersonInfo> res = personInfoDao.findByCityOrderByAgeLimit2("KH", 1, 2);
		System.out.println(res.size());
	}
	
	@Test
	public void selectBetweenTest() {
		List<PersonInfo> res = personInfoDao.findByAgeBetween(80, 20);
		System.out.println(res.size());
	}
	
	@Test
	public void selectLikeTest() {
		List<PersonInfo> res = personInfoDao.findByCityLike("Tai");
		System.out.println(res.size());
	}
	
	@Test
	public void selectLikeIgnoreCaseTest() {
		List<PersonInfo> res = personInfoDao.findByCityContainingIgnoreCase("tai");
		System.out.println(res.size());
	}
	
	@Test
	public void selectRegexpTest() {
		List<PersonInfo> res = personInfoDao.findByCityRegexp("Tai", "Tao");
		System.out.println(res.size());
	}
	
	@Test
	public void joinTest() {
		List<JoinVo> res = personInfoDao.joinById();
		System.out.println(res.size());
	}

}
