package com.jolo.cms.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jolo.basic.dao.BaseDao;
import com.jolo.basic.model.Pager;
import com.jolo.cms.model.Group;
@Repository("groupDao")
public class GroupDao extends BaseDao<Group> implements IGroupDao {


}
