package com.bharath.location.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

import com.bharath.location.entities.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
	
	//@Query("select type,count(type) from location group by type")
	@Query (value="select type,count(type) from location group by type", nativeQuery = true)
	public List<Object[]> findTypeAndTypeCount();

}
