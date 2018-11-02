package com.coomia.erm.conpoment;

import com.coomia.erm.service.ErmAdminService;
import com.coomia.erm.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangying
 * @Description:
 * @Date: Created in  2018/7/31
 */
@Component
@Order(value=1000000)
public class AccontConpoment implements CommandLineRunner
{
    @Autowired
    private ErmAdminService ermAdminService;
    @Override
    public void run(String... args) throws Exception
    {
        Map params=new HashMap();
        params.put("ebId", 1);
        Query query = new Query(params);
        ermAdminService.createSchoolAccount(query);
        System.out.println(">>>>This is MyStartupRunnerTest Order=1. Only testing CommandLineRunner...<<<<");
    }
}

