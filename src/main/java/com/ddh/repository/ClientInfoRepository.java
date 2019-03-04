package com.ddh.repository;

import com.ddh.bean.ClientInfo;
import org.springframework.data.repository.CrudRepository;

public interface ClientInfoRepository extends CrudRepository<ClientInfo, String>{

    ClientInfo findClientByclientid(String clientId);

}
