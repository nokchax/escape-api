package com.nokchax.escape.entry.repository;

import com.nokchax.escape.entry.domain.Entry;
import com.nokchax.escape.entry.domain.EntryId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, EntryId>, EntryRepositoryCustom {
}
