package com.zonesion.layout.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.google.common.collect.Lists;
import com.zonesion.layout.model.TemplateEntity;
import com.zonesion.layout.model.TemplateVO;
import com.zonesion.layout.page.QueryResult;

/**    
 * @author andieguo andieguo@foxmail.com
 * @Description: TODO 
 * @date 2016年4月21日 上午11:48:55  
 * @version V1.0    
 */
public class TemplateDaoImpl extends JdbcDaoSupport implements TemplateDao {

	@Override
	public List<TemplateEntity> findAll() {
		// TODO Auto-generated method stub
		return getJdbcTemplate().query("select * from tb_template where aid in ( select id from tb_admin where visible=1)", new Object[] {}, 
				new BeanPropertyRowMapper<TemplateEntity>(TemplateEntity.class));
	}

	@Override
	public TemplateEntity findByTemplateId(int tid) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().queryForObject("select * from tb_template where id=? and aid in ( select id from tb_admin where visible=1)", new Object[]{tid},
				new BeanPropertyRowMapper<TemplateEntity>(TemplateEntity.class));
	}

	@Override
	public List<TemplateEntity> findByAdminId(int aid,int visible) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().query("select * from tb_template where aid=? and visible=?", new Object[] {aid,visible}, 
				new BeanPropertyRowMapper<TemplateEntity>(TemplateEntity.class));
	}
	
	@Override
	public List<Integer> findByAdminId(int aid) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().queryForList("select id from tb_template where aid=?", new Object[] {aid},Integer.class);
	}
	
	@Override
	public List<TemplateEntity> findByAdminIdAndTid(int aid,int tid,int visible){
		return getJdbcTemplate().query("select * from tb_template where aid=? and id=? and visible=?", new Object[] {aid,tid,visible}, 
				new BeanPropertyRowMapper<TemplateEntity>(TemplateEntity.class));
	}
	
	@Override
	public List<TemplateEntity> findByAdminAndType(int aid, int type,int visible) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().query("select * from tb_template where aid=? and type=? and visible=?", new Object[] {aid,type,visible}, 
				new BeanPropertyRowMapper<TemplateEntity>(TemplateEntity.class));
	}
	
	@Override
	public List<TemplateEntity> findByTempalteAndType(int tid,int type,int visible){
		return getJdbcTemplate().query("select * from tb_template where id=? and type=? and visible=?", new Object[] {tid,type,visible}, 
				new BeanPropertyRowMapper<TemplateEntity>(TemplateEntity.class));	
	}
	
	@Override
	public List<TemplateEntity> findByType(int type) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().query("select * from tb_template where type=? and visible=1", new Object[] {type}, 
				new BeanPropertyRowMapper<TemplateEntity>(TemplateEntity.class));
	}

	@Override
	public int save(TemplateEntity templateEntity) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().update("insert into tb_template(name, layoutJSON, layoutContent, aid, type, createTime, modifyTime) values(?,?,?,?,?,?,?)", 
				new Object[] {templateEntity.getName(),templateEntity.getLayoutJSON(),templateEntity.getLayoutContent(),
				templateEntity.getAid(),templateEntity.getType(),templateEntity.getCreateTime(),templateEntity.getModifyTime()});
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().update("update tb_template set visible=0 where id=?",new Object[]{id});
	}
	
	@Override
	public int delete(int[] ids,int aid) {
		// TODO Auto-generated method stub
		if(ids.length > 0){
			Object[] params = new Object[ids.length+1];
			StringBuffer sql = new StringBuffer("delete from tb_template where id in (");
			for(int i=0;i<ids.length;i++){
				params[i] = ids[i];
				sql.append("?,");
			}
			sql.delete(sql.length()-1, sql.length()).append(") and aid=?");
			params[ids.length] = aid;
			return getJdbcTemplate().update(sql.toString(),params);	
		}else{
			return -1;
		}
	}
	
	@Override
	public int delete(int[] ids) {
		// TODO Auto-generated method stub
		if(ids.length > 0){
			Object[] params = new Object[ids.length];
			StringBuffer sql = new StringBuffer("delete from tb_template where id in (");
			for(int i=0;i<ids.length;i++){
				params[i] = ids[i];
				sql.append("?,");
			}
			sql.delete(sql.length()-1, sql.length()).append(")");
			return getJdbcTemplate().update(sql.toString(),params);	
		}else{
			return -1;
		}
	}
	
	@Override
	public int enable(int id,int visible) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().update("update tb_template set visible=? where id=?",new Object[]{visible,id});
	}

	@Override
	public int update(TemplateEntity templateEntity) {
		// TODO Auto-generated method stub
		return getJdbcTemplate().update("update tb_template set name=?, layoutJSON=?, layoutContent=?, aid=?, type=?, createTime=?, modifyTime=? where id=?", 
				new Object[] {templateEntity.getName(),templateEntity.getLayoutJSON(),templateEntity.getLayoutContent(),
				templateEntity.getAid(),templateEntity.getType(),templateEntity.getCreateTime(),templateEntity.getModifyTime(),templateEntity.getId()});
	}

	@Override
	public QueryResult<TemplateEntity> findAll(int firstindex, int maxresult) {
		// TODO Auto-generated method stub
		QueryResult<TemplateEntity> queryResult = new QueryResult<TemplateEntity>();
		List<TemplateEntity> templateList = getJdbcTemplate().query("select * from tb_template where aid in ( select id from tb_admin where visible=1) and id limit ?,?", new Object[] {firstindex,maxresult}, 
				new BeanPropertyRowMapper<TemplateEntity>(TemplateEntity.class));
		queryResult.setResultlist(templateList);
		int count = getJdbcTemplate().queryForObject("select count(*) from tb_template where aid in ( select id from tb_admin where visible=1)", Integer.class);
		queryResult.setTotalrecord(count);
		return queryResult;
	}

	@Override
	public QueryResult<TemplateEntity> findByAdminId(int aid, int firstindex, int maxresult) {
		// TODO Auto-generated method stub
		QueryResult<TemplateEntity> queryResult = new QueryResult<TemplateEntity>();
		List<TemplateEntity> templateList = getJdbcTemplate().query("select * from tb_template where aid=? and id limit ?,?", new Object[] {aid,firstindex,maxresult}, 
				new BeanPropertyRowMapper<TemplateEntity>(TemplateEntity.class));
		queryResult.setResultlist(templateList);
		int count = getJdbcTemplate().queryForObject("select count(*) from tb_template where aid=?",new Object[]{aid}, Integer.class);
		queryResult.setTotalrecord(count);
		return queryResult;
	}

	@Override
	public QueryResult<TemplateEntity> findByAdminAndType(int aid, int type, int firstindex, int maxresult) {
		// TODO Auto-generated method stub
		QueryResult<TemplateEntity> queryResult = new QueryResult<TemplateEntity>();
		List<TemplateEntity> templateList = getJdbcTemplate().query("select * from tb_template where aid=? and type=? and id limit ?,?", new Object[] {aid,type,firstindex,maxresult}, 
				new BeanPropertyRowMapper<TemplateEntity>(TemplateEntity.class));
		queryResult.setResultlist(templateList);
		int count = getJdbcTemplate().queryForObject("select count(*) from tb_template where aid=? and type=?",new Object[]{aid,type}, Integer.class);
		queryResult.setTotalrecord(count);
		return queryResult;
	}
	
	@Override
	public QueryResult<TemplateVO> findByAdminAndType(int aid, int type, int visible,int firstindex, int maxresult){
		QueryResult<TemplateVO> queryResult = new QueryResult<TemplateVO>();
		
		StringBuffer sql  = new StringBuffer("select t1.*,nickname from tb_template t1 inner join tb_admin t2 on t1.aid=t2.id where 1=1");
		StringBuffer countSql = new StringBuffer("select count(*) from tb_template t1 inner join tb_admin t2 on t1.aid=t2.id where 1=1");
		List<Integer> parmas = Lists.newArrayList();
		List<Integer> countParmas = Lists.newArrayList();
		if(aid != -1){//aid=-1表示所有存在的管理员
			sql.append(" and t1.aid=?");
			parmas.add(aid);
			countSql.append(" and t1.aid=?");
			countParmas.add(aid);
		}else{
			sql.append(" and t2.visible=1");
			countSql.append(" and t2.visible=1");
		}
		if(type != -1){//type=-1表示所有类型（系统模板、普通模板）
			sql.append(" and t1.type=?");
			parmas.add(type);
			countSql.append(" and t1.type=?");
			countParmas.add(type);
		}
		if(visible != -1){//visibel=-1包括删除、未删除模板
			sql.append(" and t1.visible=?");
			parmas.add(visible);
			countSql.append(" and t1.visible=?");
			countParmas.add(visible);
		}
		sql.append(" and t1.id limit ?,?");
		parmas.add(firstindex);
		parmas.add(maxresult);
		List<TemplateVO> templateList = getJdbcTemplate().query(sql.toString(), parmas.toArray(),
				new RowMapper<TemplateVO>(){

					@Override
					public TemplateVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						// TODO Auto-generated method stub
						TemplateVO templateVO = new TemplateVO();
						templateVO.setId(rs.getInt("id"));
						templateVO.setName(rs.getString("name"));
						templateVO.setLayoutContent(rs.getString("layoutContent"));
						templateVO.setLayoutJSON(rs.getString("layoutJSON"));
						templateVO.setAid(rs.getInt("aid"));
						templateVO.setType(rs.getInt("type"));
						templateVO.setCreateTime(rs.getDate("createTime"));
						templateVO.setModifyTime(rs.getDate("modifyTime"));
						templateVO.setVisible(rs.getInt("visible"));
						templateVO.setNickname(rs.getString("nickname"));
						return templateVO;
					}
		});	
		//查询记录
		queryResult.setResultlist(templateList);
		int count = getJdbcTemplate().queryForObject(countSql.toString(), countParmas.toArray(), Integer.class);
		//查询总记录数
		queryResult.setTotalrecord(count);
		return queryResult;
	}

	@Override
	public QueryResult<TemplateVO> findByAdminAndType(String nickname, String templatename, int type, int visible, int firstindex, int maxresult) {
		// TODO Auto-generated method stub
		QueryResult<TemplateVO> queryResult = new QueryResult<TemplateVO>();
		StringBuffer sql  = new StringBuffer("select t1.*,nickname from tb_template t1 inner join tb_admin t2 on t1.aid=t2.id where 1=1");
		StringBuffer countSql = new StringBuffer("select count(*) from tb_template t1 inner join tb_admin t2 on t1.aid=t2.id where 1=1");
		List<Object> parmas = Lists.newArrayList();
		List<Object> countParmas = Lists.newArrayList();
		if(nickname != null && !nickname.equals("")){//aid=-1表示所有存在的管理员
			sql.append(" and t2.nickname=?");
			parmas.add(nickname);
			countSql.append(" and t2.nickname=?");
			countParmas.add(nickname);
		}else{
			sql.append(" and t2.visible=1");
			countSql.append(" and t2.visible=1");
		}
		if(templatename != null && !templatename.equals("")){//aid=-1表示所有存在的管理员
			sql.append(" and t1.name=?");
			parmas.add(templatename);
			countSql.append(" and t1.name=?");
			countParmas.add(templatename);
		}
		if(type != -1){//type=-1表示所有类型（系统模板、普通模板）
			sql.append(" and t1.type=?");
			parmas.add(type);
			countSql.append(" and t1.type=?");
			countParmas.add(type);
		}
		if(visible != -1){//visibel=-1包括删除、未删除模板
			sql.append(" and t1.visible=?");
			parmas.add(visible);
			countSql.append(" and t1.visible=?");
			countParmas.add(visible);
		}
		sql.append(" and t1.id limit ?,?");
		parmas.add(firstindex);
		parmas.add(maxresult);
		List<TemplateVO> templateList = getJdbcTemplate().query(sql.toString(), parmas.toArray(),
				new RowMapper<TemplateVO>(){

					@Override
					public TemplateVO mapRow(ResultSet rs, int rowNum) throws SQLException {
						// TODO Auto-generated method stub
						TemplateVO templateVO = new TemplateVO();
						templateVO.setId(rs.getInt("id"));
						templateVO.setName(rs.getString("name"));
						templateVO.setLayoutContent(rs.getString("layoutContent"));
						templateVO.setLayoutJSON(rs.getString("layoutJSON"));
						templateVO.setAid(rs.getInt("aid"));
						templateVO.setType(rs.getInt("type"));
						templateVO.setCreateTime(rs.getDate("createTime"));
						templateVO.setModifyTime(rs.getDate("modifyTime"));
						templateVO.setVisible(rs.getInt("visible"));
						templateVO.setNickname(rs.getString("nickname"));
						return templateVO;
					}
		});	
		//查询记录
		queryResult.setResultlist(templateList);
		int count = getJdbcTemplate().queryForObject(countSql.toString(), countParmas.toArray(), Integer.class);
		//查询总记录数
		queryResult.setTotalrecord(count);
		return queryResult;
	}

}
