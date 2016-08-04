package com.jo.demo.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

public class SolrJExample {
  public static void main(String[] args) throws SolrServerException, IOException {
	  //Single node Solr client
	  String urlString = "http://localhost:8983/solr/techproducts";
	  SolrClient solr = new HttpSolrClient.Builder(urlString).build();
	  
	  //SolrCloud client
//	  String zkHostString = "zkServerA:2181,zkServerB:2181,zkServerC:2181/solr";
//	  SolrClient solr = new CloudSolrClient.Builder().withZkHost(zkHostString).build();
	  //If you are trying to mix Solr and SolrJ versions where one is version 1.x and the other is 3.x or later, then you MUST use the XML response parser.
//	  ((HttpSolrClient) solr).setParser(new XMLResponseParser());
	  
	  SolrQuery query = new SolrQuery();
	  String mQueryString = "a";
	  query.setQuery(mQueryString);
	  
	  query.setRequestHandler("/spellCheckCompRH");
	  
	  //You can also set arbitrary parameters on the query object. The first two code lines below are equivalent to each
	  //other, and the third shows how to use an arbitrary parameter q to set the query string:
	  query.set("fl", "category,title,price");
	  query.setFields("category", "title", "price");
	  query.set("q", "category:books");
	  
	  
	  QueryResponse response = solr.query(query);
	  SolrDocumentList list = response.getResults();
	  
	  
	  
	  SolrInputDocument document = new SolrInputDocument();
	  document.addField("id", "552199");
	  document.addField("name", "Gouda cheese wheel");
	  document.addField("price", "49.99");
	  UpdateResponse uresponse = solr.add(document);
	  // Remember to commit your changes!
	  solr.commit();
  }
  
  
}