package com.nchu.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nchu.pojo.Users;

/**
 * JpaSpecificationExecutor讲解
 * 
 * JpaSpecificationExecutor这个接口不能单独使用，需要配合JPA中其他接口一起使用
 * 
 * @author 时间
 *
 */
public interface UsersDao extends JpaRepository<Users, Integer>, JpaSpecificationExecutor<Users> {

}
