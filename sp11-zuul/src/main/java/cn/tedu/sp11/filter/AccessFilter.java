package cn.tedu.sp11.filter;

import cn.tedu.sp01.pojo.Util.JsonResult;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AccessFilter extends ZuulFilter {
    //过滤器类型：pre,routes,post,error
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;  //配置成前置过滤器
    }

    //当前过滤器添加到哪个位置，返回一个顺序号
    @Override
    public int filterOrder() {
        /**
         * 前置过滤其中已经存在5个默认的过滤器
         * 在第五个过滤器中，向上下文对象添加了“service-id"属性
         * */
        return 6;
    }

    //针对当前请求进行判断，是否执行过滤代码（run方法）
    @Override
    public boolean shouldFilter() {
        //当前请求，调用的是否是item-service
        //如果请求item，执行过滤代码
        //否则跳过过滤代码

        //获得正在调用的服务id
        RequestContext ctx = RequestContext.getCurrentContext();//zuul请求上下文对象
        //从上下文对象获取“服务id”属性的值
        String serviceid = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);

        return "item-service".equalsIgnoreCase(serviceid);//equalsIgnoreCase忽略大小写
    }

    //过滤方法，权限判断写在这里
    @Override
    public Object run() throws ZuulException {
        //用request
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //用request接受token参数
        String token = request.getParameter("token");
        //如果token参数为空，阻止继续访问，返回登录提示
        if(StringUtils.isBlank(token)){  //
            //阻止继续访问
            ctx.setSendZuulResponse(false);
            String json = JsonResult.err().code(JsonResult.NOT_LOGIN).msg("Not Login").toString();
            ctx.setResponseStatusCode(JsonResult.NOT_LOGIN);
//            ctx.addZuulResponseHeader("Content-Type", "application/json:charset=UTF-8");  //显示中文
            ctx.setResponseBody(json);
        }
        return null;
    }
}
