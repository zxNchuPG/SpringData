package com.nchu.test;

import java.util.ArrayList;
import java.util.List;

import javax.naming.directory.DirContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
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
public class JpaSpecificationExecutorTest {
	@Autowired
	private UsersDao usersDao;

	/**
	 * 单条件查询 根据用户姓名查询数据
	 */
	@Test
	public void test1() {
		Specification<Users> spec = new Specification<Users>() {
			/**
			 * @return Predicate:定义了查询条件,封装的一个查询对象
			 * @Param Root<Users> root:根对象.封装了查询条件的对象
			 * @Param CriteriaQuery query:定义了一个基本的查询,一般不适用
			 * @Param CriteriaBuilder cb:创建一个查询的条件
			 */
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pre = cb.equal(root.get("username"), "zhouxiang");
				return pre;
			}
		};
		List<Users> list = this.usersDao.findAll(spec);
		for (Users user : list) {
			System.out.println(user);
		}
	}

	/**
	 * 多条件查询 方式一 需求：用户名 且 年龄 查询
	 */
	@Test
	public void test2() {
		Specification<Users> spec = new Specification<Users>() {
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 多条件查询,那么就会有多个Predicate对象
				List<Predicate> list = new ArrayList<Predicate>();
				list.add(cb.equal(root.get("username"), "zhouxiang"));
				list.add(cb.equal(root.get("userage"), "25"));
				// 此时条件是没有任何关系的
				Predicate[] arr = new Predicate[list.size()];
				return cb.and(list.toArray(arr));
			}
		};
		List<Users> list = this.usersDao.findAll(spec);
		for (Users user : list) {
			System.out.println(user);
		}
	}

	/**
	 * 多条件查询 方式二 需求：用户名 或 年龄 查询
	 */
	@Test
	public void test3() {
		Specification<Users> spec = new Specification<Users>() {
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				// 不采用list,免去创建数组
				return cb.or(cb.equal(root.get("username"), "zhouxiang"), cb.equal(root.get("userage"), 24));
			}
		};
		List<Users> list = this.usersDao.findAll(spec);
		for (Users user : list) {
			System.out.println(user);
		}
	}

	/**
	 * 需求：查询姓名，并做分页处理
	 */
	@Test
	public void test4() {
		Specification<Users> spec = new Specification<Users>() {

			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("username"), "zh%");
			}
		};
		Pageable pageable = new PageRequest(0, 2);
		Page<Users> page = this.usersDao.findAll(spec, pageable);
		System.out.println("数据总条数:" + page.getTotalElements());
		System.out.println("总页数：" + page.getTotalPages());
		List<Users> list = page.getContent();
		for (Users user : list) {
			System.out.println("每页的结果集：" + user);
		}
	}

	/**
	 * 排序处理 需求：按姓名查询，并按id排序
	 */
	@Test
	public void test5() {
		Specification<Users> spec = new Specification<Users>() {

			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("username"), "zh%");
			}
		};
		Sort sort = new Sort(Direction.DESC, "userid");
		List<Users> list = this.usersDao.findAll(spec, sort);
		for (Users user : list) {
			System.out.println(user);
		}
	}

	/**
	 * 需求：分页处理，并且 按 姓名查询和按id排序
	 */
	@Test
	public void test6() {
		//排序定义
		Sort sort = new Sort(Direction.DESC,"userid");
		//分页定义
		Pageable pageable = new PageRequest(0, 2, sort);
		Specification<Users> spec = new Specification<Users>() {
			
			@Override
			public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.like(root.get("username"), "zh%");
			}
		};
		
		Page page = this.usersDao.findAll(spec, pageable);
		System.out.println("数据总条数:" + page.getTotalElements());
		System.out.println("总页数：" + page.getTotalPages());
		List<Users> list = page.getContent();
		for (Users user : list) {
			System.out.println("每页的结果集：" + user);
		}
	}
}
