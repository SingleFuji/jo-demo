package com.jo.demo.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.junit.Test;

public class SolrTest {

	@Test
	public void testQuery() {
		  String zkHostString = "zkServerA:2181,zkServerB:2181,zkServerC:2181/solr";
		  SolrClient solr = new CloudSolrClient.Builder().withZkHost(zkHostString).build();
	}

}
