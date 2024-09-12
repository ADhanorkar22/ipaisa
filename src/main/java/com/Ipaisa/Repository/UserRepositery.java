package com.Ipaisa.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Ipaisa.Entitys.User;

import jakarta.transaction.Transactional;

@Transactional
@Repository
public interface UserRepositery extends JpaRepository<User, String>{
	User findByUserid(String id);
	User findByMobileNumber(String mno);
	Integer countByRoleId(Integer id);
	User findByAadharNumber(String adhnum);

	@Query("SELECT new com.Ipaisa.Entitys.User(u.firstName, u.role) FROM User u WHERE u.userid = :userid AND u.parentId = :parentId")
	User reciverUnderSenderInfo(@Param("userid") String userid, @Param("parentId") String parentId);
	
	 @Modifying
	 @Query ("update User u set u.parentId = :newParentId where u.parentId = :oldParentId")
	    int changeParentOfPartners(@Param("oldParentId") String newParentId, @Param("newParentId") String oldParentId);

	  @Query(value = """
	            WITH RECURSIVE HierarchicalCTE AS (
	              SELECT *
	              FROM users
	              WHERE user_id = :userId
	              UNION ALL
	              SELECT u.*
	              FROM users u
	              INNER JOIN HierarchicalCTE h ON h.user_id = u.parent_id
	            )
	            SELECT *
	            FROM HierarchicalCTE
	            WHERE user_id != :userId;
	            """, nativeQuery = true)
	    List<User> findHierarchicalUsers(@Param("userId") String userId);
}

