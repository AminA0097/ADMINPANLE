package com.freq.arvand.arvand.Base;

import com.freq.arvand.arvand.userSession.UserSession;
import com.freq.arvand.arvand.utils.SearchRes;

public interface BaseInterface {
    BaseEntity findById(Long id) throws Exception;
    SearchRes search(String filter,
                     int pageNo, int pageSize, String order, String sort, UserSession userSession) throws Exception;
}
