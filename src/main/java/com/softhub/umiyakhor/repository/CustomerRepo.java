package com.softhub.umiyakhor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softhub.umiyakhor.entity.CustomerVo;
import com.softhub.umiyakhor.entity.DistrictVo;

@Repository
public interface CustomerRepo extends CrudRepository<CustomerVo, Long> {

	@Query("update CustomerVo set isDeleted=1 where id=?1")
	@Modifying
	void deleteById(long id);

	List<CustomerVo> findByName(String trim);

	List<CustomerVo> findAllByOrderByCreatedDateDesc();
}
