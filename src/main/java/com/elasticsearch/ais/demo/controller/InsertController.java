package com.elasticsearch.ais.demo.controller;

/*@author:lihang
 *@email:631533483@qq.com */


import com.elasticsearch.ais.Estemplate;
import com.elasticsearch.ais.demo.entity.User;
import com.elasticsearch.ais.demo.utils.Data;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.*;

/**
 * 这个类的主要作用是演示Estemplate的插入使用
 * The main purpose of this class is to demonstrate the insertion and use of estemplate
 *@author lihang
 *@email 631533483@qq.com
 */

@RestController
public class InsertController {


  @Autowired
  Estemplate estemplate;

  @RequestMapping("/insert1")
  public void insert1(List<User> list, String index) throws IOException {
    estemplate.insert(list, index);
  }


  @RequestMapping("/insert2")
  public void insert2(Map<Object, User> map, String index) throws IOException {
    estemplate.insert(map, index);
  }


  @RequestMapping("/insert3")
  public void insert3(String index, User user) throws IOException {
    estemplate.insert(user, index);
  }


  /*
   * 插入足够多的测试数据
   * */
  @RequestMapping("/insertTest")
  public List<User> insertTest() {
    List<User> list = new Data().getListUser();
    try {
      estemplate.insert(list, "demo");
    } catch (Exception e) {
      LoggerFactory.getLogger(InsertController.class).info("falied:" + e);
    }
    return list;
  }


}
