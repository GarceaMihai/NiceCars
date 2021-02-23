package com.utcn.is.NiceCars.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.utcn.is.NiceCars.model.Announcement;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
	@Query(value = "SELECT * FROM announcement a WHERE a.active = 1", nativeQuery = true)
	public List<Announcement> findAllActiveAnnouncements();
}
