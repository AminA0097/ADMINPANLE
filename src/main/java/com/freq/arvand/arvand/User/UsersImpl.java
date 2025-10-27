package com.freq.arvand.arvand.User;

import com.freq.arvand.arvand.Base.BaseImpl;
import com.freq.arvand.arvand.userSession.UserSession;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersImpl extends BaseImpl implements UserInterface{

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Object[]> loadActions(Long userId) throws Exception {
        String sql = " SELECT u.fld_entity_name, u.fld_action_id FROM user_action u " +
                "WHERE u.fld_user_id = ? AND u.fld_deleted = 0 AND u.fld_status = 1";
        List<Object[]> res = entityManager
                .createNativeQuery(sql)
                .setParameter(1, userId)
                .getResultList();

        if (res.isEmpty()) {
            throw new Exception("No actions found for user ID " + userId);
        }

        return res;
    }


    @Override
    public String getCount(UserSession session) throws Exception {
//        session.getUserAction().getActions();
        return "";
    }
}
