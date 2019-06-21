package com.nchu.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nchu.dao.UsersDao;
import com.nchu.pojo.Users;

/**
 * Repository接口测试
 * 
 * @author 时间
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PagingAndfSortingRepositoryTest {
	@Autowired
	private UsersDao usersDao;

	/**
	 * 分页
	 */
	@Test
	public void test1() {
		int page = 1; // 传递当前页的索引，从0开始
		int size = 3; // 每页显示的条数
		Pageable pageable = new PageRequest(page, size);
		Page<Users> pageList = this.usersDao.findAll(pageable);
		System.out.println("数据总条数:" + pageList.getTotalElements());
		System.out.println("总页数：" + pageList.getTotalPages());
		List<Users> list = pageList.getContent();
		for (Users user : list) {
			System.out.println("每页的结果集：" + user);
		}
	}

	/**
	 * 对单列做排序
	 */
	@Test
	public void test2() {
		// Sort 表示该对象封装了排序规则以及排序列(对象的属性)
		// direction;//排序规则
		// properties;//排序属性,不能是表的列名
		Sort sort = new Sort(Direction.ASC, "userid");
		List<Users> list = (List<Users>) this.usersDao.findAll(sort);
		for (Users user : list) {
			System.out.println("通过userid排序结果：" + user);
		}
	}

	/**
	 * 多列的排序处理
	 */
	@Test
	public void test3() {
		// Sort 表示该对象封装了排序规则以及排序列(对象的属性)
		// direction;//排序规则
		// properties;//排序属性,不能是表的列名
		Order order1 = new Order(Direction.DESC, "userage");
		Order order2 = new Order(Direction.DESC, "username");
		Sort sort = new Sort(order1,order2);
		List<Users> list = (List<Users>) this.usersDao.findAll(sort);
		for (Users user : list) {
			System.out.println("通过多列排序结果：" + user);
		}
	}
}
