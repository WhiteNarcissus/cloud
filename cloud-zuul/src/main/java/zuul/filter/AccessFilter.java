package zuul.filter;

import com.cardapprove.xshq.constant.Global;
import com.cardapprove.xshq.constant.Hint;
import com.cardapprove.xshq.domain.UserDetail;
import com.cardapprove.xshq.response.RestResult;
import com.cardapprove.xshq.services.RedisManager;
import com.cardapprove.xshq.services.RedisService;
import com.cardapprove.xshq.utils.web.JsonUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import zuul.constant.Constant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link ZuulFilter}
 * ZuulFilter
 * 主要用于对请求token的验证，
 * 以及对IP地址的扫描
 *
 * @author gcb
 */
public class AccessFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Resource
    private RedisService redisService;

    private static final List<String> LIST = new ArrayList<String>();
    static {
        LIST.add(Global.USER_PREFIX + "/login");
        LIST.add(Global.USER_PREFIX + "/getCaptcha");
        LIST.add(Global.CASE_ENTRY_PREFIX + "/push");
        LIST.add(Global.CASE_ENTRY_PREFIX + "/pad/push");
        LIST.add(Global.CASE_PREFIX + "/case/customerSys/queryCaseInfo");
        LIST.add(Global.CASE_PREFIX + "/case/customerSys/accept");
        //允许访问swagger
        LIST.add("swagger");
        LIST.add(Global.CASE_ENTRY_PREFIX + "/zxqz/preEntry");
        LIST.add(Global.CASE_PREFIX + "/zxqz/getApproveResult");

        LIST.add(Global.CASE_PREFIX + "/cardLoan/receive");
        Map<String, String> map = Constant.getDocs();
        if(!map.keySet().isEmpty()) {
            for (String key : map.keySet()) {
                LIST.add(map.get(key));
            }
        }
    }

    @Override
    public Object run() {
     return null;
//        return doRun();
    }

    public Object doRun() {
        RequestContext ctx = RequestContext.getCurrentContext();
        /**
         * 根据业务需求，增加和Token的校验
         */
        HttpServletRequest httpServletRequest = ctx.getRequest();
        String uri = httpServletRequest.getRequestURI();
        logger.info("请求uri:[{}]",uri);
        for (String value : LIST) {
            if (uri.indexOf(value) >= 0) {
                return true;
            }
        }

        String token = httpServletRequest.getHeader(Global.TOKEN_HEADER);
        try{

            /**
             * 用户是否有登出校验.
             */
            if (null == token || !redisService.exists(token)) {
                logger.info("User token doesn't exist, will not allow to perform");
                HttpServletResponse httpServletResponse = ctx.getResponse();
                if(!userNotLogin(httpServletResponse)){
                    ctx.setSendZuulResponse(false);
                    return false;
                }else{
                    return true;
                }
            }
            UserDetail userDetail = redisService.get(token, UserDetail.class);
            String key = RedisManager.getKeyWithUserId(userDetail.getUserId());
            if (!redisService.exists(key)) {
                HttpServletResponse httpServletResponse = ctx.getResponse();
                if(!userNotLogin(httpServletResponse)){
                    ctx.setSendZuulResponse(false);
                    return false;
                }else{
                    return true;
                }
            }
            /**
             * 刷新用户token
             */
            refreshToken(httpServletRequest);
        }catch (Exception e){
            ctx.setSendZuulResponse(false);
            logger.error("",e);
            return false;
        }
        return true;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 当用户验证通过时，即刷新用户token，不需要用户操作完再刷新
     */
    private void refreshToken(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(Global.TOKEN_HEADER);
        if (null != token && redisService.exists(token)) {
            UserDetail userDetail = redisService.get(token, UserDetail.class);
            Long userId = userDetail.getUserId();
            String key = RedisManager.getKeyWithUserId(userId);
            int times = 30 * 60;
            if (null != key && redisService.exists(key)) {
                String[] tokens = redisService.get(key) == null ? new String[]{} : redisService.get(key).split(",");
                for (String token1 : tokens) {
                    if (redisService.exists(token1)) {
                        redisService.setExpire(token1, times);
                    }
                }
                redisService.setExpire(key, times);
            }
        }
    }

    /**
     * 用户未登录时要做的操作.
     *
     * @param httpServletResponse
     * @return
     */
    private Boolean userNotLogin(HttpServletResponse httpServletResponse) {
        httpServletResponse.setContentType("application/json; charset=utf-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();
            out.append(JsonUtils.objectToJson(RestResult.build().code(Hint.UserConstant.USER_NOT_LOGIN_CODE)
                    .msg(Hint.UserConstant.USER_NOT_LOGIN)));
        } catch (IOException exc) {
            exc.printStackTrace();
        } finally {
            if (null != out) {
                out.close();
            }
        }
        return false;
    }

}
