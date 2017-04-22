package com.backend.springboot.template.mongodb.repositories.complex;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import com.backend.springboot.template.mongodb.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.backend.springboot.template.constants.ConstantsUtils;
import com.backend.springboot.template.mongodb.utils.QueryUtils;

@Component
public class UserTemplate {

	private final MongoTemplate mongoTemplate;

    @Autowired
    public UserTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * Looking for user by keyworkds
     * @param keyWords, the string to look for
     * @param asc, true for asc, false for desc
     * @return a list of user
     */
    public List<User> searchUserByKeyWords(List<String> keyWords, boolean asc){
    	Criteria criteriaSite = null;
		Pattern regex = null;
    	if(keyWords != null && keyWords.size() > 0){
    		regex = QueryUtils.convertToRegex(keyWords);
    	}

    	if(regex != null){
    		criteriaSite = new Criteria().orOperator(where(ConstantsUtils.USER_USER_NAME).regex(regex));
    	}
		if (criteriaSite != null) {
			Query query = new Query(criteriaSite);
			if (asc) {
				query.with(new Sort(Sort.Direction.ASC, ConstantsUtils.USER_USER_NAME));
			} else {
				query.with(new Sort(Sort.Direction.DESC, ConstantsUtils.USER_USER_NAME));
			}
			return mongoTemplate.find(query, User.class);
		} else {
			return Collections.emptyList();
		}
    }
}
