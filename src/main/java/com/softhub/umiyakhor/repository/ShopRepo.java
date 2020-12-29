package com.softhub.umiyakhor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softhub.umiyakhor.entity.ShopVo;

@Repository
public interface ShopRepo extends CrudRepository<ShopVo, Long> {

	@Query("update ShopVo set isDeleted=1 where id=?1")
	@Modifying
	void deleteById(long id);

	List<ShopVo> findByName(String trim);

	List<ShopVo> findAllByOrderByCreatedDateDesc();
}
