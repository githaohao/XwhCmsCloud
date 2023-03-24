package com.xwh.system.service;

import com.xwh.core.dto.Result;
import org.springframework.stereotype.Service;

/**
 * @author xiangwenhao
 * @create 2022-02-10 02:04
 **/
@Service
public interface CaptchaService {

    Result verifyCode(String captcha, String captchaId);
}
