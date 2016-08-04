package com.jo.demo.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.Field;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.NoOpResponseParser;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.util.NamedList;
import org.junit.Test;

import junit.framework.TestCase;

public class SolrClientTest extends TestCase {


    @Test
    public void testAddData() throws SolrServerException, IOException {
        CloudSolrClient solrClient = new CloudSolrClient("172.20.6.21:2181");
        solrClient.setParser(new NoOpResponseParser("json"));
        UpdateRequest request = new UpdateRequest();
        SolrInputDocument document = new SolrInputDocument();
        // document.addField("F_PK", "127");
        // document.addField("reconDate", "20160322");
        // document.addField("tradeTypeCode", "0001");
        // document.addField("channelCode", "15");
        // document.addField("acquInstCode", "48495800");
        // document.addField("retrievalRefNo", "001245657");
        // document.addField("sysTraceAuditNo", "12345678");
        // document.addField("pan", "46586215421547821");
        // document.addField("tradeAmt", 99);
        // request.add(document);
        // document = new SolrInputDocument();
        // document.addField("F_PK", "128");
        // document.addField("reconDate", "20160322");
        // document.addField("tradeTypeCode", "0001");
        // document.addField("channelCode", "15");
        // document.addField("acquInstCode", "48495800");
        // document.addField("retrievalRefNo", "001245657");
        // document.addField("sysTraceAuditNo", "12345678");
        // document.addField("pan", "46586215421547821");
        // document.addField("tradeAmt", 88);
        // request.add(document, true);

        List<ValueBean> valueBeans = new ArrayList<ValueBean>();
        ValueBean bean = new ValueBean();
        bean.setId("130");
        bean.setReconDate("20160323");
        bean.setTradeTypeCode("0001");
        bean.setChannelCode("15");
        bean.setAcquInstCode("48495800");
        bean.setRetrievalRefNo("0025689714");
        bean.setSysTraceAuditNo("12345689");
        bean.setPan("12345689714235221");
        bean.setTradeAmt(1011);
        valueBeans.add(bean);
        // solrClient.addBean("test_core", bean);
        System.out.println(solrClient.addBeans("test_core", valueBeans));
        solrClient.commit("test_core");

        // 更新字段
        // document = new SolrInputDocument();
        // document.addField("F_PK", "130");
        // Map<String, Object> updateField = new HashMap<String, Object>();
        // updateField.put("set", 99);
        // document.addField("tradeAmt", updateField);
        // request = new UpdateRequest();
        // request.add(document);
        // NamedList<Object> rsp = solrClient.request(request, "test_core");
        // System.out.println(rsp);
        // solrClient.commit("test_core");

        // 删除字段
        System.out.println(solrClient.deleteById("test_core", "change.me"));
        solrClient.commit("test_core");


        solrClient.close();
    }

    @Test
    public void testQueryData() throws SolrServerException, IOException {
        CloudSolrClient solrClient = new CloudSolrClient("172.20.6.21:2181");
        solrClient.setParser(new NoOpResponseParser("json")); // 设置响应格式为json
        // 查询条件AND
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set(CommonParams.Q, "F_PK:128 AND pan:46586215421547821");
        solrQuery.setFields("F_PK", "pan", "tradeAmt"); // 过滤字段
        QueryRequest queryRequest = new QueryRequest(solrQuery);
        NamedList<Object> namedList = solrClient.request(queryRequest, "test_core");
        System.out.println(namedList);

        // 查询条件或
        solrQuery = new SolrQuery();
        solrQuery.set(CommonParams.Q, "F_PK:128 OR pan:46586215421547821");
        solrQuery.setFields("F_PK", "pan", "tradeAmt"); // 过滤字段
        queryRequest = new QueryRequest(solrQuery);
        namedList = solrClient.request(queryRequest, "test_core");
        System.out.println(namedList);

        // 排序
        solrQuery = new SolrQuery();
        solrQuery.set(CommonParams.Q, "pan:46586215421547821");
        solrQuery.set(CommonParams.SORT, "F_PK desc");
        queryRequest = new QueryRequest(solrQuery);
        namedList = solrClient.request(queryRequest, "test_core");
        System.out.println(namedList);

        // 分页
        solrQuery = new SolrQuery();
        solrQuery.set(CommonParams.Q, "pan:46586215421547821");
        solrQuery.set(CommonParams.START, "0");
        solrQuery.set(CommonParams.ROWS, "2");
        solrQuery.set(CommonParams.SORT, "F_PK desc");
        queryRequest = new QueryRequest(solrQuery);
        namedList = solrClient.request(queryRequest, "test_core");
        System.out.println(namedList);

        // count函数
        solrQuery = new SolrQuery();
        solrQuery.setParam(CommonParams.Q, "*:*");
        solrQuery.setParam("facet", "true");
        solrQuery.add("facet.field", "pan");
        solrQuery.setParam("facet.prefix", "123");// 包含123
        queryRequest = new QueryRequest(solrQuery);
        namedList = solrClient.request(queryRequest, "test_core");
        System.out.println(namedList);


        // 2个字段count函数
        solrQuery = new SolrQuery();
        solrQuery.setParam(CommonParams.Q, "*:*");
        solrQuery.setParam("facet", "true");
        solrQuery.add("facet.field", "pan");
        solrQuery.add("facet.field", "tradeAmt");
        queryRequest = new QueryRequest(solrQuery);
        namedList = solrClient.request(queryRequest, "test_core");
        System.out.println(namedList);

        // 统计
        solrQuery = new SolrQuery();
        solrQuery.setParam(CommonParams.Q, "*:*");
        solrQuery.setParam("stats", "true");
        solrQuery.add("stats.field", "tradeAmt");
        queryRequest = new QueryRequest(solrQuery);
        namedList = solrClient.request(queryRequest, "test_core");
        System.out.println(namedList);

        // 范围查询
        solrQuery = new SolrQuery();
        solrQuery.setParam(CommonParams.Q, "*:*");
        solrQuery.add(CommonParams.FQ, "{!frange l=20160322 u=20160323} reconDate");
        solrQuery.add(CommonParams.FQ, "{!frange l=20160322 u=20160322} reconDate");
        queryRequest = new QueryRequest(solrQuery);
        namedList = solrClient.request(queryRequest, "test_core");
        System.out.println(namedList);
        solrClient.close();
    }

    static class ValueBean {

        @Field("F_PK")
        private String id;

        @Field("reconDate")
        private String reconDate;

        @Field("tradeTypeCode")
        private String tradeTypeCode;

        @Field("channelCode")
        private String channelCode;

        @Field("acquInstCode")
        private String acquInstCode;

        @Field("retrievalRefNo")
        private String retrievalRefNo;

        @Field("sysTraceAuditNo")
        private String sysTraceAuditNo;

        @Field("pan")
        private String pan;

        @Field("tradeAmt")
        private long tradeAmt;

        /**
         * @return the id
         */
        public String getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(String id) {
            this.id = id;
        }

        /**
         * @return the reconDate
         */
        public String getReconDate() {
            return reconDate;
        }

        /**
         * @param reconDate the reconDate to set
         */
        public void setReconDate(String reconDate) {
            this.reconDate = reconDate;
        }

        /**
         * @return the tradeTypeCode
         */
        public String getTradeTypeCode() {
            return tradeTypeCode;
        }

        /**
         * @param tradeTypeCode the tradeTypeCode to set
         */
        public void setTradeTypeCode(String tradeTypeCode) {
            this.tradeTypeCode = tradeTypeCode;
        }

        /**
         * @return the channelCode
         */
        public String getChannelCode() {
            return channelCode;
        }

        /**
         * @param channelCode the channelCode to set
         */
        public void setChannelCode(String channelCode) {
            this.channelCode = channelCode;
        }

        /**
         * @return the acquInstCode
         */
        public String getAcquInstCode() {
            return acquInstCode;
        }

        /**
         * @param acquInstCode the acquInstCode to set
         */
        public void setAcquInstCode(String acquInstCode) {
            this.acquInstCode = acquInstCode;
        }

        /**
         * @return the retrievalRefNo
         */
        public String getRetrievalRefNo() {
            return retrievalRefNo;
        }

        /**
         * @param retrievalRefNo the retrievalRefNo to set
         */
        public void setRetrievalRefNo(String retrievalRefNo) {
            this.retrievalRefNo = retrievalRefNo;
        }

        /**
         * @return the sysTraceAuditNo
         */
        public String getSysTraceAuditNo() {
            return sysTraceAuditNo;
        }

        /**
         * @param sysTraceAuditNo the sysTraceAuditNo to set
         */
        public void setSysTraceAuditNo(String sysTraceAuditNo) {
            this.sysTraceAuditNo = sysTraceAuditNo;
        }

        /**
         * @return the pan
         */
        public String getPan() {
            return pan;
        }

        /**
         * @param pan the pan to set
         */
        public void setPan(String pan) {
            this.pan = pan;
        }

        /**
         * @return the tradeAmt
         */
        public long getTradeAmt() {
            return tradeAmt;
        }

        /**
         * @param tradeAmt the tradeAmt to set
         */
        public void setTradeAmt(long tradeAmt) {
            this.tradeAmt = tradeAmt;
        }


    }
}
