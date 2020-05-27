package practice.interfaces;

/**
 * 实际项目中通过拦截器或者切面来实现：
 * 1、定义一个interface，命名为BaseCheck，BaseCheck里面有一个抽象的check方法，check方法返回值是boolean。
 *
 * 2、定义校验的注解，比如：@NotNull、@Length。
 *
 * 3、根据上面的注解，分别定义对应的校验类，比如：NotNullCheck、LengthCheck。
 *
 * 4、NotNullCheck、LengthCheck都需要实现BaseCheck的check方法，你要在check方法里面写校验流程。
 *
 * 5、定义一个容器，在工程启动的时候，把NotNullCheck和LengthCheck的对象塞到里面，
 *
 * 如果你使用spring，直接在NotNullCheck和LengthCheck上面加个注解@Component，也能达到同样的效果。
 *
 * 6、定义一个拦截器或者切面。
 *
 * 7、在这个拦截器或者切面里，拿到请求的参数，也就是那个user对象。
 *
 * 8、通过反射，获取到这个user对象所对应的类，类的名字肯定就是User了。
 *
 * 9、遍历User里面的所有Field，检查每一个Field是否含有注解。
 *
 * 10、遍历Field上的所有注解。
 *
 * 11、假设找到一个注解@NotNull，就去找一下对应的校验类，
 *
 * BaseCheck baseCheck = 容器.get("NotNullCheck")
 *
 * 或者BaseCheck baseCheck = (BaseCheck) SpringUtl.getBean("NotNullCheck")
 *
 * 12、如果找到，也就是baseCheck不为空，则通过反射获取这个Field的实际值，将这个值作为参数，调用baseCheck.check方法
 *
 * 13、baseCheck.check如果返回false则报错，如果返回true则继续，直到遍历完所有Field、所有注解
 */
public interface BaseCheck {
    boolean check(Object obj);
}
