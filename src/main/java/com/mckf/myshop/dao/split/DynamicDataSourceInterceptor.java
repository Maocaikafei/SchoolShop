package com.mckf.myshop.dao.split;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * @program: MyShop
 * @description:
 * @author: 冒菜咖啡~
 * @create: 2020-06-14 21:32
 **/
//mybatis会将增删改的操作封装在update中，因此只需写update，query代表查询
@Intercepts({@Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class}),
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {
    private static Logger logger= LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);
    //正则表达式 u0020表示空格
    private static final String REGEX=".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    /**
    * @Description:主要的拦截方法，设置在什么情况下去拦截
    * @Param: [invocation]
    * @return: java.lang.Object
    * @Author: 冒菜咖啡
    * @Date: 2020/6/14 21:36
    */
    public Object intercept(Invocation invocation) throws Throwable {
        //判断调用拦截器的这个方法是否是用事务进行管理的，如果是则返回true
        boolean synchronizationActive= TransactionSynchronizationManager.isActualTransactionActive();
        //用于获取传过来的SQL语句中的参数
        Object[] objects = invocation.getArgs();
        //获取第一个参数，一般第一个参数都是用于指定操作类型的
        MappedStatement statement = (MappedStatement) objects[0];
        //用于指定DataSource类型，默认为master，比较安全
        String lookupKey=DynamicDataSourceHolder.DB_MASTER;;

        //如果拦截的这个方法不是用事务管理的
        if (synchronizationActive==false) {
            //这里第一层只判断是否为Select，因为其余的都只能使用master了，无需判断
            //Select内部还需要通过正则表达式进行判断，因为可能存在嵌套的情况，
            // 比如insert into xxx select xx from xxx，这时候要走主库而不是从库
            if (statement.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                //如果是自增id查询主键方法，需要使用主库，因为这实际上一个写操作，比如插入
                // 一条数据，在插入之前要先执行SELECT LAST_INSERT_ID()
                if (statement.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    lookupKey = DynamicDataSourceHolder.DB_MASTER;
                } else {
                    BoundSql boundSql = statement.getSqlSource().getBoundSql(objects[1]);
                    //将所有的制表符，换行符，回车替换为空格，因为SQL里可能有很多句，所以要用空格隔开
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                    if (sql.matches(REGEX)) {
                        lookupKey = DynamicDataSourceHolder.DB_MASTER;
                    } else {
                        lookupKey = DynamicDataSourceHolder.DB_SLAVE;
                    }
                }
            }
        }else {
            //如果拦截的这个方法是用事务管理的，一般都是写操作，直接设为主库
            lookupKey = DynamicDataSourceHolder.DB_MASTER;
        }
        logger.debug("设置方法[{}] use [{}] Strategy SqlCommandType [{}]",
                statement.getId(),lookupKey,statement.getSqlCommandType().name());
        //最终确定使用哪个数据源
        DynamicDataSourceHolder.setDbType(lookupKey);
        return invocation.proceed();
    }

    /**
    * @Description:决定返回一个封装过的对象还是原对象
    * @Param: [target]
    * @return: java.lang.Object
    * @Author: 冒菜咖啡
    * @Date: 2020/6/14 21:48
    */
    public Object plugin(Object target) {
        //instanceof用于判断该对象是否为这个类的实例
        //如果target是一个Executor对象，就拦截下来，返回一个经过intercept包装的对象，否则返回原Target
        //拦截Executor的原因是在Mybatis中，Executor对象是用来执行增删改查的操作的，我们将其拦截下来
        //通过intercept去决定使用哪个数据源
        if(target instanceof Executor){
            return Plugin.wrap(target,this);
        }else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
