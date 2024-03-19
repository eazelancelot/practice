package com.example.practice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.practice.entity.PersonInfo;
import com.example.practice.vo.JoinVo;

@Repository
public interface PersonInfoDao extends JpaRepository<PersonInfo, String> {

	public List<PersonInfo> findByAgeGreaterThan(int age);

	public List<PersonInfo> findByAgeLessThanOrAgeGreaterThan(int age1, int age2);

	public List<PersonInfo> findByAgeLessThanEqualOrderByAge(int age);

	// sql ���Ƨǹw�]�O Asc�A�ҥH����k�����浲�G�|�W�����@�Ҥ@��
	public List<PersonInfo> findByAgeLessThanEqualOrderByAgeAsc(int age);

	// JPA ����ƥi�H�� First �άO Top�A�᭱������������ƼƦr
	public List<PersonInfo> findFirst3ByAgeBetweenOrderByAgeDesc(int age1, int age2);

	public List<PersonInfo> findTop3ByAgeBetweenOrderByAgeDesc(int age1, int age2);

	public List<PersonInfo> findByCityContaining(String str);

	public List<PersonInfo> findByAgeGreaterThanAndCityContainingOrderByAgeDesc(int age, String str);

	// ==============================================
	// insert: �u��O nativeQuery = true
	@Modifying
	@Transactional
	@Query(value = "insert into person_info (id, name) values (?1, ?2)", nativeQuery = true)
	public int insert(String id, String name);

	@Modifying
	@Transactional
	@Query(value = "insert into person_info (id, name, city) values (:inputId, :inputName, :inputCity)", nativeQuery = true)
	public int insert( //
			@Param("inputId") String id, //
			@Param("inputName") String name, //
			@Param("inputCity") String city);

	// ===========================
	// update
	// �n�O�o�[�W where ����A���M�|��s""�Ҧ�""�����
	@Modifying
	@Transactional
	@Query(value = "update person_info set city = ?2 where id = ?1", nativeQuery = true)
	public int updateCityById(String id, String city);

	// nativeQuery = true�A�y�k���ާ@����H�O table name �M ���W��
	@Modifying
	@Transactional
	@Query(value = "update person_info set city = ?2 where age <= ?1", nativeQuery = true)
	public int updateCityByAgeLessThanEqual(int age, String city);

	// @Query �����y�y���[�W nativeQuery = true�A��� nativeQuery = false(�w�])
	// nativeQuery = false�A�y�k���ާ@����H�O Entity name �M �ݩ�(�ܼ�)�W��
	// �y�k���� city �M id �O�ݩ�(�ܼ�)�W�١A�u�O��n�M���W�٦P�W
	@Modifying
	@Transactional
	@Query(value = "update PersonInfo set city = ?2 where id = ?1")
	public int updateCityById2(String id, String city);

	// clearAutomatically = true: ��s��ƫ�|�M���Ȧs���
	// �Ω�: ��s��ƫ�A�A�qDB���o����ƨ̵M�O�¸�ơA�N�i�H�ϥ� clearAutomatically = true
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update PersonInfo set city = ?2 where id = ?1")
	public int updateCityById3(String id, String city);
	// ===========================
	// select

	@Query(value = "select * from person_info where city = ?1", nativeQuery = true)
	public List<PersonInfo> findByCity(String city);

	// nativeQuery = true�A�u�༴���Ҧ������
	@Query(value = "select id, name, age, city from person_info where city = ?1", nativeQuery = true)
	public List<PersonInfo> findByCity1(String city);

	// nativeQuery = false�A�O�ާ@Entity�M�ݩʡA��������쥲���n���������غc��k
	// �]���y�k�����ϥΨ� new PersonInfo(id, name, age)�A�ҥH�N�n���������غc��k
	@Query(value = "select new PersonInfo(id, name, age) from PersonInfo where city = ?1")
	public List<PersonInfo> findByCity2(String city);

	// nativeQuery = false�A�ϥΧO�W(�W�٦۩w�q)�A��ܼ������O��� Entity
	// �y�k���S�ϥΨ� new PersonInfo(id, name, age, city)�A
	// �ҥH�Y�ϧ�a�������Ѽƪ��غc��k���ѱ��]���|����
	@Query(value = "select p from PersonInfo as p where city = ?1")
	public List<PersonInfo> findByCity3(String city);

	// select distinct
	@Query(value = "select distinct new PersonInfo(city) from PersonInfo")
	public List<PersonInfo> findDistinctCity();

	// order by: asc default
//	@Query(value = "select * from person_info where city = ?1 order by age", nativeQuery = true)
	@Query("select p from PersonInfo as p where city = ?1 order by age")
	public List<PersonInfo> findByCityOrderByAge(String city);

	// order by + limit: limit �u��b nativeQuery = true ����
	@Query(value = "select * from person_info where city = ?1 order by age limit ?2", nativeQuery = true)
	public List<PersonInfo> findByCityOrderByAgeLimit(String city, int limit);

	// limit + �_�l index
	// limit 5(����5��) ���P�� limit 0, 5(����5���A�q index = 0 �}�l��)
	// limit 0, 5 --> 0: ��� start_index, 5��ܭ���^�ǵ���
	@Query(value = "select * from person_info where city = ?1 order by age limit ?2, ?3", nativeQuery = true)
	public List<PersonInfo> findByCityOrderByAgeLimit2(String city, int startIndex, int limit);

	// between
	@Query(value = "select p from PersonInfo as p where age between ?1 and ?2")
//	@Query(value = "select * from person_info where age between ?1 and ?2", nativeQuery = true)
	public List<PersonInfo> findByAgeBetween(int age1, int age2);

	// in: @Query �����y�k�L�k�F��ʺA�ѼơA�ҥH��ĳ�����ϥ� JPA �� in
	@Query(value = "select * from person_info where city in(?1, ?2, ?3)", nativeQuery = true)
	public List<PersonInfo> findByCityIn(String city1, String city2, String city3);

	// like
//	@Query(value = "select * from person_info where city like %?1%", nativeQuery = true)
	@Query(value = "select p from PersonInfo as p where city like %?1%")
	public List<PersonInfo> findByCityLike(String str);

	// like + ignore case(�����j�p�g):
	// LOWER/lower: ���������ন���p�g���
	// UPPER/upper: ���������ন���j�g���
	// concat: ��r��M�Ȧꦨ�r��ASQL�y�k���r��O�γ�޸�
//	@Query(value = "select * from person_info where Lower(city) like Lower(concat('%',?1,'%'))", nativeQuery = true)
	@Query(value = "select p from PersonInfo as p where lower(city) like lower(concat('%',?1,'%'))")
	public List<PersonInfo> findByCityContainingIgnoreCase(String str);

	// regexp: �u��b nativeQuery = true ����
	@Query(value = "select * from person_info where city regexp ?1", nativeQuery = true)
	public List<PersonInfo> findByCityRegexp(String str1);

	// regexp:
	@Query(value = "select * from person_info where city regexp concat(?1, '|', ?2)", nativeQuery = true)
	public List<PersonInfo> findByCityRegexp(String str1, String str2);

	// join
	// 1. ��ĳ�ϥ� nativeQuery = false�A�]���ϥ� nativeQuery = true �ɡAList ������ƫ��A�O Object(�L getter/setter)
	// 2. �]�� JoinVo �S���Q Spring Boot �U��(class �W�S���[���� Annotation)�A�ҥH�����n�i�����|(package)
	// 3. JoinVo �����Ҧ����A�n�ϥ� ��.���(�O�W.���)
	@Query(value = "select new com.example.practice.vo.JoinVo(p.id, p.name, p.age, p.city, a.amount) "
			+ " from PersonInfo as p join Atm as a on p.id = a.account")
	public List<JoinVo> joinById();

}
