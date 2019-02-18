package br.com.gam.simulator.annotation;

import br.com.gam.simulator.constants.TransactionManagerConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Guilherme Mendes on 17/02/2019
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionManager {
	
	TransactionManagerConstants transactionManagerConstants();
}
