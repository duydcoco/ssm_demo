//package com.ck.config.serviceconfig;
//
//import com.atomikos.icatch.config.UserTransactionService;
//import com.atomikos.icatch.config.UserTransactionServiceImp;
//import com.atomikos.icatch.jta.UserTransactionImp;
//import com.atomikos.icatch.jta.UserTransactionManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.DependsOn;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.transaction.jta.JtaTransactionManager;
//
//import javax.transaction.TransactionManager;
//import javax.transaction.UserTransaction;
//
//@Configuration
//@EnableTransactionManagement
//public class TransactionConfig {
//
//	@Bean
//	public PlatformTransactionManager transactionManager(
//			@Autowired TransactionManager atomikosTransactionManager,
//			@Autowired UserTransaction atomikosUserTransaction) {
//		JtaTransactionManager jtaTransactionManager = new JtaTransactionManager();
//		jtaTransactionManager.setTransactionManager(atomikosTransactionManager);
//		jtaTransactionManager.setUserTransaction(atomikosUserTransaction);
//		return jtaTransactionManager;
//	}
//
//	@Bean(initMethod = "init", destroyMethod = "close")
//	@DependsOn("userTransactionService")
//	public TransactionManager atomikosTransactionManager() {
//		UserTransactionManager transactionManager = new UserTransactionManager();
//		transactionManager.setForceShutdown(false);
//		return transactionManager;
//	}
//
//	@Bean(initMethod = "init", destroyMethod = "shutdownForce")
//	public UserTransactionService userTransactionService() {
//		return new UserTransactionServiceImp();
//	}
//
//	@Bean
//	public UserTransaction atomikosUserTransaction(@Value("${transaction.timeout}") String timeout) throws Exception {
//		UserTransactionImp userTransactionImp = new UserTransactionImp();
//		userTransactionImp.setTransactionTimeout(Integer.valueOf(timeout));
//		return userTransactionImp;
//	}
//}