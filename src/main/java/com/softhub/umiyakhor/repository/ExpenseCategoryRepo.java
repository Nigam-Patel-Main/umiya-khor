package com.softhub.umiyakhor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softhub.umiyakhor.entity.ExpenseCategoryVo;

@Repository
public interface ExpenseCategoryRepo extends CrudRepository<ExpenseCategoryVo, Long> {

	@Query("update ExpenseCategoryVo set isDeleted=1 where id=?1")
	@Modifying
	void deleteById(long id);

	List<ExpenseCategoryVo> findByName(String trim);

	List<ExpenseCategoryVo> findAllByOrderByCreatedDateDesc();
}
