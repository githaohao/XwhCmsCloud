package com.xwh.system.validator;

import com.xwh.system.entity.SysUser;
import com.xwh.system.entity.vo.AuthRequestVo;

/**
 * <p>验证请求/auth接口时,请求参数的正确性</p>
 * <p>
 * <p>如果想拓展验证方法只需实现这个接口,然后在AuthenticationRestController类中注意相应实现的本接口的类即可</p>
 *
 * @author fhh
 * @date 2019-08-23 11:48
 */
public interface IReqValidator {

    /**
     * 通过请求参数验证
     *
     * @Date 2017/8/23 11:49
     * @return
     */
	SysUser validate(AuthRequestVo authRequestVo);
}
