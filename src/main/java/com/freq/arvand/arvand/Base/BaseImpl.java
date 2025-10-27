package com.freq.arvand.arvand.Base;

import com.freq.arvand.arvand.userSession.UserSession;
import com.freq.arvand.arvand.utils.ClsFounder;
import com.freq.arvand.arvand.utils.JqlTool;
import com.freq.arvand.arvand.utils.SearchRes;
import com.freq.arvand.arvand.utils.SpringContextHelper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class BaseImpl implements BaseInterface{
    private final static String SEARCH_ACTION = "1";
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public BaseEntity findById(Long id) throws Exception {
        Class<?> entityClassName = ClsFounder.getClass(this.getClass(), "Entity");
        StringBuffer query = new StringBuffer("select e from "+entityClassName.getSimpleName()+" e");
        query.append(" where e.id = ").append(id);
        Query queryObject = entityManager.createQuery(query.toString());
        List<BaseEntity> baseEntities = queryObject.getResultList();
        if(baseEntities.size() == 0){
            throw new Exception("msg:"+"This Id Not Found");
        }
        return baseEntities.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SearchRes search(String filter,
                            int pageNo, int pageSize, String order, String sort, UserSession userSession) throws Exception {
        Class<?> entityClassName = ClsFounder.getClass(this.getClass(), "Entity");
        userSession.getUserAction().hasAction(entityClassName.getSimpleName(),SEARCH_ACTION);
        BaseSimple baseSimple = (BaseSimple) SpringContextHelper.getContext()
                .getBean(ClsFounder.getClass(this.getClass(),"Simple"));
        JqlTool.checkFilter(filter,baseSimple.getFields());
        pageSize = pageSize > 25 ? pageSize : 10;
        order = order == null ? "no_order" : order;
        SearchRes searchRes = new SearchRes();
        StringBuffer jpql = new StringBuffer();
        jpql.append(baseSimple.getSimpleQuery(""));
        if(filter != null && !filter.isEmpty()){
            if(jpql.indexOf("where") == -1){
                jpql.append(" where (").append(JqlTool.addFilter(filter.toString())).append(")");
            }
            else {
                jpql.append(" and ").append(JqlTool.addFilter(filter.toString()));
            }
        }
        JqlTool.addDelWhere(jpql);
//        Map<String,Object> param = JqlTool.createQuery(jpql,filter,order);
        Query queryObject = entityManager.createQuery(jpql.toString());
        queryObject.setFirstResult((pageNo - 1) * pageSize);
        queryObject.setMaxResults(pageSize);
        searchRes.setRes(queryObject.getResultList());
        String countJql;
        String baseQuery = jpql.toString();
        int orderIndex = jpql.indexOf("order by");
        if (orderIndex > -1) {
            baseQuery = baseQuery.substring(0, orderIndex);
        }

        int countQuery = JqlTool.findparenthrse(jpql.toString().toCharArray(),jpql.indexOf("("));
        if (baseQuery.toLowerCase().contains("group by")) {
            countJql = "select count(*) " + baseQuery.substring(countQuery + 1, baseQuery.toLowerCase().indexOf("group by"));
        } else {
            countJql = "select count(*) " + baseQuery.substring(countQuery + 1);
        }
        Query countQ = entityManager.createQuery(countJql);
        Long count = (Long) countQ.getSingleResult();
        searchRes.setCount(count.intValue());
        return searchRes;
    }
}
