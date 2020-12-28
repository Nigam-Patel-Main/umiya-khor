package com.softhub.umiyakhor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softhub.umiyakhor.entity.DistrictVo;
import com.softhub.umiyakhor.entity.VillageVo;

@Repository
public interface VillageRepo extends CrudRepository<VillageVo, Long> {
	
	@Query("update VillageVo set isDeleted=1 where id=?1")
	@Modifying
	void deleteById(long id);

	List<VillageVo> findAllByOrderByCreatedDateDesc();

	List<VillageVo> findByName(String trim);
}
