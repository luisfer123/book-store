package com.book.store.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;

public class PasswordMatchValidator 
	implements ConstraintValidator<PasswordMatch, Object> {
	
	private String firstFieldName;
	private String secondFieldName;
	
	@Override
	public void initialize(PasswordMatch constraintAnnotation) {
		
		firstFieldName = constraintAnnotation.first();
		secondFieldName = constraintAnnotation.second();
		
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		
		System.out.println("isValid() executed!" + value.getClass());
		
		try {
			Object passwordObject = BeanUtils.getProperty(value, firstFieldName);
			Object confirmedPasswordObject = BeanUtils.getProperty(value, secondFieldName);
			
			 return passwordObject == null 
					 && confirmedPasswordObject == null 
					 || passwordObject != null 
					 && passwordObject.equals(confirmedPasswordObject);
			 
        } catch (Exception ignore) {
            System.out.println(ignore.getMessage());
		}
		
		return true;
	}
	
	

}
