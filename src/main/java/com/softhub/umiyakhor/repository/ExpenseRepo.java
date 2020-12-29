package com.softhub.umiyakhor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softhub.umiyakhor.entity.ExpenseVo;

@Repository
public interface ExpenseRepo extends CrudRepository<ExpenseVo, Long> {

	@Query("update ExpenseVo set isDeleted=1 where id=?1")
	@Modifying
	void deleteById(long id);

	List<ExpenseVo> findByName(String trim);

	List<ExpenseVo> findAllByOrderByCreatedDateDesc();
}
