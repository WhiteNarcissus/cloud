package common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware{
    /**
     * 上下文对象实例
     * @param applicationContext
     * @throws BeansException
     */
    private static ApplicationContext applicationContext=null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(null==applicationContext){
            this.setApplicationContext(applicationContext);
        }
    }
    /***
     * 获取applicationContext
     * @return
     */
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    /***
     * 通过name 获取 Bean
     * @param name
     * @return
     */
    public static Object getbean(String name){
        return getApplicationContext().getBean(name);
    }

    /***
     * 通过name 获取 Bean
     * @param clazz
     * @return
     */
    public static <T> T getbean(Class<T>  clazz){
        return getApplicationContext().getBean(clazz);
    }


    /****
     * 通过name 以及clazz返回指定的
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getbean(String name,Class<T>  clazz){
        return getApplicationContext().getBean(name,clazz);
    }


}
