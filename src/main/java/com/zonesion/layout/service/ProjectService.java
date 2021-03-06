package com.zonesion.layout.service;

import java.util.List;

import com.zonesion.layout.model.ProjectEntity;
import com.zonesion.layout.model.ProjectVO;
import com.zonesion.layout.page.QueryResult;

/**    
 * @author andieguo andieguo@foxmail.com
 * @Description: TODO 
 * @date 2016年4月21日 上午11:47:39  
 * @version V1.0    
 */
public interface ProjectService {


	public List<ProjectEntity> findAll();
	
	public QueryResult<ProjectEntity> findAll(int firstindex,int maxresult);
	
	public ProjectEntity findByProjectId(int pid);
	
	public List<ProjectEntity> findByAdminId(int aid);
	
	public QueryResult<ProjectEntity> findByAdminId(int aid,int firstindex,int maxresult);
	
	public QueryResult<ProjectVO> findByAdminIdAndTemplate(int aid,int tid,int visible,int firstindex,int maxresult);
	
	public QueryResult<ProjectVO> findByAdminIdAndTemplate(String nickname, String templatename,String name, int visible, int firstindex, int maxresult);
	
	public int save(ProjectEntity projectEntity);
	
	public int delete(int id);
	
	public int delete(int[] ids);
	
	public int enable(int id,int visible);
	
	public int update(ProjectEntity projectEntity);
	
}
