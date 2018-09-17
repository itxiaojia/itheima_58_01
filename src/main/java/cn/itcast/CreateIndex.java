package cn.itcast;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

/**
 * @Author: Mr.jia
 * @Date: 2018/9/14 23:09
 * 创建索引库
 * 必须现有索引库数据 才能查询
 */
public class CreateIndex {

	@Test
	public void addIndex() throws Exception {
		//1,指定索引库的存储位置
		String path="F:\\ssm项目\\lucene\\预习";

		//2,创建目录对象,关联索引库存储位置
		FSDirectory directory = FSDirectory.open(new File(path));

		//3,创建分词器对象
		IKAnalyzer ikAnalyzer = new IKAnalyzer();

		//4,创建写索引库核心对象配置对象
		//指定lucene版本
		//指定分词器对象
		IndexWriterConfig iwWriterConfig = new IndexWriterConfig(Version.LUCENE_4_10_3,ikAnalyzer);

		//5,创建索引库的核心对象
		IndexWriter indexWriter = new IndexWriter(directory,iwWriterConfig);

		//模拟文档对象数据
		//doc(id，title,desc,contnet) 类似javabean对象
		//1,网页 抽象设计： id,title,desc,content,url
		//2,文件 抽象设计：id,title,desc,content,url
		//3,数据库数据: id,title,desc,content
		Document doc = new Document();
		doc.add(new StringField("id","10086", Field.Store.NO));
		doc.add(new TextField("title","Lucene并不是现成的搜索引擎产品，但可以用来制作搜索引擎产品,黄晓明在传智播客学习java",Field.Store.YES));
		doc.add(new TextField("desc","全文检索系统是按照全文检索理论建立起来的用于提供全文检索服务的软件系统",Field.Store.YES));
		doc.add(new TextField("content","搜索引擎是全文检索技术最主要的一个应用，例如百度。搜索引擎起源于传统的信息全文检索理论",Field.Store.NO));

		//6,写索引库
		indexWriter.addDocument(doc);
		//7,提交
		indexWriter.commit();
	}
}
