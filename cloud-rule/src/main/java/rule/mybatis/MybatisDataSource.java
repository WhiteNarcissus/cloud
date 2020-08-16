package rule.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;



@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
@MapperScan("rule.dao")
public class MybatisDataSource {

//    private static final Logger logger = LoggerFactory.getLogger(MybatisDataSource.class);

    @Autowired
    private DataSourceProperties dataSourceProperties;

    //mybaits mapper xml搜索路径
    private final static String mapperLocations="classpath:/mapper/*.xml";

    private org.apache.tomcat.jdbc.pool.DataSource pool;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSourceProperties config = dataSourceProperties;
        this.pool = new org.apache.tomcat.jdbc.pool.DataSource();
        this.pool.setDriverClassName(config.getDriverClassName());
        this.pool.setUrl(config.getUrl());
        if (config.getUsername() != null) {
            this.pool.setUsername(config.getUsername());
        }
        if (config.getPassword() != null) {
            this.pool.setPassword(config.getPassword());
        }
        this.pool.setInitialSize(config.getInitialSize());
        this.pool.setMaxActive(config.getMaxActive());
        this.pool.setMaxIdle(config.getMaxIdle());
        this.pool.setMinIdle(config.getMinIdle());
        this.pool.setTestOnBorrow(config.isTestOnBorrow());
        this.pool.setTestOnReturn(config.isTestOnReturn());
        this.pool.setValidationQuery(config.getValidationQuery());
        this.pool.setMaxWait(3600000);

        return this.pool;
    }

    @PreDestroy
    public void close() {
        if (this.pool != null) {
            this.pool.close();
        }
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

//        PageHelper pageHelper = new PageHelper();
//        Properties properties = new Properties();
//        properties.setProperty("reasonable", "true");
//        properties.setProperty("supportMethodsArguments", "true");
//        properties.setProperty("returnPageInfo", "check");
//        properties.setProperty("params", "count=countSql");
//        pageHelper.setProperties(properties);
//
//        logger.info("init PageHelper Interceptor");
//        //添加插件
//        sqlSessionFactoryBean.setPlugins(new Interceptor[]{(Interceptor) pageHelper});

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
//        logger.info("init SqlSessionFactoryBean");
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
