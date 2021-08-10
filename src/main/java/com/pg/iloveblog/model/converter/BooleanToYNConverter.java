package com.pg.iloveblog.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToYNConverter implements AttributeConverter<Boolean, String>{
	/*
	 * Boolean → Y,N 
	 * @Param attribute boolean값
	 * @Return String true:Y / false:N 
	 * */
	@Override
	public String convertToDatabaseColumn(Boolean attribute) {
		return (attribute != null && attribute) ? "Y" : "N";
	}
	
	/*
	 * Y,N → Boolean 
	 * @Param : yn
	 * @Return : boolean
	 * 
	 * */
	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		return "Y".equalsIgnoreCase(dbData);
	}
	
	

}
