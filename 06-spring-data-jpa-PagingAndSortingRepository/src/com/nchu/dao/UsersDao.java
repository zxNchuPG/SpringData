package com.nchu.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.nchu.pojo.Users;

/**
 * CrudRepository讲解
 * 
 * @author 时间
 *
 */
public interface UsersDao extends PagingAndSortingRepository<Users, Integer> {

}
