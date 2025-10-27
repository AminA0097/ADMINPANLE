package com.freq.arvand.arvand.security;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        CustomUserDetail userDetail = (CustomUserDetail) loadUserDetailByUsername(s);
        return userDetail;
    }
    public UserDetails loadUserDetailByUsername(String s) throws UsernameNotFoundException {
        StringBuilder jql = new StringBuilder();
        jql.append("select u.userName,u.password,u.status.id from UsersEntity u where u.deleted = false and u.userName = :u");
        Query query = entityManager.createQuery(jql.toString()).setParameter("u", s);
        List res = query.getResultList();
        if(res.size() == 0) {throw new UsernameNotFoundException("User not found");}
        return new CustomUserDetail(((Object[]) res.get(0))[0].toString(),
                ((Object[]) res.get(0))[1].toString()
                , ((Object[]) res.get(0))[2].toString().equals("1") ? true : false,true,true,true);
    }
}
