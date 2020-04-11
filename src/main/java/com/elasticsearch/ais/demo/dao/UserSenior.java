package com.elasticsearch.ais.demo.dao;


import com.elasticsearch.ais.annotation.Elasticsearch;
import com.elasticsearch.ais.demo.entity.User;
import java.util.List;

/**
 * json:UserSenior.json
 *
 * @author lihang
 * @email 631533483@qq.com
 */

@Elasticsearch
public interface UserSenior {

  List<User> findSenior(User user);

  List<User> findSeniorTest(User user);
}
