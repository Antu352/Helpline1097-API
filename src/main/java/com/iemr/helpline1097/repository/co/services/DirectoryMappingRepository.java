/*
* AMRIT – Accessible Medical Records via Integrated Technology
* Integrated EHR (Electronic Health Records) Solution
*
* Copyright (C) "Piramal Swasthya Management and Research Institute"
*
* This file is part of AMRIT.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program.  If not, see https://www.gnu.org/licenses/.
*/
package com.iemr.helpline1097.repository.co.services;

import java.util.Objects;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.iemr.helpline1097.data.co.services.DirectoryMapping;

@Repository
@RestResource(exported = false)
public interface DirectoryMappingRepository extends CrudRepository<DirectoryMapping, Long>
{
	@Query("select m.instituteDirMapID, i from DirectoryMapping m JOIN m.institutionDetails i "
			+ "where m.deleted = false and m.instituteDirectoryID = :instituteDirectoryID and "
			+ "m.instituteSubDirectoryID = :instituteSubDirectoryID and "
			+ "i.stateID = :stateID and i.districtID = :districtID and i.blockID = :blockID and "
			+ "i.districtBranchMappingID = :districtBranchMappingID and i.deleted = false")

	public Set<Objects[]> findAciveInstituteDirectories(@Param("instituteDirectoryID") Integer instituteDirectoryID,
			@Param("instituteSubDirectoryID") Integer instituteSubDirectoryID, @Param("stateID") Integer stateID,
			@Param("districtID") Integer districtID, @Param("blockID") Integer blockID,
			@Param("districtBranchMappingID") Integer districtBranchMappingID);

	@Query("select m.instituteDirMapID, i from DirectoryMapping m JOIN m.institutionDetails i "
			+ "where m.deleted = false and m.instituteDirectoryID = :instituteDirectoryID and "
			+ "m.instituteSubDirectoryID = :instituteSubDirectoryID and "
			+ "i.stateID = :stateID and i.districtID = :districtID and i.blockID = :blockID and i.deleted = false")

	public Set<Objects[]> findAciveInstituteDirectories(@Param("instituteDirectoryID") Integer instituteDirectoryID,
			@Param("instituteSubDirectoryID") Integer instituteSubDirectoryID, @Param("stateID") Integer stateID,
			@Param("districtID") Integer districtID, @Param("blockID") Integer blockID);

	@Query("select m.instituteDirMapID, i from DirectoryMapping m JOIN m.institutionDetails i "
			+ "where m.deleted = false and m.instituteDirectoryID = :instituteDirectoryID and "
			+ "m.instituteSubDirectoryID = :instituteSubDirectoryID and "
			+ "i.stateID = :stateID and i.districtID = :districtID and i.deleted = false")

	public Set<Objects[]> findAciveInstituteDirectories(@Param("instituteDirectoryID") Integer instituteDirectoryID,
			@Param("instituteSubDirectoryID") Integer instituteSubDirectoryID, @Param("stateID") Integer stateID,
			@Param("districtID") Integer districtID);
}
