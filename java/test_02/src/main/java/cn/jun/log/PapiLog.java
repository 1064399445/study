package cn.jun.log;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PapiLog {

    static Map<String,String> map = new HashMap<>();
    /**匹配日至头(url)*/
    static Pattern patternHead = Pattern.compile("\\S*http://\\S*\n");
    /**匹配线程号*/
    static Pattern patternThreadNumber = Pattern.compile("catalina-exec-\\S*");
    /**匹配日期*/
    static Pattern patternTimestamp = Pattern.compile("\\d{4}[-]\\d{2}[-]\\d{2}[ ]\\d{2}[:]\\d{2}[:]\\d{2}");
    /**匹配日志级别*/
    static Pattern patternLogLevel = Pattern.compile("(ERROR)|(INFO)");
    /** 日期格式转换器*/
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) throws Exception{

        List<String> logs = new ArrayList<>();
        logs.add("2018-08-23 08:00:00 catalina-exec-3530  [INFO ]:com.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.preHandle(AuthorizationInterceptor.java:48) - 请求URL：http://chargeapitest.ucarinc.com/evpchargepapi/evcs/v20161110/query_token\n");
        logs.add("2018-08-23 08:00:00 catalina-exec-3530  [INFO ]:com.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.preHandle(AuthorizationInterceptor.java:50) - 请求参数：{\"Data\":\"BmuQPtmtMfSqKqTPqiWVUAYFBN6VGfUH7INq04il+WN/cUIZr3iVlfJoY52ESRFUOTN4VBXoVZILx1plzRAp2g==\",\"OperatorID\":\"313744932\",\"Seq\":\"0001\",\"Sig\":\"D8ACA8CFC3BF6DA0880BED6AEFBCF8F3\",\"TimeStamp\":\"20180823080000\"}\n");
        logs.add("2018-08-23 08:00:00 catalina-exec-3530  [INFO ]:com.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.postHandle(AuthorizationInterceptor.java:90) - 请求耗时3ms\n");

        logs.add("2018-08-23 08:00:00 catalina-exec-3521  [INFO ]:com.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.preHandle(AuthorizationInterceptor.java:48) - 请求URL：http://chargeapitest.ucarinc.com/evpchargepapi/evcs/v20161110/check_charge_orders\n");
        logs.add("2018-08-23 08:00:00 catalina-exec-3521  [INFO ]:com.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.preHandle(AuthorizationInterceptor.java:50) - 请求参数：{\"Data\":\"8SvDEeFAsjTuyGAr1SjY12/mCQm4cIJTeIEi8t4VYOX3xgW1qX2OpjsltYBLnH24vjPhr9L2OitejAs5r6xm8iHLeDck/GCNuWe0dDDi07ikkHSX8zy1caGyxpQY2FQku6+2I6xAwhfJxHGRr4nOc3WxfGHMwfxWY4n4ERY2o4PTmD6Tzp6HaG+6vh7iPEImSdXJ86qQQ3l+wgr6pLgMkZ6nogQ4loSyXsxXSSVqlDfX+4AsyAQUOAXAxfmXto8spYDKTFd8Xgy5gYcOrx4cig7rFLDb7nz6fnWZSZC7RlVDOvSmXvZiouGcAE1ojfdlC0kmyfdTc9lu7ZSQImU8MEVZ/3cxtAUTpyG4nLrgRcseXYA9oahtpw2OGM0NQOv1R1O/RQEVsIEa4FDCicQo4+j4tzi4Za45NsIJwHJrv41LdwulvtOg7yYB+lRYVcpZ2TPEwGLYTkFedi3SDsatiUpn0m7q48sLQesBqYmpKUL4MJfXl+UfJPlcTKOM//iz4OTHgenhCTmf4Iq8EA7fVZ6kbxsh6nG8h9e8sa0aLA4AThJLNZfgDUNxqCZrxEaae/61b0xmC3PTbhtoB0sQHGUm+1EoNAKFs+BVhdnFYGmCIa6M+6Fjx6a0OIFRmOLMQbiB+WNyzox/FY0AwgIWsAybyIOx5K5Vq4Wk2dX96xZxFymu+4cpl84WrFFiwKrdEwfFLNxZQwzH/t/GAOqJpG9s+mOlGYd3jqQcnOexzJ+bwajWzxXqwv/8FplP2lidBVh7YZ8+MQBajxOBvvfo0OYcrbvMDFjOIh6MSLE7ZiEuvbtOvb0V4b0Z10a3TR9oh1gvYXJk9h+wHaFb76JpOZ0tXaSloMl7soIM1cK+krvswfGZacvk1K723ZXMmY7u+Ya7VMs40QhzJnniqKdnZT4iJ46IYk9jpCVj8lk2NHg0ZCYHQgpaA/x0Odc191A0738KfUYwkyT2wfrI+ABKMprFasVusmAFRArOgpeVB4NGNwkdyP9uOoyriiKEPy6UDWWGyV6KZH5duVoB3jW1BefI4+4oQ5IocGw5Ym9VPaOR11oBhk7e2wRcoGBVWYxtPzNdDWpC0Hri6usVX9taPabOIRuGVjVYSQ5WERuJUHx0THa4C1VSfF2ibGxGyL4BTy4gTbEJhGSB+oWVKBB8DI7hd+I5CLEOUZDscvFBWqcqrzDR4Zoxb9QmySvatg0VrzgJRDCuhD+IhFkNziNEk9Pz30V8PU+pXwgKTyjq2gOnt9DxGSjvdOUx060s2e4NE4VL7d8HmO9CQfi2C2Hg5Q==\",\"OperatorID\":\"313744932\",\"Seq\":\"0001\",\"Sig\":\"0EFF45C92A32BB869A3D97CE2576A165\",\"TimeStamp\":\"20180823080000\"}\n");
        logs.add("2018-08-23 08:00:00 catalina-exec-3521  [INFO ]:com.evp.papi.common.NotificationObjectUtil.getMap(NotificationObjectUtil.java:131) - 解密后请求参数：{\"ChargeOrders\":[{\"StartChargeSeq\":\"313744932180822000000000007\",\"TotalMoney\":1.42,\"TotalPower\":0.90},{\"StartChargeSeq\":\"313744932180822000000000014\",\"TotalMoney\":0.47,\"TotalPower\":0.30},{\"StartChargeSeq\":\"313744932180822000000000026\",\"TotalMoney\":128.37,\"TotalPower\":89.90},{\"StartChargeSeq\":\"313744932180822000000000056\",\"TotalMoney\":0.79,\"TotalPower\":0.50},{\"StartChargeSeq\":\"313744932180822000000000063\",\"TotalMoney\":0.38,\"TotalPower\":0.30},{\"StartChargeSeq\":\"313744932180822000000000070\",\"TotalMoney\":1.39,\"TotalPower\":1.10},{\"StartChargeSeq\":\"313744932180822000000000081\",\"TotalMoney\":0.50,\"TotalPower\":0.40},{\"StartChargeSeq\":\"313744932180822000000000082\",\"TotalMoney\":0.13,\"TotalPower\":0.10},{\"StartChargeSeq\":\"313744932180822000000000117\",\"TotalMoney\":3.00,\"TotalPower\":1.90}],\"CheckOrderSeq\":\"740415113000000000020180822\",\"EndTime\":\"2018-08-22 23:59:59\",\"OrderCount\":9,\"StartTime\":\"2018-08-22 00:00:00\",\"TotalOrderMoney\":136.45,\"TotalOrderPower\":95.40}\n");
        logs.add("2018-08-23 08:00:00 catalina-exec-3530  [INFO ]:com.evp.papi.common.NotificationObjectUtil.getDTO(NotificationObjectUtil.java:68) - 解密后请求参数：{\"OperatorID\":\"313744932\",\"OperatorSecret\":\"1234567890abcdef\"}\n");
        logs.add("2018-08-23 08:00:01 catalina-exec-3521  [INFO ]:com.evp.papi.modules.charge.util.ResultUtil.getResult(ResultUtil.java:29) - 调用成功，服务名：NotifyCheckChargeOrderService.notificationCheckChargeOrders, 返回结果：{\"re\":{\"checkOrderSeq\":\"740415113000000000020180822\",\"disputeChargeOrderDTOS\":[],\"endTime\":\"2018-08-22 23:59:59\",\"startTime\":\"2018-08-22 00:00:00\",\"totalDisputeMoney\":0,\"totalDisputeOrder\":0,\"totalDisputePower\":0},\"status\":0,\"success\":true}\n");

        logs.add("2018-08-23 08:35:37 catalina-exec-3526  [INFO ]:com.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.preHandle(AuthorizationInterceptor.java:48) - 请求URL：http://chargeapitest.ucarinc.com/evpchargepapi/evcs/v20161110/notification_stationStatus\n");
        logs.add("2018-08-23 08:35:37 catalina-exec-3526  [INFO ]:com.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.preHandle(AuthorizationInterceptor.java:50) - 请求参数：{\"Data\":\"Bq+0LsmzLSCEoMjxBr7JEZiqs8Goy0eNs0xUSBu5loSaVAQsGbPCPUTZ8WmJcR4YKuJ+UI7JNPh9KeY9V1ypgDfOkkO7erS4ojKKY4aaDr6keHTx707Eb4k+FynielZPE3WL2N/aaDIQVYr3SRL0hA==\",\"OperatorID\":\"313744932\",\"Seq\":\"0001\",\"Sig\":\"4E9825472E6FA2A524ADB3B513A4852F\",\"TimeStamp\":\"20180823083537\"}\n");
        logs.add("2018-08-23 08:00:01 catalina-exec-3521  [INFO ]:com.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.postHandle(AuthorizationInterceptor.java:90) - 请求耗时179ms\n");
        logs.add("2018-08-23 08:35:37 catalina-exec-3526  [INFO ]:com.evp.papi.common.NotificationObjectUtil.getMap(NotificationObjectUtil.java:131) - 解密后请求参数：{\"ConnectorStatusInfo\":{\"ConnectorID\":\"11000000000000090000341000\",\"LockStatus\":0,\"ParkStatus\":50,\"Status\":3}}\n");
        logs.add("2018-08-23 08:35:37 catalina-exec-3526  [INFO ]:com.evp.papi.modules.charge.util.ResultUtil.getResult(ResultUtil.java:29) - 调用成功，服务名：NotifyConnectorStatusInfoService.pushConnectorStatusInfo, 返回结果：{\"re\":{\"status\":0},\"status\":0,\"success\":true}\n");
        logs.add("2018-08-23 08:35:37 catalina-exec-3526  [INFO ]:com.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.postHandle(AuthorizationInterceptor.java:90) - 请求耗时34ms\n");

        logs.add("2018-08-23 08:00:00 catalina-exec-3530  [INFO ]:aom.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.preHandle(AuthorizationInterceptor.java:48) - 请求URL：http://chargeapitest.ucarinc.com/evpchargepapi/evcs/v20161110/query_token\n");
        logs.add("2018-08-23 08:35:37 catalina-exec-3526  [INFO ]:aom.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.preHandle(AuthorizationInterceptor.java:48) - 请求URL：http://chargeapitest.ucarinc.com/evpchargepapi/evcs/v20161110/notification_stationStatus\n");
        logs.add("2018-08-23 08:00:00 catalina-exec-3530  [INFO ]:aom.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.preHandle(AuthorizationInterceptor.java:50) - 请求参数：{\"Data\":\"BmuQPtmtMfSqKqTPqiWVUAYFBN6VGfUH7INq04il+WN/cUIZr3iVlfJoY52ESRFUOTN4VBXoVZILx1plzRAp2g==\",\"OperatorID\":\"313744932\",\"Seq\":\"0001\",\"Sig\":\"D8ACA8CFC3BF6DA0880BED6AEFBCF8F3\",\"TimeStamp\":\"20180823080000\"}\n");
        logs.add("2018-08-23 08:35:37 catalina-exec-3526  [INFO ]:aom.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.preHandle(AuthorizationInterceptor.java:50) - 请求参数：{\"Data\":\"Bq+0LsmzLSCEoMjxBr7JEZiqs8Goy0eNs0xUSBu5loSaVAQsGbPCPUTZ8WmJcR4YKuJ+UI7JNPh9KeY9V1ypgDfOkkO7erS4ojKKY4aaDr6keHTx707Eb4k+FynielZPE3WL2N/aaDIQVYr3SRL0hA==\",\"OperatorID\":\"313744932\",\"Seq\":\"0001\",\"Sig\":\"4E9825472E6FA2A524ADB3B513A4852F\",\"TimeStamp\":\"20180823083537\"}\n");
        logs.add("2018-08-23 08:00:00 catalina-exec-3530  [INFO ]:aom.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.postHandle(AuthorizationInterceptor.java:90) - 请求耗时3ms\n");
        logs.add("2018-08-23 08:00:00 catalina-exec-3530  [INFO ]:aom.evp.papi.common.NotificationObjectUtil.getDTO(NotificationObjectUtil.java:68) - 解密后请求参数：{\"OperatorID\":\"313744932\",\"OperatorSecret\":\"1234567890abcdef\"}\n");
        logs.add("2018-08-23 08:35:37 catalina-exec-3526  [INFO ]:aom.evp.papi.common.NotificationObjectUtil.getMap(NotificationObjectUtil.java:131) - 解密后请求参数：{\"ConnectorStatusInfo\":{\"ConnectorID\":\"11000000000000090000341000\",\"LockStatus\":0,\"ParkStatus\":50,\"Status\":3}}\n");
        logs.add("2018-08-23 08:35:37 catalina-exec-3526  [INFO ]:aom.evp.papi.modules.charge.util.ResultUtil.getResult(ResultUtil.java:29) - 调用成功，服务名：NotifyConnectorStatusInfoService.pushConnectorStatusInfo, 返回结果：{\"re\":{\"status\":0},\"status\":0,\"success\":true}\n");
        logs.add("2018-08-23 08:35:37 catalina-exec-3526  [INFO ]:aom.evp.papi.modules.charge.web.interceptor.AuthorizationInterceptor.postHandle(AuthorizationInterceptor.java:90) - 请求耗时34ms\n");
        logs.add("2018-08-23 09:44:42 catalina-exec-3526  [ERROR]:com.evp.papi.modules.charge.web.exception.WebExceptionHandler.resolveException(WebExceptionHandler.java:57) - 153498869634738881846    请求的业务参数不合法，各接口定义自己的必须参数\n" +
                "com.evp.papi.modules.charge.web.exception.BusinessParamException: 请求的业务参数不合法，各接口定义自己的必须参数\n" +
                "\tat com.evp.papi.common.RequestContext.getDTO(RequestContext.java:118)\n" +
                "\tat com.evp.papi.modules.charge.web.controller.ConnectorStatusInfoNotificationController.notifyStationStatus(ConnectorStatusInfoNotificationController.java:42)\n" +
                "\tat sun.reflect.GeneratedMethodAccessor135.invoke(Unknown Source)\n" +
                "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n" +
                "\tat java.lang.reflect.Method.invoke(Method.java:606)\n" +
                "\tat org.springframework.web.method.support.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:219)\n" +
                "\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:132)\n" +
                "\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:103)\n" +
                "\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:604)\n" +
                "\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:565)\n" +
                "\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:80)\n" +
                "\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:923)\n" +
                "\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:852)\n" +
                "\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:882)\n" +
                "\tat org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:789)\n" +
                "\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:648)\n" +
                "\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:729)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:230)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)\n" +
                "\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:52)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)\n" +
                "\tat com.evp.papi.modules.charge.web.filter.RequestBodyReaderFilter.doFilter(RequestBodyReaderFilter.java:36)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)\n" +
                "\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:88)\n" +
                "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:76)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:192)\n" +
                "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:165)\n" +
                "\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:198)\n" +
                "\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:108)\n" +
                "\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:522)\n" +
                "\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:140)\n" +
                "\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:79)\n" +
                "\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:87)\n" +
                "\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:355)\n" +
                "\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:1110)\n" +
                "\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)\n" +
                "\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:785)\n" +
                "\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1425)\n" +
                "\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1145)\n" +
                "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:615)\n" +
                "\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n" +
                "\tat java.lang.Thread.run(Thread.java:745)\n" +
                "Caused by: java.lang.Exception: 参数校验失败:充电设备接口编码不能为空\n" +
                "\tat com.evp.papi.modules.charge.util.ValidationUtil.validate(ValidationUtil.java:38)\n" +
                "\tat com.evp.papi.common.RequestContext.getDTO(RequestContext.java:116)\n" +
                "\t... 44 more");



        String timestamp = "";
        String api = "";
        String logLevel = "";
        for(String log : logs){
            Matcher matcherHead = patternHead.matcher(log);
            Matcher matcherThreadNumber = patternThreadNumber.matcher(log);
            String threadNumber = "";
            if(matcherThreadNumber.find()){
                threadNumber = matcherThreadNumber.group();
            }
            //匹配到日至头
            if(matcherHead.find()){
                //若已经有日志，则将其持久化
                if(map.containsKey(threadNumber)){
                    ChargePapiLog papiLog = new ChargePapiLog(logLevel, SIMPLE_DATE_FORMAT.parse(timestamp), map.get(threadNumber), api);
                    //输出代替持久化
                    System.out.println(papiLog);
                }
                //已经持久化的日志无用了，我们继续处理下一条日志
                map.put(threadNumber,log);
                //提取日期
                Matcher matcherTimestamp = patternTimestamp.matcher(log);
                if(matcherTimestamp.find()){
                  timestamp = matcherTimestamp.group();
                }
                //提取api
                String[] oldUrl = matcherHead.group().split("/");
                api = oldUrl[oldUrl.length-1];
                //提取日志级别
                Matcher matcherLogLevel = patternLogLevel.matcher(log);
                if(matcherLogLevel.find()){
                    logLevel = matcherLogLevel.group();
                }
            } else{
                if(!map.containsKey(threadNumber)){
                    //垃圾日志->丢弃
                    continue;
                }else{
                    String value = map.get(threadNumber);
                    value += log;
                    map.put(threadNumber,value);
                }
            }
        }
        getLastChargePapiLog(map);

    }

    private static void getLastChargePapiLog(Map<String,String> map) throws Exception{
        String timestamp = "";
        String api = "";
        String logLevel = "";
        for(String log : map.values()){
            //提取日期
            Matcher matcherTimestamp = patternTimestamp.matcher(log);
            if(matcherTimestamp.find()){
                timestamp = matcherTimestamp.group();
            }
            //提取api
            Matcher matcherHead = patternHead.matcher(log);
            if(matcherHead.find()){
                String[] oldUrl = matcherHead.group().split("/");
                api = oldUrl[oldUrl.length-1];
            }
            //提取日志级别
            Matcher matcherLogLevel = patternLogLevel.matcher(log);
            if(matcherLogLevel.find()){
                logLevel = matcherLogLevel.group();
            }
            ChargePapiLog papiLog = new ChargePapiLog(logLevel, SIMPLE_DATE_FORMAT.parse(timestamp), log, api);
            //输出代替持久化
            System.out.println(papiLog);
        }
    }

}
