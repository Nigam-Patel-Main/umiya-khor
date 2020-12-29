package com.softhub.umiyakhor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softhub.umiyakhor.entity.ProductVo;

@Repository
public interface ProductRepo extends CrudRepository<ProductVo, Long> {

	@Query("update ProductVo set isDeleted=1 where id=?1")
	@Modifying
	void deleteById(long id);

	List<ProductVo> findByName(String trim);

	List<ProductVo> findAllByOrderByCreatedDateDesc();
}
