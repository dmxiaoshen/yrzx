package com.lljz.yrzx.test.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp") 
@ContextConfiguration(locations={"classpath*:spring-context*.xml"})
@TransactionConfiguration(transactionManager="transactionManager",defaultRollback=true)
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests{

}