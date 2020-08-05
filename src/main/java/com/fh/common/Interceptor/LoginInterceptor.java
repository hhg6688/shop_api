package com.fh.common.Interceptor;

import com.fh.util.JWT;
import com.fh.util.RedisUser;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Base64;
import java.util.Map;

public class LoginInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
       //从header里获取数据
        String token = request.getHeader("token");
        //判断头信息是否完整
        if (StringUtils.isEmpty(token)){
            throw  new Exception("头信息不完整");
        }
        //解密 解密出来是字节数组
        byte[] decode = Base64.getDecoder().decode(token);
        //得到字符数组 转为字符串
        String signToken=new String(decode);
        //判断是否被篡改
        String[] split = signToken.split(",");
        if (split.length != 2){
            throw new Exception("token格式不正确");
        }
        String iphoneNum=split[0];
        //jwt的秘钥
        String sign=split[1];
        Map user = JWT.unsign(sign, Map.class);
        if (user == null){
            throw new LoginException("没有登录");
        }
        if (user  != null ){//jwt验证过了
            String sign_redis = RedisUser.get("token_" + iphoneNum);
            if(!sign.equals(sign_redis)){//验证秘钥是不是最新的
                //返回json字符串
                throw new LoginException("验证已过期，重新登录");
            }
        }
        //前面逻辑验证过了 设置redis key值得有效时间为30分
        RedisUser.set("token_"+user.get("iphoneNum"),sign,60*30);
        //将用户信息放入request中 方便后面需求处理
        request.setAttribute("login_hhg",user);
        return true;
    }
}
