package cn.smbms.helper;

import java.util.HashMap;
import java.util.List;

/**
 * @author Ackles
 *
 * @param <T>
 */
/**
 * @author Ackles
 *
 * @param <T>
 */
public class PageHelper<T> {
	//参数   
	//1.分页参数   页码，每页显示行数
	private Integer pageNo=1;
	private Integer pageSize=5;
	//2.业务参数   不固定（参数个数不固定，名称固定）
	private HashMap<String,Object> params  = new HashMap<String,Object>();
	//数据
	//1.总行数（当前查询条件下）
	private Integer totalSize=0;
	//2.当前页的数据
	private List<T> results;
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public HashMap<String, Object> getParams() {
		params.put("startRow", (pageNo-1)*pageSize);
		params.put("pageSize", pageSize);
		return params;
	}
	public void setParams(HashMap<String, Object> params) {
		this.params = params;
	}
	public Integer getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}
	public List<T> getResults() {
		return results;
	}
	public void setResults(List<T> results) {
		this.results = results;
	}
	
	
	public Integer getTotalPage(){
		int totalPage = totalSize/pageSize;
		if(totalSize%pageSize!=0)
			totalPage++;
		return totalPage;
	}
	
	
	
}
