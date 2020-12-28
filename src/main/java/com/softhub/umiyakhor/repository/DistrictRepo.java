package com.softhub.umiyakhor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softhub.umiyakhor.entity.DistrictVo;

@Repository
public interface DistrictRepo extends CrudRepository<DistrictVo, Long> {

	@Query("update DistrictVo set isDeleted=1 where id=?1")
	@Modifying
	void deleteById(long id);

	List<DistrictVo> findByName(String trim);

	List<DistrictVo> findAllByOrderByCreatedDateDesc();
}
