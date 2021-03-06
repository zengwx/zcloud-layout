package com.zonesion.layout.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.google.common.collect.Lists;
import com.zonesion.layout.model.AdminEntity;
import com.zonesion.layout.page.QueryResult;

/**
 * @author andieguo andieguo@foxmail.com
 * @Description: TODO
 * @date 2016年4月21日 上午11:48:19
 * @version V1.0
 */
public class AdminDaoImpl extends JdbcDaoSupport implements AdminDao {

	@Override
	public List<AdminEntity> findAll() {
		// TODO Auto-generated method stub
		return getJdbcTemplate().query("select * from tb_admin", 
				new Object[] {}, new BeanPropertyRowMapper<AdminEntity>(AdminEntity.class));
	}
	
	@Override
	public List<AdminEntity> findAll(int visible){
		// TODO Auto-generated method stub
		return getJdbcTemplate().query("select * from tb_admin where visible=?", 
				new Object[] {visible}, new BeanPropertyRowMapper<AdminEntity>(AdminEntity.class));
	}
	
	@Override
	public QueryResult<AdminEntity> findAll(int firstindex,int maxresult) {
		// TODO Auto-generated method stub
		QueryResult<AdminEntity> qr = new QueryResult<AdminEntity>();
		List<AdminEntity> adminList = getJdbcTemplate().query("select * from tb_admin where id limit ?,?", 
				new Object[] {firstindex,maxresult}, new BeanPropertyRowMapper<AdminEntity>(AdminEntity.class));		
		//查询记录
		qr.setResultlist(adminList);
		int count = getJdbcTemplate().queryForObject("select count(*) from tb_admin", Integer.class);
		//查询总记录数
		qr.setTotalrecord(count);
		return qr;
	}
	
	@Override
	public QueryResult<AdminEntity> findAll(int firstindex,int maxresult,int visible,int role,String nickname) {
		// TODO Auto-generated method stub
		QueryResult<AdminEntity> qr = new QueryResult<AdminEntity>();
		StringBuffer sql  = new StringBuffer("select * from tb_admin where 1=1");
		StringBuffer countSql = new StringBuffer("select count(*) from tb_admin where 1=1");
		List<Object> parmas = Lists.newArrayList();
		List<Object> countParmas = Lists.newArrayList();
		if(nickname!=null && !nickname.equals("")){
			sql.append(" and nickname=?");
			parmas.add(nickname);
			countSql.append(" and nickname=?");
			countParmas.add(nickname);
		}
		if(visible != -1){
			sql.append(" and visible=?");
			parmas.add(visible);
			countSql.append(" and visible=?");
			countParmas.add(visible);
		}
		if(role != -1){
			sql.append(" and role=?");
			parmas.add(role);
			countSql.append(" and role=?");
			countParmas.add(role);
		}
		sql.append(" and id limit ?,?");
		parmas.add(firstindex);
		parmas.add(maxresult);
		List<AdminEntity> adminList = getJdbcTemplate().query(sql.toString(), parmas.toArray(), new BeanPropertyRowMapper<AdminEntity>(AdminEntity.class));	
		//查询记录
		qr.setResultlist(adminList);
		int count = getJdbcTemplate().queryForObject(countSql.toString(), countParmas.toArray(), Integer.class);
		//查询总记录数
		qr.setTotalrecord(count);
		return qr;
	}

	@Override
	public AdminEntity findById(int id) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().queryForObject("select * from tb_admin where id=?",
				new BeanPropertyRowMapper<AdminEntity>(AdminEntity.class), new Object[]{id});
	}

	@Override
	public AdminEntity login(String nickname,String password,int role,int visible) {
		// TODO Auto-generated method stub
		AdminEntity admin = null;
		try{
			admin = getJdbcTemplate().queryForObject("select * from tb_admin where nickname=? and password=? and role=? and visible=?", 
					new BeanPropertyRowMapper<AdminEntity>(AdminEntity.class), new Object[]{nickname,password,role,visible});
		}catch(Exception e){
			if(e instanceof EmptyResultDataAccessException){
				return null;
			}
		}
		return admin;
	}

	@Override
	public boolean existAdminName(String nickname) {
		// TODO Auto-generated method stub
		try{
			getJdbcTemplate().queryForObject("select * from tb_admin where nickname=?", 
					new BeanPropertyRowMapper<AdminEntity>(AdminEntity.class), new Object[]{nickname});
		}catch(Exception e){
			if(e instanceof EmptyResultDataAccessException){
				return false;
			}
		}
		return true;
	}

	@Override
	public int register(AdminEntity admin) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().update(
				"insert into tb_admin(nickname,password,phoneNumber,email,sex,role,createTime,modifyTime) values (?,?,?,?,?,?,?,?)",
				new Object[] {admin.getNickname(),admin.getPassword(),admin.getPhoneNumber(),admin.getEmail(),admin.getSex(),admin.getRole(),admin.getCreateTime(),admin.getModifyTime()});
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().update("update tb_admin set visible=0 where id=?",new Object[] {id});	
	}
	
	@Override
	public int enable(int id,int visible) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().update("update tb_admin set visible=? where id=?",new Object[] {visible,id});	
	}

	@Override
	public int update(AdminEntity admin) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().update(
				"update tb_admin set nickname=?,password=?,phoneNumber=?,email=?,sex=?,role=?,createTime=?,modifyTime=? where id=?",
				new Object[] {admin.getNickname(),admin.getPassword(),admin.getPhoneNumber(),admin.getEmail(),admin.getSex(),admin.getRole(),admin.getCreateTime(),admin.getModifyTime(),admin.getId()});	
	}

	@Override
	public boolean confirmPasswd(String password, int id) {
		// TODO Auto-generated method stub
		try{
			getJdbcTemplate().queryForObject("select * from tb_admin where password=? and id=?", 
					new BeanPropertyRowMapper<AdminEntity>(AdminEntity.class), new Object[]{password,id});
		}catch(Exception e){
			if(e instanceof EmptyResultDataAccessException){
				return false;
			}
		}
		return true;
	}
}
