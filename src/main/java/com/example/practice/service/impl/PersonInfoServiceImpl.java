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
				System.out.println("PersonInfo ���~");
				continue;
			}
			if (personInfoDao.existsById(item.getId())) {
				System.out.println(item.getId() + "�w�s�b");
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
			System.out.println("id ���~");
			return;
		}
		Optional<PersonInfo> op = personInfoDao.findById(id);
		if (op.isEmpty()) {
			System.out.println("id �䤣��");
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
			System.out.println("�䤣����");
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

	// @Transactional: �w�]��Ʀ^�ҬO�o�ͦb RuntimException �M��l���O��xxxException
	// �Y�O�Q�n�s�P RuntimException ���S�����O�o�Ϳ��~�ɤ]�i�H��Ʀ^�ҡA�N���Ʀ^�Ҫ��h�ũԨ�����O(Exception)
	// rollbackOn = Exception.class: ��o�� Exception �ɡA��Ʒ|�^��(��Ƥ��|��s)
	// �{���X���� IOException �M RuntimeException �O�S�����O
	// �i�H���ծ��� rollbackOn = Exception.class �ݬݸ�ƬO�_�|�^��
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int updateCityById3(String id, String city) throws IOException {
		personInfoDao.updateCityById2(id, city);
		throw new IOException("================");
	}

	// �e���O�o�� RuntimeException
	// ����1: Dao ���[ @Transactional�A�� service ����k�S���[ @Transactional
	// ���G: Transactional �L�ġF�]�� non-transactional ��k�I�s transactional ��k�Atransactional �L��
	// non-transactional ��k�O����k�W�S���[ @Transactional
	@Override
	public int transTest1(String id, String city) {
		personInfoDao.updateCityById2(id, city);
		throw new RuntimeException("================");
	}

	// �e���O�o�� RuntimeException
	// ����2: Dao�Mservice����k�����[@Transactional
	// ���G: Transactional ���ġF�]�� transactional ��k�I�s transactional ��k
	@Transactional
	@Override
	public int transTest2(String id, String city) {
		personInfoDao.updateCityById2(id, city);
		throw new RuntimeException("================");
	}

	// �e���O�o�� RuntimeException
	// ����3: Dao �M publicMethod ��k�����[ @Transactional�Aservice ��k�S���[ @Transactional�A
	// Exception �o�ͦb publicMethod
	// ���G: Transactional �L�ġA���P�󱡹�1
	@Override
	public int transTest3(String id, String city) {
		return publicMethod(id, city);
	}

	// ����4: Dao �M privateMethod ��k�����[ @Transactional�Aservice ��k�S���[ @Transactional�A
	// Exception �o�ͦb privateMethod
	// ���G: Transactional �L�ġA���P�󱡹�3
	@Override
	public int transTest4(String id, String city) {
		return privateMethod(id, city);
	}

	// ����5: Dao�BprivateMethod �M service ��k�����[ @Transactional�A
	// Exception �o�ͦb privateMethod
	// ���G: Transactional ����
	@Transactional
	@Override
	public int transTest5(String id, String city) {
		return privateMethod(id, city);
	}
	
	// ����6: Dao �M service ��k�����[ @Transactional�AprivateMethod1�S���[�A
	// Exception �o�ͦb privateMethod
	// ���G: Transactional ���ġA�P����2
	@Transactional
	@Override
	public int transTest6(String id, String city) {
		return privateMethod1(id, city);
	}
	
	// ����7: read-only(��Ū�A�ק�|����)
	// readOnly �u�s�b�� org.springframework.transaction.annotation.Transactional �� library ��
	@Transactional(readOnly = true)
	@Override
	public int transTest7(String id, String city) {
		return personInfoDao.updateCityById2(id, city);
	}

	// private ��k���[ @Transactional
	@Transactional
	private int privateMethod(String id, String city) {
		int a = personInfoDao.updateCityById2(id, city);
		throw new RuntimeException("================");
	}
	
	// private ��k�S�[ @Transactional
	private int privateMethod1(String id, String city) {
		int a = personInfoDao.updateCityById2(id, city);
		throw new RuntimeException("================");
	}

	// public ��k���[ @Transactional
	@Transactional
	public int publicMethod(String id, String city) {
		int a = personInfoDao.updateCityById2(id, city);
		throw new RuntimeException("================");
	}

}
