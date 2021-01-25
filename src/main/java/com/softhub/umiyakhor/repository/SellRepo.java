package com.softhub.umiyakhor.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softhub.umiyakhor.entity.SellVo;

@Repository
@Transactional
public interface SellRepo extends CrudRepository<SellVo, Long> {

	@Query("update SellVo set isDeleted=1 where id=?1")
	@Modifying
	void deleteById(long id);

	List<SellVo> findAllByOrderByCreatedDateDesc();

	@Query("delete from SellItemVo where sellVo.id=?1")
	@Modifying
	void deleteSellItemBySellId(long id);

	@Query("delete from ExpenseItemVo where sellVo.id=?1")
	@Modifying
	void deleteExpenseItemBySellId(long id);

}
