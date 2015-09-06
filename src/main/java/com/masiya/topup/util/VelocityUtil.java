package com.masiya.topup.util;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocityUtil {
	public enum TemplateType {
		
		PasswordResetText("PasswordResetText"),
		PasswordResetHtml("PasswordResetHtml");
		
		private String name;
		private TemplateType(String name) {
			this.name = name;
		}
	}

	public static String getTemplate(TemplateType type, VelocityContext context) {
		String templ = "";
		StringWriter writer = new StringWriter();
		VelocityEngine ve = new VelocityEngine();
		
		ve.setProperty("resource.loader", "class");
		ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		ve.init();
		Template tpl = ve.getTemplate(type.name()+".vm");
		
		if(context != null) {
			tpl.merge(context, writer);
			return writer.toString();
		}
		return templ;
	}
}
