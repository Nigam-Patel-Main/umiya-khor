package com.softhub.umiyakhor.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softhub.umiyakhor.entity.PurchaseVo;

@Repository
@Transactional
public interface PurchaseRepo extends CrudRepository<PurchaseVo, Long> {

	@Query("update PurchaseVo set isDeleted=1 where id=?1")
	@Modifying
	void deleteById(long id);

	List<PurchaseVo> findAllByOrderByCreatedDateDesc();

	@Query("delete from PurchaseItemVo where purchaseVo.id=?1")
	@Modifying
	void deletePurchaseItemByPurchaseId(long id);

	@Query("delete from ExpenseItemVo where purchaseVo.id=?1")
	@Modifying
	void deleteExpenseItemByPurchaseId(long id);

}
