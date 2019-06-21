package com.nchu.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.nchu.dao.UsersDao;
import com.nchu.pojo.Users;

/**
 * Repository接口测试
 * @author 时间
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class CrudRepositoryTest {
	@Autowired
	private UsersDao usersDao;
	
	/**
	 * 添加单条数据 或 多条,都可用save方法,多条传入List
	 */
	@Test
	public void test1() {
		Users user = new Users();
		user.setUserage(31);
		user.setUsername("crudRepository");
		this.usersDao.save(user);
	}
	
	/**
	 * 根据id查询单条
	 */
	@Test
	public void test2() {
		Users user = this.usersDao.findOne(6);
		System.out.println(user);
	}
	
	/**
	 * 查询全部数据
	 */
	@Test
	public void test3() {
		List<Users> list = (List<Users>) this.usersDao.findAll();
		for(Users user:list) {
			System.out.println(user);
		}
	}
	
	/**
	 * 删除数据
	 */
	@Test
	public void test4() {
		this.usersDao.delete(6);
	}
	
	/**
	 * 更新数据,没有提供update方法,先查出来,save会判断该对象是否存在
	 * 方式一:自己调用save方法
	 * 方法二:不需要调用save方法,因为JPA底层依赖hibernate实现，查出来的对象是持久化状态，直接set修改属性也会被持久化到数据库中
	 * 但是需要加@Transaction事务进行提交
	 */
	@Test
	public void test5() {
		Users user = this.usersDao.findOne(5);
		user.setUsername("testUpdate");
		this.usersDao.save(user);
	}
}
