package com.realpick.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realpick.vo.ResultVO;
import com.realpick.vo.StatusCode;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class CheckToeknInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //预检OPTIONS请求放行
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }

        String token = request.getHeader("token");
        if (token == null) {
            ResultVO resultVO = new ResultVO(StatusCode.LOGIN_OUT, "请先登录！", null);
            doResponse(response, resultVO);
        } else {
            try {
                JwtParser parser = Jwts.parser();
                parser.setSigningKey("realpick"); //解析token的SigningKey必须和生成token时设置密码一致

                //如果token正确（密码正确，有效期内）则正常执行，否则抛出异常
                Jws<Claims> claimsJws = parser.parseClaimsJws(token);
                return true;
            } catch (ExpiredJwtException e) {
                ResultVO resultVO = new ResultVO(StatusCode.LOGIN_INVALID, "登录过期，请重新登录！", null);
                doResponse(response, resultVO);
            } catch (UnsupportedJwtException e) {
                ResultVO resultVO = new ResultVO(StatusCode.LOGIN_OUT, "Token不合法！", null);
                doResponse(response, resultVO);
            } catch (Exception e) {
                ResultVO resultVO = new ResultVO(StatusCode.LOGIN_OUT, "请先登录！", null);
                doResponse(response, resultVO);
            }
        }
        return false;
    }

    private void doResponse(HttpServletResponse response, ResultVO resultVO) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        String s = new ObjectMapper().writeValueAsString(resultVO);
        out.print(s);
        out.flush();
        out.close();
    }

}
