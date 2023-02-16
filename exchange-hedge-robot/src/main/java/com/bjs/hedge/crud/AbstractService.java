package com.bjs.hedge.crud;

import com.alibaba.fastjson.util.TypeUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 *
 * @author wb-wj434262
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected Mapper<T> mapper;
    /**
     * 当前泛型真实类型的Class
     */
    private Class<T> modelClass;

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();
        modelClass = (Class<T>)pt.getActualTypeArguments()[0];
    }

    @Override
    public boolean save(T model) {
        return mapper.insertSelective(model) > 0;
    }

    @Override
    public boolean save(List<T> models) {
        if(CollectionUtils.isEmpty(models)){
            return false;
        }
        return mapper.insertList(models)>0;
    }

    @Override
    public boolean deleteById(Object id) {

        return mapper.deleteByPrimaryKey(id)>0;
    }

    @Override
    public boolean deleteByIds(String ids) {
        return mapper.deleteByIds(ids)> 0;
    }

    @Override
    public boolean update(T model) {
        return mapper.updateByPrimaryKeySelective(model)> 0;
    }

    @Override
    public T findById(Object id) {
        return mapper.selectByPrimaryKey(getPKValue(id));
    }

    @Override
    public T findBy(String fieldName, Object value) throws TooManyResultsException {
        try {
            T model = modelClass.newInstance();
            Field field = modelClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
            return mapper.selectOne(model);
        } catch (ReflectiveOperationException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> findByIds(String ids) {
        return mapper.selectByIds(ids);
    }

    @Override
    public List<T> findByCondition(Condition condition) {
        return mapper.selectByCondition(condition);
    }

    @Override
    public List<T> findAll() {
        return mapper.selectAll();
    }


    public Object getPKValue(Object id){
        Set<EntityColumn> pkColumns = EntityHelper.getPKColumns(modelClass);
        EntityColumn column = pkColumns.iterator().next();
        Class<?> javaType = column.getJavaType();
        if (javaType.isInstance(id)){
            return id;
        }else{
            return TypeUtils.castToJavaBean(id, javaType);
        }
    }

    @Override
    public List<T> select(T model) {
        return mapper.select(model);
    }

    @Override
    public T selectOne(T model) {
        return mapper.selectOne(model);
    }

}
