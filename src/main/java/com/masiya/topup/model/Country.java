package com.masiya.topup.model;

import org.apache.ibatis.type.Alias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Alias("Country")
public class Country {
	private Integer countryId;
	private String countryCode;
	private String countryName;
	private String currencyCode;
	private String capital;
	private String continent;
	private String area;
	private String language;
	private String[] languages;

	public Country() {
		//languages = new String[] {};
	}
	
	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	@JsonInclude(Include.NON_NULL)
	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@JsonInclude(Include.NON_NULL)
	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	@JsonInclude(Include.NON_NULL)
	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

	@JsonInclude(Include.NON_NULL)
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@JsonIgnore
	public String getLanguage() {
		return language;
	}

	@JsonIgnore
	public void setLanguage(String language) {
		this.language = language;
		if(language != null) {
			languages = language.split(",");
		}
	}

	@JsonInclude(Include.NON_NULL)
	public String[] getLanguages() {
		return languages;
	}

	public void setLanguages(String[] languages) {
		this.languages = languages;
	}

}
