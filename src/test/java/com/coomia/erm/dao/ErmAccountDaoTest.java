package com.coomia.erm.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ErmAccountDaoTest {


    @Autowired ErmAccountDao ermAccountDao;
    @Test
    public  void test(){
        Map<String,Object> params=new HashMap<>();
//        params.put("value",1);
        params.put("offset","0");
        params.put("limit","3");
        System.out.println(ermAccountDao.queryList(params));

    }


}