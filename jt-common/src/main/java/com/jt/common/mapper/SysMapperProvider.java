package com.jt.common.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Table;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;

import com.github.abel533.mapper.MapperProvider;
import com.github.abel533.mapperhelper.EntityHelper;
import com.github.abel533.mapperhelper.MapperHelper;

public class SysMapperProvider extends MapperProvider {

    public SysMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public SqlNode deleteByIDS(MappedStatement ms) {
        Class<?> entityClass = getSelectReturnType(ms);
        Set<EntityHelper.EntityColumn> entityColumns = EntityHelper.getPKColumns(entityClass);
        EntityHelper.EntityColumn column = null;
        for (EntityHelper.EntityColumn entityColumn : entityColumns) {
            column = entityColumn;
            break;
        }
        
        List<SqlNode> sqlNodes = new ArrayList<SqlNode>();
        // 开始拼sql
        BEGIN();
        // delete from table
        DELETE_FROM(tableName(entityClass));
        // 得到sql
        String sql = SQL();
        // 静态SQL部分
        sqlNodes.add(new StaticTextSqlNode(sql + " WHERE " + column.getColumn() + " IN "));
        // 构造foreach sql
        SqlNode foreach = new ForEachSqlNode(ms.getConfiguration(), new StaticTextSqlNode("#{"
                + column.getProperty() + "}"), "ids", "index", column.getProperty(), "(", ")", ",");
        sqlNodes.add(foreach);
        return new MixedSqlNode(sqlNodes);
    }
    /**
     * 方法名称与接口方法名称一一对应
     * @return
     * 
     * 1.Mybatis最终将通用mapper解析为Sql语句.交给Mysql执行
     * 2.SqlNode 将接口方法最后转化为sql语句
     * 
     * 具体步骤:
     * 	1.获取全路径
     *  2.获取对象路径
     *  3.获取Class类型
     *  4.获取上级的接口
     *  5.获取泛型的类型
     *  6.获取泛型中的参数
     *  7.获取类型上的注解@Table
     *  8.获取注解中的name参数   ---表名
     * 
     */       
    public SqlNode findCount(MappedStatement ms){
    	try {
			//1.代码具体的方法路径     com.jt.manage.mapper.itemMapper.findCount()
    		String path = ms.getId(); 
    		//2.获取ItemMapper的路径    com.jt.manage.mapper.itemMapper
    		String classpath=path.substring(0, path.lastIndexOf("."));
    		//3.获取Class类型
    		Class<?> targetClass=Class.forName(classpath);
    		//4.获取上级    import java.lang.reflect.Type;  type是java中的超级接口
    		Type[] types=targetClass.getGenericInterfaces();   		
    		Type type=types[0];
    		//5先判断是否为泛型  之后获取泛型的类型      ParameterizedType泛型的类型
    		if(type instanceof ParameterizedType){
    		 ParameterizedType parameterizedType=(ParameterizedType) type;
    		//6.获取泛型的参数类型   <Item,XXX,XXX,XXX>
    		 Type[] argTypes=parameterizedType.getActualTypeArguments();
    		//获取Item类型
    		 Class<?> argType=(Class<?>)argTypes[0];
    		//7.获取注解   @Table(name="tb_item")  
    		 Table table =argType.getAnnotation(Table.class);
    		//获取表名   name="tb_item 
    		   String name=table.name();
    		 //编辑sql语句   注意末尾的空格
    		   String sql="select count(*) from "+name;
    		 //新建对象 返回sqlNode
       		  SqlNode sqlNode = new StaticTextSqlNode(sql);
       		
       		  return sqlNode;  
    		   
    		}
    		
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	return null;
    }
    
    
    

}
