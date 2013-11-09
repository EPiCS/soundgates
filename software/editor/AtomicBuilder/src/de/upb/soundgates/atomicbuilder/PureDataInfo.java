package de.upb.soundgates.atomicbuilder;

import java.util.ArrayList;
import java.util.List;

public class PureDataInfo {
	private String code;
	private List<String> outputDefinitions;
	private List<String> inputDefinitions;
	private List<String> properties;
	
	public List<String> getProperties() {
		return new ArrayList<String>(properties);
	}

	public PureDataInfo(){
		outputDefinitions = new ArrayList<>();
		inputDefinitions = new ArrayList<>();
		properties = new ArrayList<>();
	}

	public void addOutputDefinition(String outputDefinition){
		outputDefinitions.add(outputDefinition);
	}
	
	public void addInputDefinition(String inputDefinition){
		inputDefinitions.add(inputDefinition);
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<String> getOutputDefinitions() {
		return new ArrayList<String>(outputDefinitions);
	}
	public List<String> getInputDefinitions() {
		return new ArrayList<String>(inputDefinitions);
	}

	public void addProperty(String property) {
		properties.add(property);
	}
}
