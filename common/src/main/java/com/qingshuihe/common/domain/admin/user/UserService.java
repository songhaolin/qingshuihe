package com.qingshuihe.common.domain.admin.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingshuihe.common.domain.admin.user.entity.UserEntity;
import com.qingshuihe.common.interfaces.outbond.admin.vo.LoginUserVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.QueryPageVo;
import com.qingshuihe.common.interfaces.outbond.admin.vo.RegisterUserVO;
import com.qingshuihe.common.interfaces.outbond.dto.ResultDo;
import com.qingshuihe.common.interfaces.outbond.dto.ResultPageDo;

/**
 * @author song
 */
public interface UserService extends IService<UserEntity> {
    LoginUserVo findUserByName(String username);

    ResultDo modifyUser(RegisterUserVO registerUserVO);
    ResultPageDo<RegisterUserVO> queryUser(QueryPageVo<RegisterUserVO> queryPageVo);
}
