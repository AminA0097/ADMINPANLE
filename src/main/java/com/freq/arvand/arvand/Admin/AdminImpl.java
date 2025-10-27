package com.freq.arvand.arvand.Admin;

import com.freq.arvand.arvand.Base.BaseImpl;
import com.freq.arvand.arvand.Base.BaseSimple;
import com.freq.arvand.arvand.User.UsersSimple;
import com.freq.arvand.arvand.form.AdminForm;
import com.freq.arvand.arvand.form.LoginForm;
import com.freq.arvand.arvand.userSession.UserActions;
import com.freq.arvand.arvand.userSession.UserSession;
import com.freq.arvand.arvand.userSession.UserSessionImpl;
import com.freq.arvand.arvand.utils.SearchRes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AdminImpl extends BaseImpl implements AdminInterface{
    @Autowired
    private AuthenticationManager authenticationManager;

    @PersistenceContext
    private EntityManager entityManager;
    private UserSessionImpl userSession;
    private AdminInterface adminInterface;
    public AdminImpl(UserSessionImpl userSession, AdminInterface adminInterface) {
        this.userSession = userSession;
        this.adminInterface = adminInterface;
    }

    @Override
//    public List<Map<String, String>> fillDashboard() throws Exception{
    public List<Map<String, String>> fillDashboard(UserSession userSession) throws Exception{
        List mainList = new ArrayList();
        StringBuilder userCount = new StringBuilder().append("select count(*) from CORES_USERS u where u.fld_deleted = 0");
        List _res = entityManager.createNativeQuery(userCount.toString()).getResultList();
        if(_res.size() > 0){
            Map<String,String> userCountMap = new HashMap<String,String>();
            userCountMap.put("value",_res.get(0).toString());
            userCountMap.put("title","User Count");
            userCountMap.put("desc","All Of Users");
            mainList.add(userCountMap);
        }
        
        return mainList;
    }

    @Override
    public String loginAdmin(AdminForm loginForm) throws Exception {

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginForm.getUserName(),
                            loginForm.getPassword()
                    )
            );}
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password");
        }catch (LockedException e){
            throw new Exception("Contact With Administrator");
        }
        SearchRes simples = adminInterface.search(
                " e.userName = '" + loginForm.getUserName() + "' and e.isSysAdmin = true",
                1,1,null,null, UserSession.getSysSession()
        );
        if(simples.getRes() == null || simples.getCount() == 0){throw new Exception("msg: "+loginForm.getUserName()+" not found");}
        BaseSimple user =  simples.getRes().iterator().next();
        userSession.createSession((UsersSimple) user);
        return ((UsersSimple) user).getUserName();
    }
}
