package com.elasticsearch.ais.demo.dao;


import com.elasticsearch.ais.annotation.Elasticsearch;
import com.elasticsearch.ais.demo.entity.User;
import java.util.List;


/**
 * JSON: UserJson.json.
 *
 * @author lihang
 * @email 631533483@qq.com
 */
@Elasticsearch
public interface UserJson {

  List<User> findName(String name);

  List<User> findIdName(String name, String id);
}
