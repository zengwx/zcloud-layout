package com.zonesion.layout.dao;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zonesion.layout.dao.AdminDao;
import com.zonesion.layout.model.AdminEntity;
import com.zonesion.layout.page.QueryResult;

import junit.framework.TestCase;

/**    
 * @author andieguo andieguo@foxmail.com
 * @Description: TODO 
 * @date 2016年4月21日 下午2:27:42  
 * @version V1.0    
 */
public class AdminDaoTest extends TestCase{

	private AdminDao adminDao;

	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		adminDao = (AdminDao)context.getBean("adminDao");
	}
	
	public void testRegister(){
		for(int i=0;i<100;i++){
			adminDao.register(new AdminEntity("andy"+i, "andy"+i, "123456","123456@qq.com", 0, 0, new Date(), new Date()));
		}
	}
	
	public void testFindById(){
		System.out.println(adminDao.findById(2));
	}
	
	public void testFindByAdminName(){
		System.out.println(adminDao.existAdminName("andy"));
		System.out.println(adminDao.existAdminName("adndy"));
	}
	
	public void testLogin(){
		System.out.println(adminDao.login("adny", "andy",1));
		System.out.println(adminDao.login("andy","andy",1));
	}
	
	public void testFindAll(){
		for(AdminEntity admin: adminDao.findAll()){
			System.out.println(admin);
		}
	}
	//第0页
	public void testFindAllPage0(){
		QueryResult<AdminEntity> queryResult = adminDao.findAll(0, 10);
		System.out.println(queryResult.getTotalrecord());
		for(AdminEntity admin:queryResult.getResultlist()){
			System.out.println(admin);
		}
	}
	
	//第0页
	public void testFindAllPage1(){
		QueryResult<AdminEntity> queryResult = adminDao.findAll(0+10, 10);
		System.out.println(queryResult.getTotalrecord());
		for(AdminEntity admin:queryResult.getResultlist()){
			System.out.println(admin);
		}
	}
	
	public void testUpdate(){
		AdminEntity admin = adminDao.findById(2);
		admin.setEmail("andy@qq.com");
		System.out.println(adminDao.update(admin));
	}
	
	public void testDelete(){
		System.out.println(adminDao.delete(2));
	}
}
