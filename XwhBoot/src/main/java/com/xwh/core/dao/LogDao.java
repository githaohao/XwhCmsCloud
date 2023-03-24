package com.xwh.core.dao;

import com.xwh.core.entity.SysLog;
import org.apache.ibatis.annotations.Insert;

/**
 * @ClassName: LogDao
 * @Description: 日志dao
 */
public interface LogDao {
    /**
     * 保存日志
     */
	@Insert("insert into sys_log(id,operation,content,ip,method,type,create_by,create_date,update_by,update_date) values (#{id},#{operation},#{content},#{ip},#{method},#{type},#{createBy},#{createDate},#{updateBy},#{updateDate})")
	void saveLog(SysLog lv);
}
