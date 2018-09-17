package cn.itcast;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/**
 * @Author: Mr.jia
 * @Date: 2018/9/16 15:49
 */
public class searchIndex {

	/**
	 * 查询索引库
	 */
	@Test
	public void queryIndex() throws Exception {
		//指定索引库存储位置
		String path="F:\\ssm项目\\lucene\\预习";
		//创建reader对象,读取索引库索引
		DirectoryReader directoryReader = DirectoryReader.open(FSDirectory.open(new File(path)));
		//创建搜索索引库核心对象
		IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

		//指定搜索关键字
		String qName="搜索引擎";
		//创建查询解析器，对象搜索关键词进行解析分词
		//参数1：指定Lucene版本
		//参数2：指定从哪个域字段中进行搜索
		//参数3：指定搜索时候使用哪个分词器进行查询关键词分词
		QueryParser queryParser = new QueryParser(Version.LUCENE_4_10_3, "title", new IKAnalyzer());

		//使用查询解析器对查询关键词进行分词
		//返回分词后包装类对象
		Query query = queryParser.parse(qName);


		//使用搜索核心对象查询索引库
		//返回值：文档概要信息
		//1,文档id
		//2,文档得分
		//3,文档命中总记录数
		//返回结果：返回匹配度最高的10条数据
		//什么叫做匹配度最高？ ------ 得分越高 ，匹配度越高
		TopDocs topDocs = indexSearcher.search(query, 10);

		//文档命中总记录数
		int totalHits=topDocs.totalHits;
		System.out.println("文档命中总记录数="+totalHits);
		//获取文档的分,文档id
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		//循环获取文档数组,文档id,文档得分
		for (ScoreDoc scoreDoc : scoreDocs) {
			//获取文档的分
			float score = scoreDoc.score;
			//获取文档id
			int docId = scoreDoc.doc;

			//根据文档id获取文旦
			Document doc = indexSearcher.doc(docId);
			//获取文档中值
			String id = doc.get("id");
			System.out.println("文档域id:"+id);

			//标题
			String title = doc.get("title");
			System.out.println("标题:"+title);

			//描述
			String desc = doc.get("desc");
			System.out.println("描述:"+desc);

			//内容
			String content = doc.get("content");
			System.out.println("内容："+content);
		}
	}
}
