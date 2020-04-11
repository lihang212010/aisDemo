package com.elasticsearch.ais.demo.controller;


import com.elasticsearch.ais.Estemplate;
import com.elasticsearch.ais.EstemplateCustom;
import com.elasticsearch.ais.demo.dao.UserJson;
import com.elasticsearch.ais.demo.dao.UserSenior;
import com.elasticsearch.ais.demo.entity.User;
import com.elasticsearch.ais.staticString.Find;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * 这个类的主要作用是演示Estemplate的查询模块使用方法. The main purpose of this class is to demonstrate how to use the
 * query module of estemplate
 *
 * @author lihang
 * @email 631533483@qq.com
 */
@RestController
public class FindController {


  @Autowired
  Estemplate estemplate;

  private String index = "demo";


  @Autowired
  EstemplateCustom estemplateCustom;

  @Autowired
  UserJson userJson;

  @Autowired
  UserSenior userSenior;

  @RequestMapping("/findName")
  public List<User> findName(String name) {
    return userJson.findName(name);
  }


  @RequestMapping("/findIdName")
  public List<User> findIdName(String name, String id) {
    return userJson.findIdName(name, id);
  }

  @RequestMapping("/findSenior")
  public List<User> findSenior(User user) {
    return userSenior.findSenior(user);
  }

  @RequestMapping("/findSeniorTest")
  public List<User> findSeniorTest(User user) {
    return userSenior.findSeniorTest(user);
  }

  @RequestMapping("/findCustom")
  public List<User> findCustom(User user)
      throws IllegalAccessException, NoSuchFieldException, IOException {
    String script = "GET demo/_search\n" +
        "{\n" +
        "  \"query\": {\n" +
        "    \"bool\": {\n" +
        "      \"must\": [\n" +
        "        {\n" +
        "          \"wildcard\": {\n" +
        "            \"name\": {\n" +
        "              \"value\": \"#{name}\"\n" +
        "            }\n" +
        "          }\n" +
        "        },\n" +
        "        {\n" +
        "          \"term\": {\n" +
        "            \"id\": {\n" +
        "              \"value\": \"#{id}\"\n" +
        "            }\n" +
        "          }\n" +
        "        }\n" +
        "      ],\n" +
        "      \"must_not\": [\n" +
        "        {\n" +
        "          \"range\": {\n" +
        "            \"age\": {\n" +
        "              \"gte\": #{age}\n" +
        "            }\n" +
        "          }\n" +
        "        }\n" +
        "      ]\n" +
        "    }\n" +
        "  },\n" +
        "  \"from\": \"#{pageFrom}\",\n" +
        "  \"size\": \"#{pageSize}\"\n" +
        "}";
    return estemplateCustom.excute(script, user);
  }

  /*
   * 这个查询为age在10和48之间,但不在22-24的，name首字母为李的，title中含有are，worker是Teacher或者Doctor的从0位开始到第20位
   * */
  @RequestMapping("find1")
  public List<User> find1() throws IOException {
    estemplate.range("age", 10, 48);
    estemplate.range("must_not", "age", 22, 24);
    estemplate.prefix("name", "李");
    estemplate.wildcard("title", "are");
    estemplate.terms("worker", "Teacher", "Doctor");
    estemplate.from(0);
    estemplate.size(20);
    return estemplate.execute(index, User.class);
  }

  /*
   * 姓名是张三或者liu的age大于20的
   * */
  @RequestMapping("find2")
  public List<User> find2() throws IOException {
    estemplate.script("doc['age']>20");
    estemplate.should(1, Find.term("name", "张三"), Find.term("name", "liu"));
    estemplate.from(0);
    estemplate.size(20);
    return estemplate.execute(index, User.class);
  }

  @RequestMapping("wildcard")
  public List<User> wildcard() throws IOException {
    estemplate.wildcard("name", "曹");
    return estemplate.execute(index, User.class);
  }

  @RequestMapping("term")
  public List<User> term() throws IOException {
    estemplate.term("name.keyword", "奚范");
    return estemplate.execute(index, User.class);
  }


  @RequestMapping("match")
  public List<User> match() throws IOException {
    estemplate.match("name", "奚范");
    return estemplate.execute(index, User.class);
  }

  @RequestMapping("terms")
  public List<User> terms() throws IOException {
    estemplate.terms("age", 25, 35, 10, 68);
    return estemplate.execute(index, User.class);
  }

  @RequestMapping("match_phrase")
  public List<User> match_phrase() throws IOException {
    estemplate.match_phrase("title", "We friends", 2);
    return estemplate.execute(index, User.class);
  }


  @RequestMapping("match_phrase_prefix")
  public List<User> match_phrase_prefix() throws IOException {
    estemplate.match_phrase_prefix("title", "We are good f", 10);
    return estemplate.execute(index, User.class);
  }

  @RequestMapping("common")
  public List<User> common() throws IOException {
    estemplate.common("title", "We are", 0.01);
    return estemplate.execute(index, User.class);
  }

  @RequestMapping("exits")
  public List<User> exits() throws IOException {
    estemplate.exits("title");
    return estemplate.execute(index, User.class);
  }


  @RequestMapping("fuzzy")
  public List<User> fuzzy() throws IOException {
    estemplate.fuzzy("name", "李松", 1);
    return estemplate.execute(index, User.class);
  }


  @RequestMapping("ids")
  public List<User> ids() throws IOException {
    estemplate.ids("09EprnABqEAuwq_e8YJH", "19EprnABqEAuwq_e8YJH");
    return estemplate.execute(index, User.class);
  }


  @RequestMapping("multi_match")
  public List<User> multi_match() throws IOException {
    estemplate.multi_match("are", "name", "title");
    return estemplate.execute(index, User.class);
  }

  @RequestMapping("more_like_this")
  public List<User> more_like_this() throws IOException {
    estemplate.more_like_this(1, 10, "are", "title");
    return estemplate.execute(index, User.class);
  }


  @RequestMapping("prefix")
  public List<User> prefix() throws IOException {
    estemplate.prefix("name", "李");
    return estemplate.execute(index, User.class);
  }


  @RequestMapping("query_string")
  public List<User> query_string() throws IOException {
    estemplate.query_string("name", "(孙李) OR (李周)");
    return estemplate.execute(index, User.class);
  }


  @RequestMapping("range")
  public List<User> range() throws IOException {
    estemplate.range("age", 10, 100);
    return estemplate.execute(index, User.class);
  }


  @RequestMapping("regexp")
  public List<User> regexp() throws IOException {
    estemplate.regexp("name", "孙.?");
    return estemplate.execute(index, User.class);
  }


  @RequestMapping("script")
  public List<User> script() throws IOException {
    estemplate.script("doc['age']>10");
    return estemplate.execute(index, User.class);
  }

  @RequestMapping("findFree")
  public List<User> findFree() throws IOException {
    String query = "{\n" +
        "    \"term\": {\n" +
        "      \"name.keyword\": {\n" +
        "        \"value\": \"奚范\"\n}" +
        "      }\n" +
        "    }";
    estemplate.findFree(query);
    return estemplate.execute(index, User.class);
  }


}
