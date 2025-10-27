package com.freq.arvand.arvand.userSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freq.arvand.arvand.User.UsersSimple;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class UserSessionImpl{
    private final static Cache<String, UserSession> cachePrincipal =
            CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.MINUTES).build();
    private final static Cache<String, Authentication> cacheAuthentication =
            CacheBuilder.newBuilder().expireAfterAccess(15, TimeUnit.MINUTES).build();
    private final static ConcurrentHashMap<String,String> CONCURRENTHASHMAP = new ConcurrentHashMap<>();

    public static Authentication getAuthentication(String userName) {
        Authentication authentication = cacheAuthentication.getIfPresent(userName);
        if(authentication!= null) return authentication;
        return null;
    }

    public static void addAuthentication(String username, Authentication authentication) {
        Authentication _authentication = getAuthentication(username);
        if(_authentication != null)return;
        cacheAuthentication.put(username, authentication);
    }

    public void createSession(UsersSimple usersSimple) throws Exception {
        String sessionId = UUID.randomUUID().toString();
        UserSession userSession = new UserSession(sessionId,usersSimple);
        cachePrincipal.put(sessionId, userSession);
        CONCURRENTHASHMAP.put(sessionId,usersSimple.getUserName());
    }

    public UserSession getSession() throws Exception{
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        UserSession userSession = cachePrincipal.getIfPresent(userName);
        if(userSession != null) {
            return userSession;
        }
        else {
            throw new Exception("Session Expired!");
        }
    }

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public  String dumpAllCaches() {
        try {
            Map<String, Object> result = new LinkedHashMap<>();

            // --- UserSessionSimple ---
            List<Object> userSessions = new ArrayList<>();
            cachePrincipal.asMap().forEach((k, v) -> userSessions.add(v));
            result.put("userSession", userSessions);

            // --- Authentication ---
            List<Object> authentications = new ArrayList<>();
            cacheAuthentication.asMap().forEach((k, v) -> authentications.add(v));
            result.put("authentication", authentications);

            // --- ConcurrentHashMap ---
            List<Map<String,String>> concList = new ArrayList<>();
            CONCURRENTHASHMAP.forEach((k,v) -> {
                Map<String,String> map = new HashMap<>();
                map.put(k, v);
                concList.add(map);
            });
            result.put("concurrentMap", concList);

            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);

        } catch (Exception e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
