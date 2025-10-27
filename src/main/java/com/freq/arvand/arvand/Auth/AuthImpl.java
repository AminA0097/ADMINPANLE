package com.freq.arvand.arvand.Auth;

import com.freq.arvand.arvand.Base.BaseSimple;
import com.freq.arvand.arvand.User.UserInterface;
import com.freq.arvand.arvand.User.UsersEntity;
import com.freq.arvand.arvand.User.UsersSimple;
import com.freq.arvand.arvand.form.LoginForm;
import com.freq.arvand.arvand.security.UserDetailService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthImpl implements AuthInterface {
    @PersistenceContext
    private EntityManager entityManager;
    private UserInterface userInterface;
    private PasswordEncoder passwordEncoder;
    private UserSessionImpl userSession;

    public AuthImpl(EntityManager entityManager, UserInterface userInterface, PasswordEncoder passwordEncoder, UserDetailService userDetailService, UserSessionImpl userSession) {
        this.entityManager = entityManager;
        this.userInterface = userInterface;
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
        this.userSession = userSession;
    }
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    UserDetailService userDetailService;

    public UsersEntity findUserByUserName(String userName)throws Exception{
        String q = "select e from UsersEntity e where e.userName=:u";
        List<UsersEntity> res = entityManager.createQuery(q).setParameter("u", userName).getResultList();
        if(res.size() == 0){return null;}
        return res.get(0);
    }
    @Override
    @Transactional
    public String Login(LoginForm loginForm) throws Exception {
        try{Authentication authentication = authenticationManager.authenticate(
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
        SearchRes simples = userInterface.search(
                " e.userName = '" + loginForm.getUserName() + "' and e.isSysAdmin = true",
                1,1,null,null, UserSession.getSysSession()
        );
        if(simples.getRes() == null || simples.getCount() == 0){throw new Exception("msg: "+loginForm.getUserName()+" not found");}
        BaseSimple user =  simples.getRes().iterator().next();
        userSession.createSession((UsersSimple) user);
        return ((UsersSimple) user).getUserName();
    }

}
