package com.woowol.gutenmorgen.processor;

import java.util.List;

import org.springframework.stereotype.Service;

import com.woowol.gutenmorgen.processor.ExampleProcessor.Parameter;

@Service
public class ExampleProcessor extends Processor<Parameter> {
	@Override
	public void process(Parameter parameter) throws Exception {
		parameter.getParameter();
		parameter.getListParameter();
	}
	
	public static class Parameter {
		private String parameter;
		private List<String> listParameter;
		
		public String getParameter() {
			return parameter;
		}
		public void setParameter(String parameter) {
			this.parameter = parameter;
		}
		public List<String> getListParameter() {
			return listParameter;
		}
		public void setListParameter(List<String> listParameter) {
			this.listParameter = listParameter;
		}
	}
}
