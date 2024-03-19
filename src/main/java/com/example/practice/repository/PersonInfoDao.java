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

	// sql 的排序預設是 Asc，所以此方法的執行結果會上面的一模一樣
	public List<PersonInfo> findByAgeLessThanEqualOrderByAgeAsc(int age);

	// JPA 限制筆數可以用 First 或是 Top，後面直接接限制的筆數數字
	public List<PersonInfo> findFirst3ByAgeBetweenOrderByAgeDesc(int age1, int age2);

	public List<PersonInfo> findTop3ByAgeBetweenOrderByAgeDesc(int age1, int age2);

	public List<PersonInfo> findByCityContaining(String str);

	public List<PersonInfo> findByAgeGreaterThanAndCityContainingOrderByAgeDesc(int age, String str);

	// ==============================================
	// insert: 只能是 nativeQuery = true
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
	// 要記得加上 where 條件，不然會更新""所有""的資料
	@Modifying
	@Transactional
	@Query(value = "update person_info set city = ?2 where id = ?1", nativeQuery = true)
	public int updateCityById(String id, String city);

	// nativeQuery = true，語法中操作的對象是 table name 和 欄位名稱
	@Modifying
	@Transactional
	@Query(value = "update person_info set city = ?2 where age <= ?1", nativeQuery = true)
	public int updateCityByAgeLessThanEqual(int age, String city);

	// @Query 中的語句不加上 nativeQuery = true，表示 nativeQuery = false(預設)
	// nativeQuery = false，語法中操作的對象是 Entity name 和 屬性(變數)名稱
	// 語法中的 city 和 id 是屬性(變數)名稱，只是剛好和欄位名稱同名
	@Modifying
	@Transactional
	@Query(value = "update PersonInfo set city = ?2 where id = ?1")
	public int updateCityById2(String id, String city);

	// clearAutomatically = true: 更新資料後會清除暫存資料
	// 用於: 更新資料後，再從DB取得的資料依然是舊資料，就可以使用 clearAutomatically = true
	@Modifying(clearAutomatically = true)
	@Transactional
	@Query(value = "update PersonInfo set city = ?2 where id = ?1")
	public int updateCityById3(String id, String city);
	// ===========================
	// select

	@Query(value = "select * from person_info where city = ?1", nativeQuery = true)
	public List<PersonInfo> findByCity(String city);

	// nativeQuery = true，只能撈取所有的欄位
	@Query(value = "select id, name, age, city from person_info where city = ?1", nativeQuery = true)
	public List<PersonInfo> findByCity1(String city);

	// nativeQuery = false，是操作Entity和屬性，撈取的欄位必須要有對應的建構方法
	// 因為語法中有使用到 new PersonInfo(id, name, age)，所以就要有對應的建構方法
	@Query(value = "select new PersonInfo(id, name, age) from PersonInfo where city = ?1")
	public List<PersonInfo> findByCity2(String city);

	// nativeQuery = false，使用別名(名稱自定義)，表示撈取的是整個 Entity
	// 語法中沒使用到 new PersonInfo(id, name, age, city)，
	// 所以即使把帶有全部參數的建構方法註解掉也不會報錯
	@Query(value = "select p from PersonInfo as p where city = ?1")
	public List<PersonInfo> findByCity3(String city);

	// select distinct
	@Query(value = "select distinct new PersonInfo(city) from PersonInfo")
	public List<PersonInfo> findDistinctCity();

	// order by: asc default
//	@Query(value = "select * from person_info where city = ?1 order by age", nativeQuery = true)
	@Query("select p from PersonInfo as p where city = ?1 order by age")
	public List<PersonInfo> findByCityOrderByAge(String city);

	// order by + limit: limit 只能在 nativeQuery = true 執行
	@Query(value = "select * from person_info where city = ?1 order by age limit ?2", nativeQuery = true)
	public List<PersonInfo> findByCityOrderByAgeLimit(String city, int limit);

	// limit + 起始 index
	// limit 5(限制5筆) 等同於 limit 0, 5(限制5筆，從 index = 0 開始算)
	// limit 0, 5 --> 0: 表示 start_index, 5表示限制的回傳筆數
	@Query(value = "select * from person_info where city = ?1 order by age limit ?2, ?3", nativeQuery = true)
	public List<PersonInfo> findByCityOrderByAgeLimit2(String city, int startIndex, int limit);

	// between
	@Query(value = "select p from PersonInfo as p where age between ?1 and ?2")
//	@Query(value = "select * from person_info where age between ?1 and ?2", nativeQuery = true)
	public List<PersonInfo> findByAgeBetween(int age1, int age2);

	// in: @Query 中的語法無法達到動態參數，所以建議直接使用 JPA 的 in
	@Query(value = "select * from person_info where city in(?1, ?2, ?3)", nativeQuery = true)
	public List<PersonInfo> findByCityIn(String city1, String city2, String city3);

	// like
//	@Query(value = "select * from person_info where city like %?1%", nativeQuery = true)
	@Query(value = "select p from PersonInfo as p where city like %?1%")
	public List<PersonInfo> findByCityLike(String str);

	// like + ignore case(忽略大小寫):
	// LOWER/lower: 把比較的值轉成全小寫比較
	// UPPER/upper: 把比較的值轉成全大寫比較
	// concat: 把字串和值串成字串，SQL語法的字串是用單引號
//	@Query(value = "select * from person_info where Lower(city) like Lower(concat('%',?1,'%'))", nativeQuery = true)
	@Query(value = "select p from PersonInfo as p where lower(city) like lower(concat('%',?1,'%'))")
	public List<PersonInfo> findByCityContainingIgnoreCase(String str);

	// regexp: 只能在 nativeQuery = true 執行
	@Query(value = "select * from person_info where city regexp ?1", nativeQuery = true)
	public List<PersonInfo> findByCityRegexp(String str1);

	// regexp:
	@Query(value = "select * from person_info where city regexp concat(?1, '|', ?2)", nativeQuery = true)
	public List<PersonInfo> findByCityRegexp(String str1, String str2);

	// join
	// 1. 建議使用 nativeQuery = false，因為使用 nativeQuery = true 時，List 中的資料型態是 Object(無 getter/setter)
	// 2. 因為 JoinVo 沒有被 Spring Boot 託管(class 上沒有加任何的 Annotation)，所以必須要告知路徑(package)
	// 3. JoinVo 中的所有欄位，要使用 表.欄位(別名.欄位)
	@Query(value = "select new com.example.practice.vo.JoinVo(p.id, p.name, p.age, p.city, a.amount) "
			+ " from PersonInfo as p join Atm as a on p.id = a.account")
	public List<JoinVo> joinById();

}
