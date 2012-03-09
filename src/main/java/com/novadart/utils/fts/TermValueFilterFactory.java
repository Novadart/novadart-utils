package com.novadart.utils.fts;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.hibernate.search.annotations.Factory;
import org.hibernate.search.annotations.Key;
import org.hibernate.search.filter.FilterKey;
import org.hibernate.search.filter.StandardFilterKey;

public class TermValueFilterFactory {
	
	public static final String FIELD_NAME = "fieldName";
	
	public static final String FIELD_VALUE = "fieldValue";
	
	private String fieldName;
	
	private String fieldValue;

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Factory
	public Filter buildFilter(){
		Term term  = new Term(fieldName, fieldValue);
		BooleanClause countryCodeClause = new BooleanClause(new TermQuery(term), BooleanClause.Occur.MUST);
		BooleanQuery query = new BooleanQuery();
		query.add(countryCodeClause);
		return new QueryWrapperFilter(query);
	}
	
	@Key
	public FilterKey getKey(){
		StandardFilterKey key = new StandardFilterKey();
		key.addParameter(fieldName);
		key.addParameter(fieldValue);
		return key;
	}

}
