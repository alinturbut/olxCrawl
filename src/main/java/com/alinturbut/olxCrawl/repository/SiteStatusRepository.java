package com.alinturbut.olxCrawl.repository;

import com.alinturbut.olxCrawl.domain.SiteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteStatusRepository extends JpaRepository<SiteStatus, Long> {
    Optional<SiteStatus> findByRelatedPostUrl(String relatedPostUrl);
}
